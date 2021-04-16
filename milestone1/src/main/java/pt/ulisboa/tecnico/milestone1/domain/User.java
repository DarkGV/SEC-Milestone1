package pt.ulisboa.tecnico.milestone1.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(generator = "UserSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "UserSequence", sequenceName = "USER_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}