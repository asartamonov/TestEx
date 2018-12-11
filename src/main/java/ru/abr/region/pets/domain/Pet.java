package ru.abr.region.pets.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Pets")
public class Pet {

    @Id
    @JsonProperty("id")
    private UUID id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("breed")
    private Breed breed;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("food")
    private Food food;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
