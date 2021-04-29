package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Client;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
public interface ClientRepository extends UserBaseRepository<Client> {
}
