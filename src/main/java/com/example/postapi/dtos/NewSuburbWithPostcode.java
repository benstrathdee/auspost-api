package com.example.postapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewSuburbWithPostcode {
    @NotBlank
    private String suburb;

    @NotNull
    private Integer postcode;
}
