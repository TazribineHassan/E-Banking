package com.ensas.ebanking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String type_transaction;

    private LocalDate date_transaction;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agent_id", referencedColumnName = "id")
    private Agent agent;


    public Transaction() {
    }

    public Transaction(String type_transaction, LocalDate date_transaction) {
        this.type_transaction = type_transaction;
        this.date_transaction = date_transaction;
    }

    public Transaction(int id, String type_transaction, LocalDate date_transaction) {
        this.id = id;
        this.type_transaction = type_transaction;
        this.date_transaction = date_transaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_transaction() {
        return type_transaction;
    }

    public void setType_transaction(String type_transaction) {
        this.type_transaction = type_transaction;
    }

    public LocalDate getDate_transaction() {
        return date_transaction;
    }

    public void setDate_transaction(LocalDate date_transaction) {
        this.date_transaction = date_transaction;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

}
