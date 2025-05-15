package com.controle.ponto.resources.utils;

import com.controle.ponto.resources.config.HashPassword;

public class Password {

    public static String EncodePassword(String password){
        HashPassword hashPassword = new HashPassword();
        String newPassword = hashPassword.EncodePassword(password);

        return newPassword;
    }
}
