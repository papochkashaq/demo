package com.alderson.demo.repository;

import java.util.UUID;

import com.alderson.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteUserById(UUID id);

    User findUserById(UUID id);
}
