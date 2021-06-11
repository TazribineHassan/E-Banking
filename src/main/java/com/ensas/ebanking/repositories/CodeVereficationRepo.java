package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.CodeVerefication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeVereficationRepo extends JpaRepository<CodeVerefication, Long> {
    public CodeVerefication findCodeVereficationByUsername(String username);
}
