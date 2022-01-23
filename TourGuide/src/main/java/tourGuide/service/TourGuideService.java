package tourGuide.service;

import java.util.List;
import java.util.Map;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.CloseAttractionBean;
import tripPricer.Provider;

public interface TourGuideService {

	/**
	 * Gets the rewards owned by an user.
	 *
	 * @param user the user
	 * @return the user rewards
	 */
	List<UserReward> getUserRewards(User user);

	/**
	 * Gets the user location.
	 *
	 * @param user the user
	 * @return the user location
	 */
	VisitedLocation getUserLocation(User user);

	/**
	 * Gets the last known location of all users.
	 *
	 * @return a map using a the userId as a key and their last known location
	 */
	Map<String, Location> getAllCurrentLocation();

	/**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	User getUser(String userName);

	/**
	 * Gets all users.
	 *
	 * @return a list of all users
	 */
	List<User> getAllUsers();

	/**
	 * Adds user to database.
	 *
	 * @param user the user to add
	 */
	void addUser(User user);

	/**
	 * Gets the personnalized offers of an user, and set it to said user.
	 *
	 * @param user the user
	 * @return a list of provider
	 */
	List<Provider> getTripDeals(User user);

	/**
	 * Track user current location.
	 *
	 * @param user the user
	 * @return the visited location
	 */
	VisitedLocation trackUserLocation(User user);

	/**
	 * Gets the attractions nearby a visited location.
	 *
	 * @param visitedLocation the visited location
	 * @return a list of nearby attractions
	 */
	List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

	/**
	 * Track the location of a large amount of users
	 *
	 * @param users a list of users
	 * @return a map of user as key and their current location
	 */
	Map<User, VisitedLocation> highVolumeTrackUserLocation(List<User> users);

	/**
	 * Gets the closest attractions to an user last known position.
	 *
	 * @param username the name of the user
	 * @return the closest attractions to the user
	 */
	List<CloseAttractionBean> getClosestAttractions(String username);

}