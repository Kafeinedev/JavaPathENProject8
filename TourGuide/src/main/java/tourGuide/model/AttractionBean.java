package tourGuide.model;

import java.util.UUID;

import gpsUtil.location.Attraction;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Small data class for allowing the use of feign for transfer of attraction data from microservice
 */
@Data
@NoArgsConstructor
public class AttractionBean {

	private double longitude;
	private double latitude;
	private String attractionName;
	private String city;
	private String state;
	private UUID attractionId;

	public AttractionBean(Attraction attraction) {
		this.longitude = attraction.longitude;
		this.latitude = attraction.latitude;
		this.attractionName = attraction.attractionName;
		this.city = attraction.city;
		this.state = attraction.state;
		this.attractionId = attraction.attractionId;
	}

	public Attraction toAttraction() {
		return new Attraction(attractionName, city, state, latitude, longitude);
	}
}
