package tourGuide.model;

import gpsUtil.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Small data class for transfering relevant data to user
 */
@Data
@AllArgsConstructor
public class CloseAttractionBean {

	private String attractionName;
	private Location attractionLocation;
	private Location userLocation;
	private double userMilesFromAttraction;
	private int rewardPoints;

}
