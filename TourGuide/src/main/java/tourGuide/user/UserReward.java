package tourGuide.user;

import gpsUtil.location.VisitedLocation;
import tourGuide.beans.AttractionBean;

public class UserReward {

	public final VisitedLocation visitedLocation;
	public final AttractionBean attraction;
	private int rewardPoints;

	public UserReward(VisitedLocation visitedLocation, AttractionBean attraction, int rewardPoints) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
		this.rewardPoints = rewardPoints;
	}

	public UserReward(VisitedLocation visitedLocation, AttractionBean attraction) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

}
