package tourGuide.rewardCentral.service;

import java.util.UUID;

public interface RewardCentralService {

	/**
	 * Gets the attraction reward points.
	 *
	 * @param attractionId the attraction id
	 * @param userId       the user id
	 * @return the rewards points
	 */
	int getAttractionRewardPoints(UUID attractionId, UUID userId);

}
