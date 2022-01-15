package tourGuide.beans;

import java.util.UUID;

import lombok.Data;

@Data
public class AttractionBean {

	private double longitude;
	private double latitude;
	private String attractionName;
	private String city;
	private String state;
	private UUID attractionId;
}
