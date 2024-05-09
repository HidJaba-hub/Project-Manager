package com.es.projectManager.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

@Entity
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="project_id")
    private Integer project_id;
    @Column(name="project_name")
    private String project_name;
    @Column(name="date_start")
    private LocalDate date_start;
    @Column(name="date_end")
    private LocalDate date_end;
    @Column(name = "cost")
    private BigInteger cost;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns =@JoinColumn(name = "user_id"))
    private List<User> users;

    public Project(String name, LocalDate start, LocalDate end, BigInteger _cost){
        project_name=name;
        date_start=start;
        date_end=end;
        cost=_cost;
    }
}
