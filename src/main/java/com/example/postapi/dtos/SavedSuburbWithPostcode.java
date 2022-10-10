package com.example.postapi.dtos;

import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
public class SavedSuburbWithPostcode extends NewSuburbWithPostcode {
    private String suburb;
    private Integer postcode;

    public SavedSuburbWithPostcode(@NotBlank String suburb, @NotNull Integer postcode) {
        super(suburb, postcode);
    }
}
