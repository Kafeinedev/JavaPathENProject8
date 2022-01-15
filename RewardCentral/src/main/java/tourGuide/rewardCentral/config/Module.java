package tourGuide.rewardCentral.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rewardCentral.RewardCentral;

@Configuration
public class Module {

	@Bean
	public RewardCentral gpsUtil() {
		return new RewardCentral();
	}

}
