package tourGuide;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import gpsUtil.location.Location;
import tourGuide.model.AttractionBean;
import tourGuide.model.CloseAttractionBean;
import tourGuide.model.LocationBean;
import tourGuide.model.VisitedLocationBean;

class BeansTests {

	@Test
	void attractionBean() {
		AttractionBean attraction = new AttractionBean();
		UUID id = UUID.randomUUID();
		attraction.setAttractionId(id);
		attraction.setAttractionName("name");
		attraction.setCity("city");
		attraction.setLatitude(0.0);
		attraction.setLongitude(0.0);
		attraction.setState("state");

		assertEquals(attraction.getAttractionId(), id);
		assertEquals(attraction.getAttractionName(), "name");
		assertEquals(attraction.getCity(), "city");
		assertEquals(attraction.getLatitude(), 0.0);
		assertEquals(attraction.getLongitude(), 0.0);
		assertEquals(attraction.getState(), "state");
	}

	@Test
	void CloseAttractionBean() {
		CloseAttractionBean attraction = new CloseAttractionBean("name", new Location(0.0, 0.0), new Location(0.0, 0.0),
				0.0, 0);
		CloseAttractionBean differentAttraction = new CloseAttractionBean("name2", new Location(1.0, 1.0),
				new Location(10.0, 10.0), 10.0, 10);
		differentAttraction.setAttractionName(attraction.getAttractionName());
		differentAttraction.setAttractionLocation(attraction.getAttractionLocation());
		differentAttraction.setRewardPoints(attraction.getRewardPoints());
		differentAttraction.setUserMilesFromAttraction(attraction.getUserMilesFromAttraction());
		differentAttraction.setUserLocation(attraction.getUserLocation());

		assertEquals(attraction, differentAttraction);
	}

	@Test
	void LocationBean() {
		LocationBean location = new LocationBean();
		LocationBean comp = new LocationBean(10.0, 10.0);
		location.setLatitude(10.0);
		location.setLongitude(10.0);

		assertEquals(location.getLatitude(), 10.0);
		assertEquals(location.getLongitude(), 10.0);
		assertEquals(location, comp);
	}

	@Test
	void VisitedLocationBean() {
		VisitedLocationBean location = new VisitedLocationBean();
		VisitedLocationBean feeder = new VisitedLocationBean(UUID.randomUUID(), new LocationBean(0.0, 0.0), new Date());

		location.setUserId(feeder.getUserId());
		location.setTimeVisited(feeder.getTimeVisited());
		location.setLocation(feeder.getLocation());

		assertEquals(location, feeder);
	}
}
