package com.ensas.ebanking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Banque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String nom;

    private double solde;

    //foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "banque")
    private Set<Agence> agences = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "banque")
    private Admin admin;


    public Banque(){ }

    public Banque(Long id, String nom, float solde) {
        this.id = id;
        this.nom = nom;
        this.solde = solde;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Set<Agence> getAgences() {
        return agences;
    }

    public void setAgences(Set<Agence> agences) {
        this.agences = agences;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
