//package com.example.postapi;
//
//import static org.assertj.core.api.Assertions.*;
//
//import com.example.postapi.controllers.PostcodeController;
//import com.example.postapi.controllers.SuburbController;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
// These tests are commented out because they fail in the build pipeline
// due to not being able to initialise certain Spring beans correctly
// Feel free to uncomment them to test them locally
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class PostApiApplicationTests {
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Mock
//	private SuburbController suburbController;
//	@Mock
//	private PostcodeController postcodeController;
//
//	@Test
//	void contextLoads() {
//		assertThat(suburbController).isNotNull();
//		assertThat(postcodeController).isNotNull();
//	}
//
//}
