package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.User;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User> {
}
