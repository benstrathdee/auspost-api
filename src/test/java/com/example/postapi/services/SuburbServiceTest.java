package com.example.postapi.services;

import com.example.postapi.entities.Postcode;
import com.example.postapi.entities.Suburb;
import com.example.postapi.repositories.SuburbRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SuburbServiceTest {
    @Mock
    private SuburbRepo suburbRepo;

    @InjectMocks
    private SuburbService suburbService;

    @Test
    public void shouldReturnPostcode_WhenProvidedSuburbName() {
        Postcode mockPostcode = new Postcode(3012);
        Suburb mockSuburb = new Suburb("Maidstone", mockPostcode);

        given(suburbRepo.findByName("Maidstone")).willReturn(Optional.of(mockSuburb));

        assertThat(suburbService.getPostcodeBySuburb("Maidstone")).isEqualTo(3012);
    }

    @Test
    public void shouldReturnNull_WhenSuburbDoesNotExist() {
        assertThat(suburbService.getPostcodeBySuburb("Maidstone")).isEqualTo(null);
    }
}
