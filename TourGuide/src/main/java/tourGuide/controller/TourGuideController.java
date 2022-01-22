package tourGuide.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.CloseAttractionBean;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.service.TourGuideService;
import tripPricer.Provider;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	@RequestMapping("/getLocation")
	public Location getLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return visitedLocation.location;
	}

	@RequestMapping("/getNearbyAttractions")
	public List<CloseAttractionBean> getNearbyAttractions(@RequestParam String userName) {
		return tourGuideService.getClosestAttractions(userName);
	}

	@RequestMapping("/getRewards")
	public List<UserReward> getRewards(@RequestParam String userName) {
		return tourGuideService.getUserRewards(getUser(userName));
	}

	@RequestMapping("/getAllCurrentLocations")
	public Map<String, Location> getAllCurrentLocations() {
		return tourGuideService.getAllCurrentLocation();
	}

	@RequestMapping("/getTripDeals")
	public List<Provider> getTripDeals(@RequestParam String userName) {
		return tourGuideService.getTripDeals(getUser(userName));
	}

	private User getUser(String userName) {
		return tourGuideService.getUser(userName);
	}

}