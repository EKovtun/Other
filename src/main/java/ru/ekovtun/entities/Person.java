package ru.ekovtun.entities;

import javax.persistence.*;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = true)
    private String email;

    protected Person() {}

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
