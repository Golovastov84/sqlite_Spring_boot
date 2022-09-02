package main;

import main.model.People;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    private static int currentId = 1;
    private static final ConcurrentHashMap<Integer, People> persons = new ConcurrentHashMap<>();

    public static List<People> getAllPersons() {
        ArrayList<People> personsList = new ArrayList<>();
        personsList.addAll(persons.values());
        return personsList;
    }

    public static int addPerson(People person) {
        int id = currentId++;
        person.setId(id);
        persons.put(id, person);
        return id;
    }

    public static int setPerson(People person) {
        int IdPerson = person.getId();
        persons.put(IdPerson, person);
        return IdPerson;
    }

    public static People getPerson(int personId) {
        if (persons.containsKey(personId)) {
            return persons.get(personId);
        }
        return null;
    }

    public static int dellPerson(int personId) {
        if (persons.containsKey(personId)) {
            persons.remove(personId);
            return personId;
        }
        return 0;
    }

    public static int dellAllPerson() {
        persons.clear();
        currentId = 1;
        return 0;
    }
}