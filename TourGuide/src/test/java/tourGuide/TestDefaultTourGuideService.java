package tourGuide;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.AttractionBean;
import tourGuide.model.CloseAttractionBean;
import tourGuide.model.LocationBean;
import tourGuide.model.User;
import tourGuide.model.VisitedLocationBean;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.impl.DefaultTourGuideService;
import tripPricer.Provider;

@ExtendWith(MockitoExtension.class)
public class TestDefaultTourGuideService {

	@Mock
	private RewardsService rewardsService;

	@Mock
	private GpsUtilProxy gpsUtil;

	private DefaultTourGuideService tourGuideService;

	@BeforeAll
	public static void setUpBeforeAll() {
		InternalTestHelper.setInternalUserNumber(0);
	}

	@BeforeEach
	public void setUp() {
		tourGuideService = new DefaultTourGuideService(gpsUtil, rewardsService, true, false);
	}

	@Test
	public void getUserLocation() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		when(gpsUtil.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocationBean(user.getUserId(), new LocationBean(), new Date()));

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);

		assertThat(user.getLastVisitedLocation()).isEqualTo(visitedLocation);
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}

	@Test
	public void getAllUsersLocation() {
		User user1 = new User(UUID.randomUUID(), "jon1", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon@tourGuide.com");
		User user3 = new User(UUID.randomUUID(), "jon3", "000", "jon@tourGuide.com");
		User user4 = new User(UUID.randomUUID(), "jon4", "000", "jon@tourGuide.com");
		tourGuideService.addUser(user1);
		tourGuideService.addUser(user2);
		tourGuideService.addUser(user3);
		tourGuideService.addUser(user4);
		LocationBean location = new LocationBean(0.0, 0.0);
		when(gpsUtil.getUserLocation(any(UUID.class)))
				.thenReturn(new VisitedLocationBean(user1.getUserId(), location, new Date()))
				.thenReturn(new VisitedLocationBean(user2.getUserId(), location, new Date()))
				.thenReturn(new VisitedLocationBean(user3.getUserId(), location, new Date()))
				.thenReturn(new VisitedLocationBean(user4.getUserId(), location, new Date()));

		Map<String, Location> test = tourGuideService.getAllCurrentLocation();

		assertThat(test.size()).isEqualTo(4);
		assertTrue(test.containsKey(user1.getUserId().toString()));
		assertTrue(test.containsKey(user2.getUserId().toString()));
		assertTrue(test.containsKey(user3.getUserId().toString()));
		assertTrue(test.containsKey(user4.getUserId().toString()));
	}

	@Test
	public void addUser() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);

		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}

	@Test
	public void getAllUsers() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);

		List<User> allUsers = tourGuideService.getAllUsers();

		assertThat(allUsers.size()).isEqualTo(2);
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}

	@Test
	public void trackUser() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		when(gpsUtil.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocationBean(user.getUserId(), new LocationBean(), new Date()));

		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

		assertEquals(user.getUserId(), visitedLocation.userId);
	}

	@Test
	public void highVolumeTrackUser() {
		User user1 = new User(UUID.randomUUID(), "jon1", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon@tourGuide.com");
		User user3 = new User(UUID.randomUUID(), "jon3", "000", "jon@tourGuide.com");
		User user4 = new User(UUID.randomUUID(), "jon4", "000", "jon@tourGuide.com");
		tourGuideService.addUser(user1);
		tourGuideService.addUser(user2);
		tourGuideService.addUser(user3);
		tourGuideService.addUser(user4);
		LocationBean location = new LocationBean(0.0, 0.0);
		when(gpsUtil.getUserLocation(any(UUID.class)))
				.thenReturn(new VisitedLocationBean(user1.getUserId(), location, new Date()))
				.thenReturn(new VisitedLocationBean(user2.getUserId(), location, new Date()))
				.thenReturn(new VisitedLocationBean(user3.getUserId(), location, new Date()))
				.thenReturn(new VisitedLocationBean(user4.getUserId(), location, new Date()));

		Map<User, VisitedLocation> test = tourGuideService.highVolumeTrackUserLocation(tourGuideService.getAllUsers());

		assertThat(test.size()).isEqualTo(4);
		assertTrue(test.containsKey(user1)); // as highVolume is multithreaded the values
												// associated to the keys are not necessarly correct
		assertTrue(test.containsKey(user2));
		assertTrue(test.containsKey(user3));
		assertTrue(test.containsKey(user4));
	}

	@Test
	public void getClosestAttractions() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		tourGuideService.addUser(user);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(0.0, 0.0), new Date()));
		AttractionBean attraction = new AttractionBean(new Attraction("close", "city", "state", 0.0, 0.0));
		when(gpsUtil.getAttractions())
				.thenReturn(List.of(attraction, attraction, attraction, attraction, attraction, attraction));

		List<CloseAttractionBean> attractions = tourGuideService.getClosestAttractions(user.getUserName());

		assertEquals(5, attractions.size());
	}

	@Test
	public void getTripDeals() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);

		assertEquals(5, providers.size()); // tripPricer will always return 5 providers not 10
	}

}
