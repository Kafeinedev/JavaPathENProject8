package tourGuide.model;

import gpsUtil.location.Location;
import lombok.Data;

@Data
public class LocationBean {
	private double longitude;
	private double latitude;

	public Location toLocation() {
		return new Location(latitude, longitude);
	}
}
