package com.ensas.ebanking.entities;

import com.ensas.ebanking.domains.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {



    @OneToOne()
    @JoinColumn(name = "banque_id", referencedColumnName = "id")
    private Banque banque;

     private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Admin() {
    }

    public Admin(String cin, String nom, String prenom, String email, String num_tele, Date date_naissance, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked, Banque banque) {
        super(cin, nom, prenom, email, num_tele, date_naissance, lastLoginDate, lastLoginDateDisplay, joinDate, username, password, roles, authorities, isActive, isNotLocked);
        this.banque = banque;
    }

    public Admin(Long id, String cin, String nom, String prenom, String email, String num_tele, Date date_naissance, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked, Banque banque) {
        super(id, cin, nom, prenom, email, num_tele, date_naissance, lastLoginDate, lastLoginDateDisplay, joinDate, username, password, roles, authorities, isActive, isNotLocked);
        this.banque = banque;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }
}
