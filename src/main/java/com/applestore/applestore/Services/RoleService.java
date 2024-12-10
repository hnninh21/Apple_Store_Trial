package com.applestore.applestore.Services;

import com.applestore.applestore.DTOs.RegisterDto;
import com.applestore.applestore.DTOs.UserDto;
import com.applestore.applestore.Entities.Role;
import com.applestore.applestore.Entities.User;
import com.applestore.applestore.Repositories.RoleRepository;
import com.applestore.applestore.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {
    private RoleRepository roleRepo;

    @Autowired
    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }
    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }

}