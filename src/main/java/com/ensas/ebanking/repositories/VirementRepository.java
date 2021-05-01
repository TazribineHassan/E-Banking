package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Virement;

import javax.transaction.Transactional;

@Transactional
public interface VirementRepository extends TransactionBaseRepository <Virement> {
}
