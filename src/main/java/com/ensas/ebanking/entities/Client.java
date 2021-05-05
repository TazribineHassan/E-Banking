package com.ensas.ebanking.entities;

import com.ensas.ebanking.domains.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Client")
public class Client extends User {

    private String type_client;


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

    public Client(String cin, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, String username, String password, String[] roles, String[] authorities, boolean isActive, boolean isNotLocked, String type_client, Agence agence, Compte compte, Set<Transaction> transactions) {
        super(cin, nom, prenom, email, num_tele, date_naissance, profileImageUrl, lastLoginDate, lastLoginDateDisplay, username, password, roles, authorities, isActive, isNotLocked);
        this.type_client = type_client;
        this.agence = agence;
        this.compte = compte;
    }

    public String getType_client() {
        return type_client;
    }

    public void setType_client(String type_client) {
        this.type_client = type_client;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }
}
