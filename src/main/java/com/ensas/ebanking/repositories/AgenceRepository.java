package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AgenceRepository extends JpaRepository<Agence,Integer> {
    @Override
    Optional<Agence> findById(Integer integer);
}
