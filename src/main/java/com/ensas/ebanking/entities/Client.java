package com.ensas.ebanking.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client extends User{
    private String cin;
    private String  nom;
    private String  prenom;
    private String  email;
    private String  phone;
    private String type_client;
    private String date_naissance;

    //foreign keys
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agence_id", referencedColumnName = "id")
    private Agence agence;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_id", referencedColumnName = "id")
    private Compte compte;

    @OneToMany(mappedBy = "client")
    private Set<Transaction> transactions = new HashSet<>();

    public Client() { }

    public Client(String username, String password, String roles, boolean active, String cin, String nom, String prenom, String email, String phone, String type_client, String date_naissance) {
        super(username, password, roles, active);
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.type_client = type_client;
        this.date_naissance = date_naissance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getType_client() {
        return type_client;
    }

    public void setType_client(String type_client) {
        this.type_client = type_client;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
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

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }
}
