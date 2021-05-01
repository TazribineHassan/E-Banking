package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Transaction;

import javax.transaction.Transactional;

@Transactional
public interface TransactionRepository extends TransactionBaseRepository <Transaction> {
}
