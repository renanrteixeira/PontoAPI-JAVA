package com.controle.ponto.utils;

import com.controle.ponto.config.HashPassword;

public class Password {

    public static String EncodePassword(String password){
        HashPassword hashPassword = new HashPassword();
        String newPassword = hashPassword.EncodePassword(password);

        return newPassword;
    }
}
