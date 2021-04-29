package com.ensas.ebanking.entities;

import com.ensas.ebanking.common.Roles;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Agent extends User{

    private String cin;
    private String code_agent;
    private String  nom;
    private String  prenom;
    private String  phone;
    private String  email;
    private LocalDate date_naissance;

    @OneToMany(mappedBy = "agent")
    private Set<Transaction> transactions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agence_id", referencedColumnName = "id")
    private Agence agence;


    public Agent() {
    }

    public Agent(String username, String password, String roles, boolean active, String cin, String code_agent, String nom, String prenom, String phone, String email, LocalDate date_naissance) {
        super(username, password, roles, active);
        this.cin = cin;
        this.code_agent = code_agent;
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

    public String getCode_agent() {
        return code_agent;
    }

    public void setCode_agent(String code_agent) {
        this.code_agent = code_agent;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
