package com.example.postapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Entity
@Table(name = "suburbs")
public class Suburb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "postcode")
    private Postcode postcode;

    public Suburb(String name) {
        this.name = name;
    }

    public Suburb(String name, Postcode postcode) {
        this.name = name;
        this.postcode = postcode;
    }
}
