package ru.appline.logic;

import java.util.HashMap;
import java.util.Map;

public class PetModel {
    private final static PetModel instance = new PetModel();

    private final Map<Integer, Pet> model;


    public PetModel() {
        model = new HashMap<Integer, Pet>();
        model.put(1, new Pet("Дио", "Брандо", 777));
        model.put(2, new Pet("Пит", "Буль", 5));
        model.put(3, new Pet("Гав", "Гав", 2));
    }

    public static PetModel getInstance() {
        return instance;
    }

    public void addPet(int id, Pet pet) {
        model.put(id, pet);
    }

    public Pet getPetById(int id) {
        return model.get(id);
    }

    public Map<Integer, Pet> getPets() {
        return model;
    }
}
