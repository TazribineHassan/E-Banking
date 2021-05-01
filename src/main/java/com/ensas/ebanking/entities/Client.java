package com.ensas.ebanking.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Client")
public class Client extends User{

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
