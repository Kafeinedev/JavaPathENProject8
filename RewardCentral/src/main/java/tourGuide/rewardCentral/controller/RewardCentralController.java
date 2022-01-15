package tourGuide.rewardCentral.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.rewardCentral.service.RewardCentralService;

@RestController
public class RewardCentralController {

	@Autowired
	private RewardCentralService rewardCentralService;

	@GetMapping("/getAttractionRewardPoints")
	public int getAttractionRewardPoints(@RequestParam(name = "attractionId") UUID attractionId,
			@RequestParam(name = "userId") UUID userId) {
		return rewardCentralService.getAttractionRewardPoints(attractionId, userId);
	}

}
