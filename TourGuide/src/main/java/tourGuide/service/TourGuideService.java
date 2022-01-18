package tourGuide.service;

import java.util.List;
import java.util.Map;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tripPricer.Provider;

public interface TourGuideService {

	List<UserReward> getUserRewards(User user);

	VisitedLocation getUserLocation(User user);

	Map<String, Location> getAllCurrentLocation();

	User getUser(String userName);

	List<User> getAllUsers();

	void addUser(User user);

	List<Provider> getTripDeals(User user);

	VisitedLocation trackUserLocation(User user);

	List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

	Map<User, VisitedLocation> highVolumeTrackUserLocation(List<User> users);

}