package com.applestore.applestore.DTOs;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int user_id;
    private String username;
    private String password;
    private String gmail;
    private String l_name;
    private String f_name;
    public String toString() {
        return "Username: "+getUsername()+"Password: " + getPassword() + ", gmail: " + getGmail() + ", Lastname: "+getL_name()  + ", Firstname: "+getF_name();
    }
}