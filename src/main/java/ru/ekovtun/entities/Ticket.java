package ru.ekovtun.entities;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id")
    private Plane plane;

    @Column(name="city_from", length = 100, nullable = false)
    private String cityFrom;

    @Column(name="city_to", length = 100, nullable = false)
    private String cityTo;

    protected Ticket() {}

    public Ticket(Person person, Plane plane, String cityFrom, String cityTo) {
        this.person = person;
        this.plane = plane;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
    }
}
