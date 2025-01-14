package tourGuide.gpsUtil.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.gpsUtil.service.GpsUtilService;

@RestController
public class GpsUtilController {

	@Autowired
	private GpsUtilService gpsUtilService;

	@GetMapping("/getAttractions")
	public List<Attraction> getAttractions() {
		return gpsUtilService.getAttractions();
	}

	@GetMapping("/getUserLocation")
	public VisitedLocation getUserLocation(@RequestParam(name = "userUUID") UUID userUUID) {
		return gpsUtilService.getUserLocation(userUUID);
	}

}
