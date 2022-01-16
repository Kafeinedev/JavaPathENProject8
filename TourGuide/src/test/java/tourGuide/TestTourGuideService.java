package tourGuide;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.beans.LocationBean;
import tourGuide.beans.VisitedLocationBean;
import tourGuide.helper.InternalTestHelper;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

@ExtendWith(MockitoExtension.class)
public class TestTourGuideService {

	@Mock
	private RewardsService rewardsService;

	@Mock
	private GpsUtilProxy gpsUtil;

	@InjectMocks
	private TourGuideService tourGuideService;

	@BeforeAll
	public static void setUpBeforeAll() {
		InternalTestHelper.setInternalUserNumber(0);
	}

	@AfterEach
	public void cleanUp() {
		tourGuideService.tracker.stopTracking();
	}

	@Test
	public void getUserLocation() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		when(gpsUtil.getUserLocation(user.getUserId()))
				.thenReturn(new VisitedLocationBean(user.getUserId(), new LocationBean(), new Date()));

		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

		assertThat(user.getLastVisitedLocation()).isEqualTo(visitedLocation);
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
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

		tourGuideService.tracker.stopTracking();

		assertThat(allUsers.size()).isEqualTo(2);
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}

	// Test already done in getUserLocation()
	// @Test
	// public void trackUser() {
	// User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	// VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);
	//
	// assertEquals(user.getUserId(), visitedLocation.userId);
	// }

	@Disabled // Not yet implemented
	@Test
	public void getNearbyAttractions() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

		List<Attraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);

		assertEquals(5, attractions.size());
	}

	@Test
	public void getTripDeals() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);

		assertEquals(5, providers.size()); // tripPricer will always return 5 providers not 10
	}

}
