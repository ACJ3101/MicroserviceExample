package com.usuario.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.service.entities.User;

@Repository
public interface UserRepository extends JpaRepository< User, Long> {

}
