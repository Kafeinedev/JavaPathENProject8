package tourGuide.mapper;

import java.util.ArrayList;
import java.util.List;

import gpsUtil.location.Attraction;
import tourGuide.model.AttractionBean;

public class AttractionMapper {

	public static List<Attraction> toAttraction(List<AttractionBean> toConvert) {
		List<Attraction> attractions = new ArrayList<>(toConvert.size());
		toConvert.forEach(attraction -> {
			attractions.add(attraction.toAttraction());
		});
		return attractions;
	}

}
