package com.ensas.ebanking.repositories;


import com.ensas.ebanking.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {

}
