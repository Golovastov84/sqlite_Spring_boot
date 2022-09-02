package main;

import main.model.People;
import main.model.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    PeopleRepository peopleRepository;

    @Value("${someParameter}")
    private Integer someParameter;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<People> peopleIterable = peopleRepository.findAll();
        ArrayList<People> peoples = new ArrayList<>();
        for(People people : peopleIterable){
            peoples.add(people);
        }
        model.addAttribute("peoples", peoples);
        model.addAttribute("peoplesCount", peoples.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }
}
