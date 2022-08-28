package main;

import main.model.Person;
import main.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public List<Person> ListPerson() {
        Iterable<Person> personIterable = personRepository.findAll();

        ArrayList<Person> persons = new ArrayList<>();
        for (Person person : personIterable) {
            persons.add(person);
        }
        return persons;
    }

    @PostMapping("/persons")
    public int addPerson(Person person) {
        if (personRepository.count() == 0) {
            person.setId(1);
        }
        // проверить ниже код
        Person newPerson = personRepository.save(person);

        return newPerson.getId();
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<?> getPerson(@PathVariable int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalPerson.get(), HttpStatus.OK);
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<?> dellPerson(@PathVariable int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        personRepository.deleteById(id);
        return new ResponseEntity<>(personRepository.count(), HttpStatus.OK);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<?> putPersonId(Person newPerson, @PathVariable int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
       // нужен код перезаписи
//        Person modifiedPerson = putDeadline(newPerson);
        personRepository.save(newPerson);
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }

    @DeleteMapping("/persons")
    public ResponseEntity dellAllPersons() {
        if (personRepository.count() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The list is already empty.");
        }
        personRepository.deleteAll();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

   /* public static Person putDeadline(Person person) {
        person.setDeadline(person.getYearPerson(), person.getMonthPerson(), person.getDayPerson());
        return person;
    }
*/
}
