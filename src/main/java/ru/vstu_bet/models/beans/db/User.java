package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login", unique = true)
    private String login;
    private String password;
    private String telephone;
    private String fio;
    private String photo;
    private Double score;


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public User(String login, String password, String telephone, String fio, String photo, Double score) {
        this.login = login;
        this.password = password;
        this.telephone = telephone;
        this.fio = fio;
        this.photo = photo;
        this.score = score;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User(String login, String password, String telephone, String fio, String photo) {
        this.login = login;
        this.password = password;
        this.telephone = telephone;
        this.fio = fio;
        this.photo = photo;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }



    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }
}
