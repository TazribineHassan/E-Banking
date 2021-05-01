package com.ensas.ebanking.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type_User",discriminatorType = DiscriminatorType.STRING,length = 5)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cin;
    private String username;
    private String nom;
    private String prenom;
    private String email;
    private String num_tele;
    private LocalDate date_naissance;
    private String password;
    private boolean active;

    public User() {
    }

    public User(String cin, String username, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String password, boolean active) {
        this.cin = cin;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num_tele = num_tele;
        this.date_naissance = date_naissance;
        this.password = password;
        this.active = active;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_tele() {
        return num_tele;
    }

    public void setNum_tele(String num_tele) {

        this.num_tele = num_tele;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
