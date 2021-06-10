package com.ensas.ebanking.entities;

import com.ensas.ebanking.domains.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Agent")
public class Agent extends User {


    private String code_agent;

    @OneToMany(mappedBy = "agent")
    private Set<Transaction> transactions = new HashSet<>();

    @OneToOne()
    @JoinColumn(name = "agence_id", referencedColumnName = "id")
    private Agence agence;

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Agent() {
    }



    public String getCode_agent() {
        return code_agent;
    }

    public void setCode_agent(String code_agent) {
        this.code_agent = code_agent;
    }


}
