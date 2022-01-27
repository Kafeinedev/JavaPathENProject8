package tourGuide.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger = LoggerFactory.getLogger(TourGuideController.class);

	@Autowired
	TourGuideService tourGuideService;

	/**
	 * Index.
	 *
	 * @return greetings
	 */
	@RequestMapping("/")
	public String index() {
		logger.info("Get request @ /");

		return "Greetings from TourGuide!";
	}

	/**
	 * Get user last known location. The spring framework will serialize the result
	 * into json.
	 *
	 * @param user the user
	 * @return the visited location
	 */
	@RequestMapping("/getLocation")
	public Location getLocation(@RequestParam String userName) {
		logger.info("Get request @ /getLocation username : " + userName);

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return visitedLocation.location;
	}

	/**
	 * Gets the closest attractions to an user last known position. The spring
	 * framework will serialize the result into json.
	 *
	 * @param username the name of the user
	 * @return the closest attractions to the user
	 */
	@RequestMapping("/getNearbyAttractions")
	public List<CloseAttractionBean> getNearbyAttractions(@RequestParam String userName) {
		logger.info("Get request @ /getNearbyAttractions username : " + userName);

		return tourGuideService.getClosestAttractions(userName);
	}

	/**
	 * Gets the rewards owned by an user. The spring framework will serialize the
	 * result into json.
	 *
	 * @param user the user
	 * @return the user rewards
	 */
	@RequestMapping("/getRewards")
	public List<UserReward> getRewards(@RequestParam String userName) {
		logger.info("Get request @ /getRewards username : " + userName);

		return tourGuideService.getUserRewards(getUser(userName));
	}

	/**
	 * Gets the last known location of all users. The spring framework will
	 * serialize the result into json.
	 *
	 * @return a map using a the userId as a key and their last known location
	 */
	@RequestMapping("/getAllCurrentLocations")
	public Map<String, Location> getAllCurrentLocations() {
		logger.info("Get request @ /getAllCurrentLocations");

		return tourGuideService.getAllCurrentLocation();
	}

	/**
	 * Gets the personnalized offers of an user, and set it to said user. The spring
	 * framework will serialize the result into json.
	 *
	 * @param user the user
	 * @return a list of provider
	 */
	@RequestMapping("/getTripDeals")
	public List<Provider> getTripDeals(@RequestParam String userName) {
		logger.info("Get request @ /getTripDeals username : " + userName);

		return tourGuideService.getTripDeals(getUser(userName));
	}

	/**
	 * Gets the user.
	 *
	 * @param userName the username
	 * @return the user
	 */
	private User getUser(String userName) {
		return tourGuideService.getUser(userName);
	}

}