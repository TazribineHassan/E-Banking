package com.ensas.ebanking.entities;

import com.ensas.ebanking.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Client")
public class Client extends User {

    //foreign keys
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agence_id", referencedColumnName = "id")
    private Agence agence;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_id", referencedColumnName = "id")
    private Compte compte;

    @OneToMany(mappedBy = "client")
    private Set<Transaction> transactions = new HashSet<>();

    public Client() { }

    public Client(String cin, String nom, String prenom, String email, String num_tele, Date date_naissance, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked, String type_client, Agence agence, Compte compte, Set<Transaction> transactions) {
        super(cin, nom, prenom, email, num_tele, date_naissance, lastLoginDate, lastLoginDateDisplay, joinDate, username, password, roles, authorities, isActive, isNotLocked);
        this.type_client = type_client;
        this.agence = agence;
        this.compte = compte;
        this.transactions = transactions;
    }

    public Client(int id, String cin, String nom, String prenom, String email, String num_tele, Date date_naissance, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked, String type_client, Agence agence, Compte compte, Set<Transaction> transactions) {
        super(id, cin, nom, prenom, email, num_tele, date_naissance, lastLoginDate, lastLoginDateDisplay, joinDate, username, password, roles, authorities, isActive, isNotLocked);
        this.type_client = type_client;
        this.agence = agence;
        this.compte = compte;
        this.transactions = transactions;
    }



    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
