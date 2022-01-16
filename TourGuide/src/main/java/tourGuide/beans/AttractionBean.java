package tourGuide.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gpsUtil.location.Attraction;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	public static List<Attraction> toAttraction(List<AttractionBean> toConvert) {
		List<Attraction> attractions = new ArrayList<>(toConvert.size());
		toConvert.forEach(attraction -> {
			attractions.add(attraction.toAttraction());
		});
		return attractions;
	}
}
