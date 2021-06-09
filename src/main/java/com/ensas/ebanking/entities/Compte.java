package com.ensas.ebanking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String numCompte;

    private double solde;

    @JsonIgnore
    @OneToOne(mappedBy = "compte")
    private Client client;

    public Compte() { }

    public Compte(Long id, String num_compte, double solde) {
        this.id = id;
        this.numCompte = num_compte;
        this.solde = solde;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String num_compte) {
        this.numCompte = num_compte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
