package ru.abr.region.pets.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Breed {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "pg-uuid"
    )
    @GenericGenerator(
            name = "pg-uuid",
            strategy = "uuid2",
            parameters = @Parameter(
                    name = "uuid_gen_strategy_class",
                    value = "ru.abr.region.pets.helper.PostgreSQLUUIDGenerationStrategy"
            )
    )
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pets_id")
    @JsonIgnore
    private Pet pet;

    public Breed(String name) {
        this.name = name;
    }

    public Breed() {
    }

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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
