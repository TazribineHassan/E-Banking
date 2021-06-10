package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgenceRepository extends JpaRepository<Agence,Long> {
    
    @Override
    Optional<Agence> findById(Long id);
    List<Agence> findByNom(String nom);
    List<Agence> findByActive(boolean etat);
    
}
