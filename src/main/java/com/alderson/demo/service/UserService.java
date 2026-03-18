package com.alderson.demo.service;

import java.util.List;
import java.util.UUID;

import com.alderson.demo.model.User;
import com.alderson.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User findUserById(UUID id) {
        return userRepository.findUserById(id);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteUserById(id);
    }
}
