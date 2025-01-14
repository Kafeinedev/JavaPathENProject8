package tourGuide.service.impl;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.mapper.AttractionMapper;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.service.RewardsService;

@Service
public class DefaultRewardsService implements RewardsService {
	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	private Logger logger = LoggerFactory.getLogger(DefaultRewardsService.class);

	// proximity in miles
	private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private ExecutorService pool = Executors.newFixedThreadPool(200);

	private GpsUtilProxy gpsUtil;
	private RewardCentralProxy rewardsCentral;

	@Autowired
	public DefaultRewardsService(GpsUtilProxy gpsUtil, RewardCentralProxy rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}

	@Override
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	@Override
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	@Override
	public void calculateRewards(User user) {
		synchronized (user) {
			List<VisitedLocation> userLocations = user.getVisitedLocations();
			List<Attraction> attractions = AttractionMapper.toAttraction(gpsUtil.getAttractions());

			for (VisitedLocation visitedLocation : userLocations) {
				for (Attraction attraction : attractions) {
					if (user.getUserRewards().stream()
							.filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
						if (nearAttraction(visitedLocation, attraction)) {
							user.addUserReward(
									new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
						}
					}
				}
			}
		}
	}

	@Override
	public void highVolumeCalculateRewards(List<User> users) {
		CountDownLatch countDownLatch = new CountDownLatch(users.size());

		users.forEach(u -> {
			pool.execute(() -> {
				try {
					calculateRewards(u);
				} catch (Exception e) {
					logger.error("Exception thrown in highVolumeCalculateRewards", e);
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
	}

	@Override
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}

	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}

	@Override
	public int getRewardPoints(Attraction attraction, User user) {
		synchronized (user) {
			return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
		}
	}

	@Override
	public double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math
				.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}

}
