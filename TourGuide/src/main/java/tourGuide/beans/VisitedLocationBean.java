package tourGuide.beans;

import java.util.Date;
import java.util.UUID;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import lombok.Data;

@Data
public class VisitedLocationBean {
	private UUID userId;
	private LocationBean location;
	private Date timeVisited;

	public VisitedLocation toVisitedLocation() {
		return new VisitedLocation(userId, new Location(location.getLatitude(), location.getLongitude()), timeVisited);
	}
}
