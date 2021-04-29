package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.User;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;


@Transactional
public interface UserRepository extends UserBaseRepository<User> {
}
