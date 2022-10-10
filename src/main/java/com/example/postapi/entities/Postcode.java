package com.example.postapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "postcodes")
public class Postcode {
    @Id
    private Integer number;

    @OneToMany(
            mappedBy = "postcode",
            orphanRemoval = true,
            cascade = CascadeType.MERGE
    )
    private List<Suburb> suburbs;

    public Postcode(Integer number) {
        this.number = number;
    }
}
