package com.ensas.ebanking.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Versement  extends  Transaction{
    private double Montant;
    private String cin_verseur;
    private String nom_verseur;
    private String num_compte;

    public Versement() {
    }

    public Versement(int id, String type_transaction, LocalDate date_transaction, double montant, String cin_verseur, String nom_verseur, String num_compte) {
        super(id, type_transaction, date_transaction);
        Montant = montant;
        this.cin_verseur = cin_verseur;
        this.nom_verseur = nom_verseur;
        this.num_compte = num_compte;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }

    public String getCin_verseur() {
        return cin_verseur;
    }

    public void setCin_verseur(String cin_verseur) {
        this.cin_verseur = cin_verseur;
    }

    public String getNom_verseur() {
        return nom_verseur;
    }

    public void setNom_verseur(String nom_verseur) {
        this.nom_verseur = nom_verseur;
    }

    public String getNum_compte() {
        return num_compte;
    }

    public void setNum_compte(String num_compte) {
        this.num_compte = num_compte;
    }
}
