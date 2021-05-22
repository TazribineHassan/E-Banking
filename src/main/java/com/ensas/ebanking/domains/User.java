package com.ensas.ebanking.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type_User",discriminatorType = DiscriminatorType.STRING,length = 5)
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private int id;
    private String cin;
    private String nom;
    private String prenom;
    private String email;
    private String num_tele;
    private LocalDate date_naissance;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    // for spring security.
    private String username;
    private String password;
    private String roles;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;

    public User() {
    }


    public User(int id, String cin, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num_tele = num_tele;
        this.date_naissance = date_naissance;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }


    public User(String cin, String nom, String prenom, String email, String num_tele, LocalDate date_naissance, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String username, String password, String roles, String[] authorities, boolean isActive, boolean isNotLocked) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.num_tele = num_tele;
        this.date_naissance = date_naissance;
        this.profileImageUrl = profileImageUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.joinDate = joinDate;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDateDisplay() {
        return lastLoginDateDisplay;
    }

    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
        this.lastLoginDateDisplay = lastLoginDateDisplay;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNum_tele() {
        return num_tele;
    }

    public void setNum_tele(String num_tele) {

        this.num_tele = num_tele;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
