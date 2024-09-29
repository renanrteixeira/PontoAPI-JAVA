package com.controle.ponto.services.token;

import com.controle.ponto.config.HashPassword;
import com.controle.ponto.domain.dto.token.TokenRequestDTO;
import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.user.User;
import com.controle.ponto.domain.user.UserAuthenticated;
import com.controle.ponto.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

@Service
public class TokenService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    @Autowired
    JwtService jwtService;

    public String getToken(TokenRequestDTO data){
        User user = repository.findByUsername(data.username());
        if (user == null){
            return null;
        }

        HashPassword hash = new HashPassword();
        boolean isValid = hash.IsValidPassword(data.password(), user.getPassword());
        if (!isValid){
            return null;
        }

        UserAuthenticated userAuthenticated = new UserAuthenticated(user);

        return jwtService.generateToken(userAuthenticated);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

//        HashPassword hash = new HashPassword();
//        boolean isValid = hash.IsValidPassword(data.password(), user.getPassword());
//        if (!isValid){
//            return null;
//        }

        return (UserDetails) user;
    }
}
