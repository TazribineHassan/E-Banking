package com.ensas.ebanking.repositories;

import com.ensas.ebanking.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserBaseRepository <T extends User> extends CrudRepository<T,Integer> {

    @Override
    Optional<T> findById(Integer integer);

    @Override
    Iterable<T> findAll();

    User findUserByUsername(String username);

    @Override
    <S extends T> S save(S s);

    User findUserByEmail(String email);
}
