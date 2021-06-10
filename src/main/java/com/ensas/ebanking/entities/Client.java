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
}
