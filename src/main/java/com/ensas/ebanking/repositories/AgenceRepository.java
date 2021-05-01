package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence,Integer> {
}
