package com.controle.ponto.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashPassword {

    public String EncodePassword(String data){
        PasswordEncoder passwordEndoder = new BCryptPasswordEncoder();
        String encoded = passwordEndoder.encode(data);

        return encoded;
    }

    public boolean IsValidPassword(String data, String passwordDB){
        PasswordEncoder passwordEndoder = new BCryptPasswordEncoder();
        String encoded = passwordEndoder.encode(data);
        boolean isValid = passwordEndoder.matches(data, passwordDB);

        return isValid;
    }

}
