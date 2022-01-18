package tourGuide.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.AttractionBean;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.tracker.Tracker;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class DefaultTourGuideService implements TourGuideService {

	private Logger logger = LoggerFactory.getLogger(DefaultTourGuideService.class);

	private GpsUtilProxy gpsUtil;
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();

	public final Tracker tracker;
	boolean testMode = true;

	@Autowired
	public DefaultTourGuideService(GpsUtilProxy gpsUtil, RewardsService rewardsService) {
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;

		if (testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
			tracker = null; // no need to track user while testing
		} else {
			tracker = new Tracker(this);
			addShutDownHook();
		}
	}

	@Override
	public List<UserReward> getUserRewards(User user) {
		synchronized (user) {
			return user.getUserRewards();
		}
	}

	@Override
	public VisitedLocation getUserLocation(User user) {
		synchronized (user) {
			VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ? user.getLastVisitedLocation()
					: trackUserLocation(user);
			return visitedLocation;
		}
	}

	@Override
	public Map<String, Location> getAllCurrentLocation() {
		List<User> users = getAllUsers();
		Map<String, Location> locations = new ConcurrentHashMap<>();

		users.stream().parallel().forEach(u -> {
			locations.put(u.getUserId().toString(), getUserLocation(u).location);
		});

		return locations;
	}

	@Override
	public User getUser(String userName) {
		synchronized (internalUserMap) {
			return internalUserMap.get(userName);
		}
	}

	@Override
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}

	@Override
	public void addUser(User user) {
		if (!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}

	}

	@Override
	public List<Provider> getTripDeals(User user) {
		synchronized (user) {
			int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();

			List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(),
					user.getUserPreferences().getNumberOfAdults(), user.getUserPreferences().getNumberOfChildren(),
					user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);

			user.setTripDeals(providers);
			return providers;
		}
	}

	@Override
	public VisitedLocation trackUserLocation(User user) {
		synchronized (user) {
			VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId()).toVisitedLocation();
			user.addToVisitedLocations(visitedLocation);
			rewardsService.calculateRewards(user);

			return visitedLocation;
		}
	}

	@Override
	public Map<User, VisitedLocation> highVolumeTrackUserLocation(List<User> users) {
		final int usersNumber = users.size();
		Map<User, VisitedLocation> locations = new ConcurrentHashMap<>(usersNumber);
		CountDownLatch countDownLatch = new CountDownLatch(usersNumber);
		ExecutorService pool = Executors.newFixedThreadPool(200);

		users.forEach(u -> {
			pool.execute(() -> {
				try {
					locations.put(u, trackUserLocation(u));
				} catch (Exception e) {
					e.printStackTrace();
				} finally { // in case something unexpected happen we do not block the other threads.
					countDownLatch.countDown();
				}
			});
		});
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return locations;
	}

	@Override
	public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
		List<Attraction> nearbyAttractions = new ArrayList<>();
		for (Attraction attraction : AttractionBean.toAttraction(gpsUtil.getAttractions())) {
			if (rewardsService.isWithinAttractionProximity(attraction, visitedLocation.location)) {
				nearbyAttractions.add(attraction);
			}
		}

		return nearbyAttractions;
	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				tracker.stopTracking();
			}
		});
	}

	/**********************************************************************************
	 * 
	 * Methods and parameters below: for internal testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes
	// internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new ConcurrentHashMap<>();

	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);

			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}

	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i -> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(),
					new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		double leftLimit = -180;
		double rightLimit = 180;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
		double rightLimit = 85.05112878;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

}
