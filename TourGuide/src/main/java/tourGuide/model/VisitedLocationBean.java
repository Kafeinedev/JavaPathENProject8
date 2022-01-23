package tourGuide.model;

import java.util.Date;
import java.util.UUID;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Small data class for allowing the use of feign for transfer of visitedlocation data from microservice
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationBean {
	private UUID userId;
	private LocationBean location;
	private Date timeVisited;

	public VisitedLocation toVisitedLocation() {
		return new VisitedLocation(userId, new Location(location.getLatitude(), location.getLongitude()), timeVisited);
	}
}
