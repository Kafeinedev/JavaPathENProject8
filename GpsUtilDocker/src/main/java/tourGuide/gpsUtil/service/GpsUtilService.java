package tourGuide.gpsUtil.service;

import java.util.List;
import java.util.UUID;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public interface GpsUtilService {

	/**
	 * Gets attractions
	 * 
	 * @return a list containing all the attractions
	 */
	public List<Attraction> getAttractions();

	/**
	 * Gets the user location.
	 *
	 * @param user id
	 * @return the user location
	 */
	public VisitedLocation getUserLocation(UUID userUUID);
}
