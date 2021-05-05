package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TransactionBaseRepository <T extends Transaction> extends CrudRepository<T,Integer> {
    @Override
    Optional<T> findById(Integer integer);
}
