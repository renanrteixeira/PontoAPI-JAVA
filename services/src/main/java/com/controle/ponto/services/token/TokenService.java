package com.controle.ponto.services.token;

import com.controle.ponto.business.token.TokenBusiness;
import com.controle.ponto.domain.dto.token.TokenRequestDTO;
import com.controle.ponto.domain.user.UserAuthenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenBusiness tokenBusiness;

    public String generateToken(UserAuthenticated user){
        return tokenBusiness.generateToken(user);
    }

    public String validateToken(String token){
        return tokenBusiness.validateToken(token);
    }

    public UserAuthenticated getUser(TokenRequestDTO data){
        return tokenBusiness.getUser(data);
    }

}
