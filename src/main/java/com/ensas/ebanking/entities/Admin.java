package com.ensas.ebanking.entities;

import com.ensas.ebanking.domains.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banque_id", referencedColumnName = "id")
    private Banque banque;


    public Admin() {
    }

    public Admin(String cin, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, String username, String password, String[] roles, String[] authorities, boolean isActive, boolean isNotLocked, Banque banque) {
        super(cin, nom, prenom, email, num_tele, date_naissance, profileImageUrl, lastLoginDate, lastLoginDateDisplay, username, password, roles, authorities, isActive, isNotLocked);
        this.banque = banque;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }
}
