package com.springboot.sqlite;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    private static int currentId = 1;
    private static final ConcurrentHashMap<Integer, Person> persons = new ConcurrentHashMap<>();

    public static List<Person> getAllPersons() {
        ArrayList<Person> personsList = new ArrayList<>();
        personsList.addAll(persons.values());
        return personsList;
    }

    public static int addPerson(Person person) {
        int id = currentId++;
        person.setId(id);
        persons.put(id, person);
        return id;
    }

    public static int setTask(Task task) {
        int IdTask = task.getId();
        persons.put(IdTask, task);
        return IdTask;
    }

    public static Task getTask(int taskId) {
        if (persons.containsKey(taskId)) {
            return persons.get(taskId);
        }
        return null;
    }

    public static int dellTask(int taskId) {
        if (persons.containsKey(taskId)) {
            persons.remove(taskId);
            return taskId;
        }
        return 0;
    }

    public static int dellAllTask() {
        persons.clear();
        currentId = 1;
        return 0;
    }
}