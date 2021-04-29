package com.ensas.ebanking.model;

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

    private float solde;

    //foreign keys
    @JsonIgnore
    @OneToMany(mappedBy = "banque")
    private Set<Agence> agences = new HashSet<>();


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

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public Set<Agence> getAgences() {
        return agences;
    }

    public void setAgences(Set<Agence> agences) {
        this.agences = agences;
    }
}
