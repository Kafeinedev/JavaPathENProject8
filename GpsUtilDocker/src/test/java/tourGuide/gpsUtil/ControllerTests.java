package tourGuide.gpsUtil;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	void getAttractionsTest() throws Exception {
		mockMvc.perform(get("/getAttractions")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string(containsString("\"longitude\":-117.922008,\"latitude\":33.817595")));
	}

	@Test
	void getUserLocationTest() throws Exception {
		mockMvc.perform(get("/getUserLocation").param("userUUID", "123e4567-e89b-12d3-a456-426655440000"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(containsString("\"location\":{\"longitude\"")));
	}

}
