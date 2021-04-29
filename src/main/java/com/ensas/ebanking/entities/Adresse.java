package com.ensas.ebanking.entities;

import javax.persistence.*;

@Entity
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String rue;

    private String ville;

    private String code_postal;

    private String pays;

    @OneToOne(mappedBy = "adresse")
    private Agence agence;


    public Adresse() { }

    public Adresse(Long id, String rue, String ville, String code_postal, String pays, Agence agence) {
        this.id = id;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
        this.pays = pays;
        this.agence = agence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
