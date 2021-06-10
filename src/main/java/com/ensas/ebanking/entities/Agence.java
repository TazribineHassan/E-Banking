package com.ensas.ebanking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //foreign keys
    @ManyToOne()
    @JoinColumn(name = "banque_id", referencedColumnName = "id")
    private Banque banque;

    @OneToOne(mappedBy = "agence")
    private Agent agent;

    @OneToOne()
    @JoinColumn(name = "adresse_id", referencedColumnName = "id")
    private Adresse adresse;

    @OneToMany(mappedBy = "agence")
    @JsonIgnore
    private Set<Client> clients = new HashSet<>();


    public Agence(){ }

    public Agence(Long id, String code, String nom, String horaire_debut, String horaire_fin, String num_tele, Banque banque, Adresse adresse) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.horaire_debut = horaire_debut;
        this.horaire_fin = horaire_fin;
        this.num_tele = num_tele;
        this.banque = banque;
        this.adresse = adresse;
    }

    public Agence(Long id, String code, String nom, String horaire_debut, String horaire_fin, String num_tele, Agent agent, Adresse adresse, Set<Client> clients) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.horaire_debut = horaire_debut;
        this.horaire_fin = horaire_fin;
        this.num_tele = num_tele;
        this.agent = agent;
        this.adresse = adresse;
        this.clients = clients;
    }

    public Agence(String code, String nom, String horaire_debut, String horaire_fin, String num_tele, Banque banque, Adresse adresse) {
        this.code = code;
        this.nom = nom;
        this.horaire_debut = horaire_debut;
        this.horaire_fin = horaire_fin;
        this.num_tele = num_tele;
        this.banque = banque;
        this.adresse = adresse;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

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

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
