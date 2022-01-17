package tourGuide.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourGuide.user.User;

public interface RewardsService {

	void setProximityBuffer(int proximityBuffer);

	void setDefaultProximityBuffer();

	void calculateRewards(User user);

	boolean isWithinAttractionProximity(Attraction attraction, Location location);

	double getDistance(Location loc1, Location loc2);

}