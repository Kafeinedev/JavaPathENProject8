package tourGuide;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

@SpringBootTest(properties = { "testmode=true", "tracking=false" })
class TestSynchronisation {

	@Autowired
	private TourGuideService tourGuideService;

	@Autowired
	private RewardsService rewardsService;

	// Trying to induce a memory race
	// this test is designed for a 4 core cpu
	// this test will run for one hour
	@Test
	@Disabled
	void ressources_whenAccededInMultithread_areThreadSafe() {
		AtomicBoolean loop = new AtomicBoolean(true);
		ExecutorService pool = Executors.newFixedThreadPool(4);
		try {
			pool.submit(() -> {
				while (loop.get()) {
					tourGuideService.highVolumeTrackUserLocation(tourGuideService.getAllUsers());
				}
			});
			pool.submit(() -> {
				while (loop.get()) {
					rewardsService.highVolumeCalculateRewards(tourGuideService.getAllUsers());
				}
			});
			pool.submit(() -> {
				while (loop.get()) {
					tourGuideService.getAllCurrentLocation();
				}
			});
			pool.submit(() -> {
				while (loop.get()) {
					tourGuideService.getAllUsers().forEach(u -> {
						tourGuideService.getTripDeals(u);
						tourGuideService.getUserLocation(u);
						tourGuideService.getUserRewards(u);
						tourGuideService.trackUserLocation(u);
						rewardsService.calculateRewards(u);
					});
				}
			});

			for (int i = 0; i < 60; ++i) {
				System.out.println("Memory Race test still running time passed : " + i + " minutes");
				Thread.sleep(60000);
			}
		} catch (InterruptedException e) {
			fail("Multithreading test fail");
			e.printStackTrace();
		} finally {
			loop.set(false);// clean up
			pool.shutdown();
		}
	}

}
