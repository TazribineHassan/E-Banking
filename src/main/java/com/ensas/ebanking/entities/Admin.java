package com.ensas.ebanking.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banque_id", referencedColumnName = "id")
    private Banque banque;


    public Admin() {
    }

    public Admin(String cin, String username, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String password, boolean active) {
        super(cin, username, nom, prenom, email, num_tele, date_naissance, password, active);
    }



    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }
}
