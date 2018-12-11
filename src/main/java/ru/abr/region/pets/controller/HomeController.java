package ru.abr.region.pets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.abr.region.pets.domain.Pet;
import ru.abr.region.pets.helper.PetNotFoundException;
import ru.abr.region.pets.service.PetService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class HomeController {

    @Autowired
    private PetService petService;

    @RequestMapping(method = RequestMethod.GET, path = "/pet/{petid}")
    public @ResponseBody
    Pet getPet(@PathVariable UUID petid) {
        Optional<Pet> pet = petService.getPetById(petid);
        if (pet.isPresent()) {
            return pet.get();
        } else {
            throw new PetNotFoundException("No pet with given UUID exists");
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/pets")
    public @ResponseBody
    List<Pet> savePets(@RequestBody List<Pet> pets) {
        petService.saveAll(pets);
        return pets;
    }
}
