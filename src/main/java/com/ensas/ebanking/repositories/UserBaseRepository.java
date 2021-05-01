package com.ensas.ebanking.repositories;

import com.ensas.ebanking.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserBaseRepository <T extends User> extends CrudRepository<T,Integer> {
    T getUserById(Integer Id);
    List<T> getAllUsers();
}
