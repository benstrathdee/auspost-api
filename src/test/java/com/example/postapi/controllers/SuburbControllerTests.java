package com.example.postapi.controllers;

import static org.assertj.core.api.Assertions.*;

import com.example.postapi.dtos.NewSuburbWithPostcode;
import com.example.postapi.dtos.SavedSuburbWithPostcode;
import com.example.postapi.services.PostcodeService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class SuburbControllerTests {
    private MockMvc mvc;
    @Mock
    private PostcodeService postcodeService;
    @InjectMocks
    SuburbController suburbController;

    private JacksonTester<NewSuburbWithPostcode> jsonSuburb;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(suburbController).build();
    }

    @Test
    public void canRetrieveByPostcodeWhenExists() throws Exception {
        List<String> mockReturn = Arrays.asList("Brooklyn", "Kingsville", "Maidstone", "Tottenham", "West Footscray");

        given(postcodeService.getSuburbsByPostcode(3012))
                .willReturn(mockReturn);

        MockHttpServletResponse response = mvc.perform(
                get("/suburbs?postcode=3012")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[\"Brooklyn\",\"Kingsville\",\"Maidstone\",\"Tottenham\",\"West Footscray\"]");
    }

    @Test
    public void canCreateNewPostcodeSuburbLink() throws Exception {
        NewSuburbWithPostcode mockData = new NewSuburbWithPostcode("Melbourne", 3000);
        SavedSuburbWithPostcode mockReturn = new SavedSuburbWithPostcode("Melbourne", 3000);

        MockHttpServletResponse response = mvc.perform(
                        post("/suburbs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonSuburb.write(mockData).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}
