package main;

import main.model.Person;
import main.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    PersonRepository personRepository;

    @Value("${someParameter}")
    private Integer someParameter;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<Person> personIterable = personRepository.findAll();
        ArrayList<Person> persons = new ArrayList<>();
        for(Person person : personIterable){
            persons.add(person);
        }
        System.out.println("person size" + persons.size());
        model.addAttribute("persons", persons);
        model.addAttribute("personsCount", persons.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }
}
