package main;

import main.model.People;
import main.model.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@RestController
@Controller
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    public PeopleController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/peoples/")
    public List<People> ListPeople() {
        Iterable<People> peopleIterable = peopleRepository.findAll();

        ArrayList<People> peoples = new ArrayList<>();
        for (People people : peopleIterable) {
            peoples.add(people);
        }
        return peoples;
    }

    @PostMapping("/peoples/")
    @ResponseBody
    public int addPeople(People people) {
        if (peopleRepository.count() == 0) {
//            System.out.println(people.getId());
            // поправить на 1
            people.setId(1);
        }
        // проверить ниже код
        People newPeople = peopleRepository.save(people);

        return newPeople.getId();
    }

    @GetMapping("/peoples/{id}")
    public ResponseEntity<?> getPeople(@PathVariable int id) {
        Optional<People> optionalPeople = peopleRepository.findById(id);
        if (!optionalPeople.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalPeople.get(), HttpStatus.OK);
    }

    @DeleteMapping("/peoples/{id}")
    public ResponseEntity<?> dellPeople(@PathVariable int id) {
        Optional<People> optionalPeople = peopleRepository.findById(id);
        if (!optionalPeople.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        peopleRepository.deleteById(id);
        return new ResponseEntity<>(peopleRepository.count(), HttpStatus.OK);
    }

    @PutMapping("/peoples/{id}")
    @ResponseBody
    public ResponseEntity<?> putPeopleId(People newPeople, @PathVariable int id) {
        Optional<People> optionalPeople = peopleRepository.findById(id);
        if (!optionalPeople.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
       // нужен код перезаписи
//        Person modifiedPerson = putDeadline(newPerson);
        peopleRepository.save(newPeople);
        return new ResponseEntity<>(newPeople, HttpStatus.OK);
    }

    @DeleteMapping("/peoples/")
    public ResponseEntity dellAllPeoples() {
        if (peopleRepository.count() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The list is already empty.");
        }
        peopleRepository.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

   /* public static Person putDeadline(Person person) {
        person.setDeadline(person.getYearPerson(), person.getMonthPerson(), person.getDayPerson());
        return person;
    }
*/
}
