package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Versement;

import javax.transaction.Transactional;

@Transactional
public interface VersementRepository extends TransactionBaseRepository <Versement> {
}
