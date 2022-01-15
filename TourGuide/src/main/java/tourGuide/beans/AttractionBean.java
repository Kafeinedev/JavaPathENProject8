package tourGuide.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gpsUtil.location.Attraction;
import lombok.Data;

@Data
public class AttractionBean {

	private double longitude;
	private double latitude;
	private String attractionName;
	private String city;
	private String state;
	private UUID attractionId;

	public Attraction toAttraction() {
		return new Attraction(attractionName, city, state, latitude, longitude);
	}

	public static List<Attraction> toAttraction(List<AttractionBean> toConvert) {
		List<Attraction> attractions = new ArrayList<>(toConvert.size());
		toConvert.forEach(attraction -> {
			attractions.add(attraction.toAttraction());
		});
		return attractions;
	}
}
