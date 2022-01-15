package tourGuide.beans;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class VisitedLocationBean {
	private UUID userId;
	private LocationBean location;
	private Date timeVisited;
}
