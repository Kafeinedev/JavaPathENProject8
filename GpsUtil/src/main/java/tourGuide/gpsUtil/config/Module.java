package tourGuide.gpsUtil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;

@Configuration
public class Module {

	@Bean
	public GpsUtil gpsUtil() {
		return new GpsUtil();
	}

}
