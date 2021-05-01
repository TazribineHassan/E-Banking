package com.ensas.ebanking.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Agent")
public class Agent extends User  {


    private String code_agent;

    @OneToMany(mappedBy = "agent")
    private Set<Transaction> transactions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agence_id", referencedColumnName = "id")
    private Agence agence;


    public Agent() {
    }

    public Agent(String code_agent) {
        this.code_agent = code_agent;
    }

    public Agent( String cin, String username, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String password, boolean active, String code_agent) {
        super(cin, username, nom, prenom, email, num_tele, date_naissance, password, active);
        this.code_agent = code_agent;
    }


    public String getCode_agent() {
        return code_agent;
    }

    public void setCode_agent(String code_agent) {
        this.code_agent = code_agent;
    }


}
