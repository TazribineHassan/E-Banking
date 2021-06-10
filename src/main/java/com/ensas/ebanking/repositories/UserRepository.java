package com.ensas.ebanking.repositories;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);

}
