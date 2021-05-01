package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.Admin;

import javax.transaction.Transactional;

@Transactional
public interface AdminRepository extends UserBaseRepository<Admin> {
}
