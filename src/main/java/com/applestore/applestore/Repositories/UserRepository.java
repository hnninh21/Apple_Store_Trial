package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    User findUserByGmail(String gmail);
    User findUserByResetPasswordToken (String resetPasswordToken);

    Optional<User> findByGmail(String email);
//    UserEntity findByUsername(String tenTaiKhoan);
}