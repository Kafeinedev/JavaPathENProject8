package tourGuide.gpsUtil.docker.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@RestController
public class GpsUtilController {

	@Autowired
	private GpsUtil gpsUtil;

	@GetMapping("/getAttractions")
	public List<Attraction> getAttractions() {
		return gpsUtil.getAttractions();
	}

	@GetMapping("/getUserLocation")
	public VisitedLocation getUserLocation(@RequestParam(name = "userUUID") UUID userUUID) {
		return gpsUtil.getUserLocation(userUUID);
	}

}
