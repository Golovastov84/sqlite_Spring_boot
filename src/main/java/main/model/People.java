package main.model;

import javax.persistence.*;

@Entity
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // было  SEQUENCE
    private int id;

    private String name;

    private String message;

    public People() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
