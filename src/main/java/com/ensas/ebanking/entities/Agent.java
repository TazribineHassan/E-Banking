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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agence_id", referencedColumnName = "id")
    private Agence agence;


    public Agent() {
    }

    public Agent(String cin, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked, String code_agent, Set<Transaction> transactions, Agence agence) {
        super(cin, nom, prenom, email, num_tele, date_naissance, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, username, password, roles, authorities, isActive, isNotLocked);
        this.code_agent = code_agent;
        this.transactions = transactions;
        this.agence = agence;
    }

    public Agent(int id, String cin, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked, String code_agent, Set<Transaction> transactions, Agence agence) {
        super(id, cin, nom, prenom, email, num_tele, date_naissance, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, username, password, roles, authorities, isActive, isNotLocked);
        this.code_agent = code_agent;
        this.transactions = transactions;
        this.agence = agence;
    }

    public String getCode_agent() {
        return code_agent;
    }

    public void setCode_agent(String code_agent) {
        this.code_agent = code_agent;
    }


}
