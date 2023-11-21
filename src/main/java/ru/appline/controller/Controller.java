package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Compass;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final AtomicInteger count = new AtomicInteger(4);
    private static final PetModel model = PetModel.getInstance();
    private static final Compass compass = Compass.getInstance();
    private static final Map<String, String> errorMsg = Collections.singletonMap("error", "invalid data provided");

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return model.getPets();
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Map<String, String> addPet(@RequestBody Pet pet) {
        if (pet.getName() != null && pet.getType() != null && pet.getAge() != null) {
            model.addPet(count.getAndIncrement(), pet);
            return Collections.singletonMap("success", "pet " + pet.getName()
                    + ", type " + pet.getType()
                    + ", age " + pet.getAge()
                    + " successfully created");
        } else return errorMsg;
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public Map<String, String> deletePet(@RequestBody Map<String, Integer> id) {
        if (model.getPets().containsKey(id.get("id"))) {
            model.getPets().remove(id.get("id"));
            return Collections.singletonMap("success", "pet id " + id.get("id") + " deleted");
        } else return errorMsg;
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public Map<String, String> updatePet(@RequestBody Map<String, String> map) {
        if (model.getPets().containsKey(Integer.parseInt(map.get("id")))) {
            model.getPets().put(Integer.parseInt(map.get("id")),
                    new Pet(map.get("name"), map.get("type"), Integer.parseInt(map.get("age"))));
            return Collections.singletonMap("success", "pet id " + map.get("id") +
                    " updated. name: " + model.getPetById(Integer.parseInt(map.get("id"))).getName() +
                    ", type: " + model.getPetById(Integer.parseInt(map.get("id"))).getType() +
                    ", age: " + model.getPetById(Integer.parseInt(map.get("id"))).getAge());
        } else return errorMsg;
    }

    @GetMapping(value = "/get", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return model.getPetById(id.get("id"));
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/addCompassData", consumes = "application/json", produces = "application/json")
    public Map<String, String> addCompassData(@RequestBody Map<String, String> map) {
        try {
            for (Map.Entry entry : map.entrySet()) {
                compass.fillCompass((String) entry.getKey(), (String) entry.getValue());
            }
            return map;
        } catch (NumberFormatException e) {
            return errorMsg;
        }
    }

    @GetMapping(value = "/getByDegree", consumes = "application/json", produces = "application/json")
    public Map<String, String> getByDegree(@RequestBody Map<String, Integer> degreeMap) {
        if (compass.getResults().containsKey(degreeMap.get("degree"))) {
            return Collections.singletonMap("result", compass.getById(degreeMap.get("degree")));
        } else {
            return errorMsg;
        }
    }
}