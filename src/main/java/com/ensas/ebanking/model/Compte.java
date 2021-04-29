package com.ensas.ebanking.model;

import javax.persistence.*;

@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String num_compte;

    private String solde;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum_compte() {
        return num_compte;
    }

    public void setNum_compte(String num_compte) {
        this.num_compte = num_compte;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }
}
