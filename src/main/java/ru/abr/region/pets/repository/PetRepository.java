package ru.abr.region.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.abr.region.pets.domain.Pet;

import java.util.UUID;

@Repository
@Transactional
public interface PetRepository extends JpaRepository<Pet, UUID> {
}
