package com.applestore.applestore.Security.Oauth2;

import com.applestore.applestore.Entities.AuthenticationProvider;
import com.applestore.applestore.Entities.Role;
import com.applestore.applestore.Repositories.RoleRepository;
import com.applestore.applestore.Repositories.UserRepository;
import com.applestore.applestore.Security.CustomUserDetails;
import com.applestore.applestore.Services.RoleService;
import com.applestore.applestore.Services.UserService;
import com.applestore.applestore.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleService roleService;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        String gmail = user.getAttribute("email");
        String name = user.getAttribute("name");
        User userEntity = userService.findByGmail(gmail);

        if(userEntity == null){
            String f_name = user.getAttribute("family_name");
            String l_name = user.getAttribute("given_name");
            userEntity = userService.createNewUserAfterOAuthlogin(gmail, f_name, l_name, AuthenticationProvider.GOOGLE);
        }else{
            userEntity = userService.updateUserAfterOAuthlogin(userEntity, AuthenticationProvider.GOOGLE);
        }
//        UserEntity updateUserEntity = userService.findByGmail(gmail);
        Set<GrantedAuthority> authorities = new HashSet<>();
        userEntity.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new DefaultOAuth2User(authorities, user.getAttributes(), "sub");
    }
//    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");
//        Optional<UserEntity> userOptional = userRepo.findByGmail(email);
////        UserEntity userEntity = userService.findByGmail(email);
//        UserEntity user ;
//        if (userOptional.isPresent()){
//            user = userOptional.get();
//            user.setAuthProvider(AuthenticationProvider.GOOGLE);
//            userRepo.save(user);
//        } else {
//            user = new UserEntity();
//            user.setUsername(name);
//            user.setPassword(passwordEncoder.encode("12345"));
//            user.setF_name(name);
//            user.setL_name(name);
//            user.setGmail(email);
//            user.setResetPasswordToken(null);
//            user.setAuthProvider(AuthenticationProvider.GOOGLE);
//            Role userRole = roleService.findByName("ROLE_USER").orElseThrow(()-> new RuntimeException("Role Is Not Found"));
//            Collection<Role> roles = new ArrayList<>();
//            roles.add(userRole);
//
//            user.setRoles(roles);
//
//            userRepo.save(user);
//        }
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        // Tạo CustomUserDetails từ UserEntity và authorities
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//        // Trả về một đối tượng có chứa thông tin người dùng và quyền hạn
//        return new DefaultOAuth2User(
//                authorities,
//                customUserDetails.getAttributes(),
//                "email"); // Giả sử email là "nameAttributeKey"
//    }
}
