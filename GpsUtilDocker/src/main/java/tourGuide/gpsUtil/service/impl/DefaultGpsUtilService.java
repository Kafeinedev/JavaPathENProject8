package tourGuide.gpsUtil.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.gpsUtil.service.GpsUtilService;

@Service
public class DefaultGpsUtilService implements GpsUtilService {

	@Autowired
	private GpsUtil gpsUtil;

	@Override
	public List<Attraction> getAttractions() {
		return gpsUtil.getAttractions();
	}

	@Override
	public VisitedLocation getUserLocation(UUID userUUID) {
		return gpsUtil.getUserLocation(userUUID);
	}

}
