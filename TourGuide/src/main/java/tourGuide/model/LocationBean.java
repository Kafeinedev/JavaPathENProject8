package tourGuide.model;

import gpsUtil.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Small data class for allowing the use of feign for transfer of location data from microservice
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationBean {
	private double longitude;
	private double latitude;

	public Location toLocation() {
		return new Location(latitude, longitude);
	}
}
