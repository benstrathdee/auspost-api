package com.example.postapi.controllers;

import com.example.postapi.dtos.NewSuburbWithPostcode;
import com.example.postapi.services.PostcodeService;
import com.example.postapi.services.SuburbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class PostcodeControllerTests {
    private MockMvc mvc;
    @Mock
    private SuburbService suburbService;
    @InjectMocks
    PostcodeController postcodeController;

    private JacksonTester<NewSuburbWithPostcode> jsonSuburb;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(postcodeController).build();
    }

    @Test
    public void canRetrieveByPostcodeWhenExists() throws Exception {
        given(suburbService.getPostcodeBySuburb("Maidstone"))
                .willReturn(3012);

        MockHttpServletResponse response = mvc.perform(
                        get("/postcodes?suburb=Maidstone")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("3012");
    }
}
