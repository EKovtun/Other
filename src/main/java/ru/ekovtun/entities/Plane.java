package ru.ekovtun.entities;

import javax.persistence.*;

@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String model;

    protected Plane() {}

    public Plane(String model) {
        this.model = model;
    }
}
