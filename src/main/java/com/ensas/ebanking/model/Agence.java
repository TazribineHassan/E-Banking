package com.ensas.ebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String code;

    private String nom;

    private String horaire_debut;

    private String horaire_fin;

    private String num_tele;

    //foreign keys
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banque_id", referencedColumnName = "id")
    private Banque banque;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adresse_id", referencedColumnName = "id")
    private Adresse adresse;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getHoraire_debut() {
        return horaire_debut;
    }

    public void setHoraire_debut(String horaire_debut) {
        this.horaire_debut = horaire_debut;
    }

    public String getHoraire_fin() {
        return horaire_fin;
    }

    public void setHoraire_fin(String horaire_fin) {
        this.horaire_fin = horaire_fin;
    }

    public String getNum_tele() {
        return num_tele;
    }

    public void setNum_tele(String num_tele) {
        this.num_tele = num_tele;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }
}
