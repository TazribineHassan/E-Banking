package com.ensas.ebanking.repositories;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Agence;

import com.ensas.ebanking.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);
    Client findByAgence(Agence agence);
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=:x ")
    public List<String> findClientMounth(@Param("x") int a);
  /*  @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=2 ")
    public List<String> findClientMounthFev();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=3 ")
    public List<String> findClientMounthMars();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=4 ")
    public List<String> findClientMounthAvr();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=5 ")
    public List<String> findClientMounthMai();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=6 ")
    public List<String> findClientMounthJuin();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=7 ")
    public List<String> findClientMounthJuill();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=8 ")
    public List<String> findClientMounthAout();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=9 ")
    public List<String> findClientMounthSept();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=10 ")
    public List<String> findClientMounthOct();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=11 ")
    public List<String> findClientMounthNov();
    @Query("SELECT MONTH(c.joinDate) FROM Client c WHERE MONTH(c.joinDate)=12 ")
    public List<String> findClientMounthDec();*/
}
