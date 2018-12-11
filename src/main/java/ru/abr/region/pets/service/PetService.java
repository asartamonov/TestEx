package ru.abr.region.pets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abr.region.pets.domain.Pet;
import ru.abr.region.pets.repository.PetRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Optional<Pet> getPetById(UUID id) {
        return petRepository.findById(id);
    }

    public void saveAll(List<Pet> pets) {
        pets.forEach(p -> petRepository.saveAndFlush(p));
    }
}

