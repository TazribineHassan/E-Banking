package com.ensas.ebanking.repositories;

import com.ensas.ebanking.domains.User;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User> {
}
