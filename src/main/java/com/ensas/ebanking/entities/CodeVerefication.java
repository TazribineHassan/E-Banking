package com.ensas.ebanking.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
public class CodeVerefication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String num_compte_beneficiaire;
    private String num_compte_source;
    private double Montant_virement;
    private String username;
    private String code;
    private Date expirationDate;

    public CodeVerefication(){}

    public CodeVerefication(Long id, String num_compte_beneficiaire, double montant_virement, String username, String code) {
        this.id = id;
        this.num_compte_beneficiaire = num_compte_beneficiaire;
        Montant_virement = montant_virement;
        this.username = username;
        this.code = code;
    }

    public String getNum_compte_beneficiaire() {
        return num_compte_beneficiaire;
    }

    public void setNum_compte_beneficiaire(String num_compte_beneficiaire) {
        this.num_compte_beneficiaire = num_compte_beneficiaire;
    }

    public double getMontant_virement() {
        return Montant_virement;
    }

    public void setMontant_virement(double montant_virement) {
        Montant_virement = montant_virement;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNum_compte_source() {
        return num_compte_source;
    }

    public void setNum_compte_source(String num_compte_source) {
        this.num_compte_source = num_compte_source;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
