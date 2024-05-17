package com.javaqr.app_qr;

import java.util.Optional;

import models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findById(@Param("id") Integer id);

}