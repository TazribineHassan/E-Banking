package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TransactionBaseRepository<T extends Transaction> extends CrudRepository<T, Integer> {
    @Override
    <S extends T> S save(S s);
}
