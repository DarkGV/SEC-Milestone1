package pt.ulisboa.tecnico.milestone1.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
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
}