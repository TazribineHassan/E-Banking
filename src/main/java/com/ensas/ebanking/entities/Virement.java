package com.ensas.ebanking.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Virement extends Transaction{
    private double montant;
    private String num_compte_source;
    private String num_compte_beneficiaire;

    public Virement() {
    }

    public Virement(int id, String type_transaction, LocalDate date_transaction, double montant, String num_compte_source, String num_compte_beneficiaire) {
        super(id, type_transaction, date_transaction);
        this.montant = montant;
        this.num_compte_source = num_compte_source;
        this.num_compte_beneficiaire = num_compte_beneficiaire;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNum_compte_source() {
        return num_compte_source;
    }

    public void setNum_compte_source(String num_compte_source) {
        this.num_compte_source = num_compte_source;
    }

    public String getNum_compte_beneficiaire() {
        return num_compte_beneficiaire;
    }

    public void setNum_compte_beneficiaire(String num_compte_beneficiaire) {
        this.num_compte_beneficiaire = num_compte_beneficiaire;
    }
}
