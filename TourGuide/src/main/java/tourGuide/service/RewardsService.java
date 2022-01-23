package tourGuide.service;

import java.util.List;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourGuide.model.User;

public interface RewardsService {

	/**
	 * Sets the proximity buffer. The proximity buffer determine if the user is near
	 * an attraction or not.
	 *
	 * @param proximityBuffer the number of miles to be set as the new
	 *                        proximityBuffer
	 */
	void setProximityBuffer(int proximityBuffer);

	/**
	 * Reset the proximityBuffer to default
	 */
	void setDefaultProximityBuffer();

	/**
	 * Check if the user has been close to an attraction elligible for a reward and
	 * if positive add that reward to the user.
	 *
	 * @param user the user to check
	 */
	void calculateRewards(User user);

	/**
	 * Checks if is within attraction proximity.
	 *
	 * @param attraction the attraction
	 * @param location   the location
	 * @return true, if is within attraction proximity
	 */
	boolean isWithinAttractionProximity(Attraction attraction, Location location);

	/**
	 * Gets the distance.
	 *
	 * @param loc1 the first location
	 * @param loc2 the second location
	 * @return the distance in miles
	 */
	double getDistance(Location loc1, Location loc2);

	/**
	 * Calculate reward for a large amount of users. Each user that have been close
	 * to an elligible attraction will receive the appropriate reward.
	 *
	 * @param users list of users to process
	 */
	void highVolumeCalculateRewards(List<User> users);

	/**
	 * Gets the reward points gained by an user from an attraction.
	 *
	 * @param attraction the attraction
	 * @param user       the user
	 * @return the reward points
	 */
	int getRewardPoints(Attraction attraction, User user);

}