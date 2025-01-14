package tourGuide.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-rewardcentral", url = "${url.rewardcentral}")
public interface RewardCentralProxy {

	/**
	 * Gets the attraction reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId       the user id
	 * @return the rewards points
	 */
	@GetMapping("/getAttractionRewardPoints")
	public int getAttractionRewardPoints(@RequestParam(name = "attractionId") UUID attractionId,
			@RequestParam(name = "userId") UUID userId);
}
