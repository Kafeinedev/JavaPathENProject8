package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.model.AttractionBean;
import tourGuide.model.VisitedLocationBean;

@FeignClient(name = "microservice-gpsutil", url = "http://localhost:9001")
public interface GpsUtilProxy {

	/**
	 * Gets attractions
	 * 
	 * @return a list containing all the attractions
	 */
	@GetMapping("/getAttractions")
	public List<AttractionBean> getAttractions();

	/**
	 * Gets the user location.
	 *
	 * @param user id
	 * @return the user location
	 */
	@GetMapping("/getUserLocation")
	public VisitedLocationBean getUserLocation(@RequestParam(name = "userUUID") UUID userUUID);
}
