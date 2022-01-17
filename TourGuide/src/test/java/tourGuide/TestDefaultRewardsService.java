package tourGuide;

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
import tourGuide.beans.AttractionBean;
import tourGuide.proxy.GpsUtilProxy;
import tourGuide.proxy.RewardCentralProxy;
import tourGuide.service.impl.DefaultRewardsService;
import tourGuide.user.User;

@ExtendWith(MockitoExtension.class)
public class TestDefaultRewardsService {

	@Mock
	private GpsUtilProxy mockGpsUtil;

	@Mock
	private RewardCentralProxy rewardsCentral;

	@InjectMocks
	private DefaultRewardsService rewardsService;

	private User user;

	private Attraction attraction;

	@BeforeEach
	public void setUp() {
		attraction = new Attraction("name", "city", "state", 0.0d, 0.0d);
		user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	}

	@Test
	public void userGetRewards() {
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
		when(mockGpsUtil.getAttractions()).thenReturn(List.of(new AttractionBean(attraction)));

		rewardsService.calculateRewards(user);

		assertTrue(user.getUserRewards().size() == 1);
	}

	@Test
	public void isWithinAttractionProximity() {
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction));
	}

	@Test
	public void nearAllAttractions() {
		AttractionBean attractionBean1 = new AttractionBean(attraction);
		AttractionBean attractionBean2 = new AttractionBean(attraction);
		AttractionBean attractionBean3 = new AttractionBean(attraction);
		attractionBean2.setAttractionName("number2");
		attractionBean3.setAttractionName("name3");
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attractionBean1.toAttraction(), new Date()));
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attractionBean2.toAttraction(), new Date()));
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attractionBean3.toAttraction(), new Date()));
		when(mockGpsUtil.getAttractions())
				.thenReturn(List.of(attractionBean1, attractionBean2, attractionBean3, attractionBean1));

		rewardsService.calculateRewards(user);

		assertEquals(3, user.getUserRewards().size());
	}

}
