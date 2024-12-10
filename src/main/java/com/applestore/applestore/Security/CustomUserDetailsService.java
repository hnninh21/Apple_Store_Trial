package com.applestore.applestore.Security;

import com.applestore.applestore.Entities.User;
import com.applestore.applestore.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(u -> {
                    System.out.println("GETROLE() in CUDS: "+u.getRoles());
                    CustomUserDetails cus = new CustomUserDetails(u);
                    System.out.println("USERNAME: "+cus.getUsername());
                    System.out.println("ROLE: "+cus.getAuthorities());
                    return cus;
                })
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}
