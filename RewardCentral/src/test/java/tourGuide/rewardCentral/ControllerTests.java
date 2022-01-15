package tourGuide.rewardCentral;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	private void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void getAttractionRewardPoints() throws Exception {
		mockMvc.perform(get("/getAttractionRewardPoints").param("attractionId", UUID.randomUUID().toString())
				.param("userId", UUID.randomUUID().toString())).andExpect(status().is2xxSuccessful());
	}

}
