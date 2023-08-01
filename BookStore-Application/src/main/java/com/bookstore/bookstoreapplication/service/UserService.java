package com.bookstore.bookstoreapplication.service;

import com.bookstore.bookstoreapplication.models.Role;
import com.bookstore.bookstoreapplication.models.User;
import com.bookstore.bookstoreapplication.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserServiceInterface{
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    @Override
    @Transactional
    public void makeAdminFromUser(String username){

        userRepository.updateUserRole(username, Role.ADMIN);
    }
}
