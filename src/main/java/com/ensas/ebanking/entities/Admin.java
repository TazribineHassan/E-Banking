package com.ensas.ebanking.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Admin extends User{

    private String cin;
    private String  nom;
    private String  prenom;
    private String  phone;
    private String  email;
    private LocalDate date_naissance;

    public Admin() {
    }

    public Admin(String username, String password, String roles, boolean active, String cin, String nom, String prenom, String phone, String email, LocalDate date_naissance) {
        super(username, password, roles, active);
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.email = email;
        this.date_naissance = date_naissance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }
}
