package com.example.postapi.services;

import com.example.postapi.dtos.NewSuburbWithPostcode;
import com.example.postapi.dtos.SavedSuburbWithPostcode;
import com.example.postapi.entities.Postcode;
import com.example.postapi.entities.Suburb;
import com.example.postapi.repositories.PostcodeRepo;
import com.example.postapi.repositories.SuburbRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PostcodeServiceTests {
    @Mock
    private PostcodeRepo postcodeRepo;
    @Mock
    private SuburbRepo suburbRepo;

    @InjectMocks
    private PostcodeService postcodeService;

    @Test
    public void shouldReturnSuburbNames_WhenProvidedPostcode() {
        List<Suburb> mockSuburbs = List.of(new Suburb("Maidstone"), new Suburb("Kingsville"));
        Postcode mockPostcode = new Postcode(3012, mockSuburbs);
        List<String> mockReturn = mockSuburbs.stream().map(Suburb::getName).toList();

        given(postcodeRepo.findByNumber(3012)).willReturn(Optional.of(mockPostcode));

        assertThat(postcodeService.getSuburbsByPostcode(3012)).isEqualTo(mockReturn);
    }

    @Test
    public void shouldReturnNull_WhenPostcodeDoesNotExist() {
        assertThat(postcodeService.getSuburbsByPostcode(3000)).isEqualTo(null);
    }

    @Test
    public void shouldCreateNewSuburbAndPostcode_WhenNotFound() {
        NewSuburbWithPostcode mockInput = new NewSuburbWithPostcode("Maidstone", 3012);
        SavedSuburbWithPostcode mockOutput = new SavedSuburbWithPostcode("Maidstone", 3012);

        assertThat(postcodeService.create(mockInput)).isInstanceOf(SavedSuburbWithPostcode.class);
        assertThat(postcodeService.create(mockInput).getPostcode()).isEqualTo(mockOutput.getPostcode());
        assertThat(postcodeService.create(mockInput).getSuburb()).isEqualTo(mockOutput.getSuburb());
    }

    @Test
    public void shouldEditExistingSuburb_WhenFound() {
        NewSuburbWithPostcode mockInput = new NewSuburbWithPostcode("Maidstone", 3012);
        SavedSuburbWithPostcode mockOutput = new SavedSuburbWithPostcode("Maidstone", 3012);

        Suburb mockSuburb = new Suburb("Maidstone", new Postcode(3000));

        given(suburbRepo.findByName("Maidstone")).willReturn(Optional.of(mockSuburb));

        assertThat(postcodeService.create(mockInput)).isInstanceOf(SavedSuburbWithPostcode.class);
        assertThat(postcodeService.create(mockInput).getPostcode()).isEqualTo(mockOutput.getPostcode());
        assertThat(postcodeService.create(mockInput).getSuburb()).isEqualTo(mockOutput.getSuburb());
    }
}
