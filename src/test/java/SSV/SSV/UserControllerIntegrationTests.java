package SSV.SSV;


import SSV.SSV.controller.UserController;
import SSV.SSV.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService mockUserService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = webApplicationContext.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(webApplicationContext.getBean("userController"));
	}

	@Test
	public void getAllTest() throws Exception {
		this.mockMvc.perform(get("/users"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

	}

	@Test
	public void getByIdTest() throws Exception {
		this.mockMvc.perform(get("/users/05bae6f5-cea6-4bd7-98b2-006692e674e5"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void postTest() throws Exception {
		this.mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON).content("{\"title:\":\"test\"}"))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void deleteUserTest() throws Exception {
		this.mockMvc.perform(delete("/users/{id}", "05bae6f5-cea6-4bd7-98b2-006692e674e5"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void putTest() throws Exception {
		this.mockMvc.perform(put("/users/{id}", "05bae6f5-cea6-4bd7-98b2-006692e674e5")
				.contentType(MediaType.APPLICATION_JSON).content("{\"firstname:\":\"test\"}"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
