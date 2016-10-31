package com.springboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "phones")
public class Phones implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Num")
    private long id;

    @NotNull
    @Column(name= "name")
    @Size(min = 4, message="Длина имени должна быть больше четырех")
    @JsonProperty("Name")
    private String name;

    @NotNull
    @Column(name= "surname")
    @Size(min = 4, message="Длина фамилии должна быть больше четырех")
    @JsonProperty("Surname")
    private String surname;

    @NotNull
    @Column(name= "middlename")
    @Size(min = 4, message="Длина отчества должна быть больше четырех")
    @JsonProperty("Middlename")
    private String middlename;

    @NotNull
    @Column(name= "phonemob")
    @Pattern(regexp = "^[\\+380]{4}[\\(]\\d{2}[\\)]\\d{7}$", message = "не верно задан номер мобильного")
    @JsonProperty("Mobail")
    private String phonemob;

    @Column(name= "phonehome")
    @Pattern(regexp = "^[\\+380]{4}[\\(]\\d{2}[\\)]\\d{7}$", message = "не верно задан номер телефона")
    @JsonProperty("Phone")
    private String phonehome;

    @Column(name= "address")
    @JsonProperty("Address")
    private String address;

    @Column(name= "email")
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$", message = "не верно задан email")
    @JsonProperty("Email")
    private String email;

    @Column(name= "users_id")
    private long usersId;

    public Phones() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getPhonemob() {
        return phonemob;
    }

    public String getPhonehome() {
        return phonehome;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setPhonemob(String phonemob) {
        this.phonemob = phonemob;
    }

    public void setPhonehome(String phonehome) {
        this.phonehome = phonehome;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUsersId() {
        return usersId;
    }

    public void setUsersId(long usersId) {
        this.usersId = usersId;
    }
}
