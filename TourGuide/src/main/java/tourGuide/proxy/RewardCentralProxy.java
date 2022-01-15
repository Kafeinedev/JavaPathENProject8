package tourGuide.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-rewardcentral", url = "http://localhost:9002")
public interface RewardCentralProxy {

	@GetMapping("/getAttractionRewardPoints")
	public int getAttractionRewardPoints(@RequestParam(name = "attractionId") UUID attractionId,
			@RequestParam(name = "userId") UUID userId);
}
