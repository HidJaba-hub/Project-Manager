package com.es.projectManager.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "name")
    private String name;
    @Column(name="login")
    private String login;
    @Column(name="password")
    private byte[] password;
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Project> projects;

    public User(){

    }
    public User(String _name, String _login, byte[] _password, UserType _userType){
        name=_name;
        login=_login;
        password=_password;
        type=_userType;
    }
}
