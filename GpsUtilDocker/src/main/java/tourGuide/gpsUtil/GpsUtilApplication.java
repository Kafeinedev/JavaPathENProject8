package tourGuide.gpsUtil;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GpsUtilApplication {

	{
		// A bug in gpsUtil.jar force us to put application locale to en_US.
		Locale.setDefault(Locale.US);
	}

	public static void main(String[] args) {
		SpringApplication.run(GpsUtilApplication.class, args);
	}

}
