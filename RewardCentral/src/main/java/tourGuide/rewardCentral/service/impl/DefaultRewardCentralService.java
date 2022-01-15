package tourGuide.rewardCentral.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;
import tourGuide.rewardCentral.service.RewardCentralService;

@Service
public class DefaultRewardCentralService implements RewardCentralService {

	@Autowired
	private RewardCentral rewardCentral;

	@Override
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		return rewardCentral.getAttractionRewardPoints(attractionId, userId);
	}

}
