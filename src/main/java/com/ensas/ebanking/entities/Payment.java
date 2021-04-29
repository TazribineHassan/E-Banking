package com.ensas.ebanking.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Payment extends Transaction{
    private String code_facture;
    private double montant_facture;

    public Payment() {
    }

    public Payment(String type_transaction, LocalDate date_transaction, String code_facture, double montant_facture) {
        super(type_transaction, date_transaction);
        this.code_facture = code_facture;
        this.montant_facture = montant_facture;
    }

    public String getCode_facture() {
        return code_facture;
    }

    public void setCode_facture(String code_facture) {
        this.code_facture = code_facture;
    }

    public double getMontant_facture() {
        return montant_facture;
    }

    public void setMontant_facture(double montant_facture) {
        this.montant_facture = montant_facture;
    }
}
