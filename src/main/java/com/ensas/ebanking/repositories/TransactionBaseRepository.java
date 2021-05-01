package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Transaction;
import org.springframework.data.repository.CrudRepository;


public interface TransactionBaseRepository <T extends Transaction> extends CrudRepository<T,Integer> {
    T getTransactionById(Integer Id);
}
