package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Payment;

import javax.transaction.Transactional;

@Transactional
public interface PaymentRepository extends  TransactionBaseRepository<Payment>{
}
