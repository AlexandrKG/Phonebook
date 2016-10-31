package com.springboot.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String login;

    @NotNull
    private String passwd;

    @NotNull
    private String fio;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "users_id")
    protected List<Phones> phonelist = new ArrayList<Phones>();


    public User() { }

    public User(long id) {
        this.id = id;
    }

    public User(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public List<Phones> getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(List<Phones> phonelist) {
        this.phonelist = phonelist;
    }
}
