package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Integer> {
    @Override
    <S extends T> S save(S s);

    @Override
    Iterable<T> findAll();
}
