package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Adresse;
import com.ensas.ebanking.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdresseRepository extends JpaRepository<Adresse,Integer> {


}
