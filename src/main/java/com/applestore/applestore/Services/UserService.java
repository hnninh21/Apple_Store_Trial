package com.applestore.applestore.Services;

import com.applestore.applestore.DTOs.RegisterDto;
import com.applestore.applestore.DTOs.UserDto;
import com.applestore.applestore.Entities.*;
import com.applestore.applestore.Exception.UserNotFoundException;
import com.applestore.applestore.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

import com.applestore.applestore.DTOs.RegisterDto;
import com.applestore.applestore.DTOs.UserDto;
import com.applestore.applestore.Entities.Role;
import com.applestore.applestore.Entities.User;
import com.applestore.applestore.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepo;
    @Autowired
    public UserService(UserRepository userRepo, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByGmail(String email) {
        User userEntity = userRepo.findUserByGmail(email);
        if(userEntity !=null){
            return userEntity;
        }
        return null;
    }
    public void saveUser(RegisterDto registerDto){

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setL_name(registerDto.getL_name());
        user.setF_name(registerDto.getF_name());
        user.setGmail(registerDto.getGmail());
        Role userRole = roleService.findByName("ROLE_USER").orElseThrow(()-> new RuntimeException("Role Is Not Found"));
        Collection<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepo.save(user);
    }
    public void saveAdmin(RegisterDto registerDto){

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setL_name(registerDto.getL_name());
        user.setF_name(registerDto.getF_name());
        user.setGmail(registerDto.getGmail());
        Role userRole = roleService.findByName("ROLE_ADMIN").orElseThrow(()-> new RuntimeException("Role Is Not Found"));
        Collection<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepo.save(user);
    }
    public UserDto mapToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .user_id(user.getUser_id())
                .username(user.getUsername())
                .password(user.getPassword())
                .l_name(user.getL_name())
                .f_name(user.getF_name())
                .gmail(user.getGmail())
                .build();
        return userDto;
    }

    public UserDto getUserDTOById(int id){
        UserDto userDto = new UserDto();
        User user = userRepo.getReferenceById(id);
        userDto.setUser_id(user.getUser_id());
        userDto.setGmail(user.getGmail());
        userDto.setF_name(user.getF_name());
        userDto.setL_name(user.getL_name());
        return userDto;
    }
    
    public User getUserById(int id){
        User user = userRepo.getReferenceById(id);
        return user;
    }


    
    public void updateResetPasswordToken(String token, String gmail) throws UserNotFoundException {
        User userEntity = userRepo.findUserByGmail(gmail);
        if(userEntity !=null){
            userEntity.setResetPasswordToken(token);
            userRepo.save(userEntity);
        }else {
            throw new UserNotFoundException("Could not find any account with gmail "+gmail);
        }
    }
    public User getByResetPasswordToken(String token){
        return userRepo.findUserByResetPasswordToken(token);
    }

    public void updatePassword(User userEntity, String newPassword){
        String hashedPw = passwordEncoder.encode(newPassword);
        userEntity.setPassword(hashedPw);
        userEntity.setResetPasswordToken(null);

        userRepo.save(userEntity);
    }

    public User createNewUserAfterOAuthlogin(String email, String f_name, String l_name, AuthenticationProvider provider) {
        User userEntity = new User();
        userEntity.setUsername(email);
        userEntity.setPassword(null);
        userEntity.setGmail(email);
        userEntity.setF_name(f_name);
        userEntity.setL_name(l_name);
        Role userRole = roleService.findByName("ROLE_USER").orElseThrow(()-> new RuntimeException("Role Is Not Found"));
        Collection<Role> roles = new ArrayList<>();
        roles.add(userRole);
        userEntity.setRoles(roles);
        userEntity.setAuthProvider(provider);
        userRepo.save(userEntity);
        return userEntity;
    }

    public User updateUserAfterOAuthlogin(User userEntity, AuthenticationProvider authenticationProvider) {
        userEntity.setAuthProvider(authenticationProvider);
        userRepo.save(userEntity);
        return userEntity;
    }
    public User findByUsername(String username) {
        Optional<User> userEntityOptional = userRepo.findByUsername(username);
        return userEntityOptional.orElse(null);
    }
    public void changePassword(String tenTaiKhoan, String matKhauMoi) {
        User user = this.findByUsername(tenTaiKhoan);
        user.setPassword(passwordEncoder.encode(matKhauMoi));
        userRepo.save(user);
    }
}