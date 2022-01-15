package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.beans.AttractionBean;
import tourGuide.beans.VisitedLocationBean;

@FeignClient(name = "microservice-gpsutil", url = "http://localhost:9001")
public interface GpsUtilProxy {

	@GetMapping("/getAttractions")
	public List<AttractionBean> getAttractions();

	@GetMapping("/getUserLocation")
	public VisitedLocationBean getUserLocation(@RequestParam(name = "userUUID") UUID userUUID);
}
