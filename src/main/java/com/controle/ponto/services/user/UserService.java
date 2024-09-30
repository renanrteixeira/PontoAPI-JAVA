package com.controle.ponto.services.user;

import com.controle.ponto.config.HashPassword;
import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.user.User;
import com.controle.ponto.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    private String EncodePassword(String password){
        HashPassword hashPassword = new HashPassword();
        String newPassword = hashPassword.EncodePassword(password);

        return newPassword;
    }

    public User postUser(UserRequestDTO data){

        Optional<User> userFound = Optional.ofNullable(repository.findByUsername(data.username()));

        if (userFound.isPresent()){
            return new User(data);
        }

        User user_ = new User(data);
        String password = EncodePassword(data.password());
        user_.setPassword(password);
        repository.save(user_);

        return user_;
    }

    @Transactional
    public User putUser(UserRequestDTO data){
        Optional<User> user = repository.findById(data.id());
        if (user.isPresent()){
            User newUser = user.get();
            newUser.setName(data.name());
            newUser.setEmail(data.email());
            newUser.setStatus(data.status());
            String password = EncodePassword(data.password());
            newUser.setPassword(password);
            newUser.setAdmin(data.admin());

            return newUser;
        }

        return new User();
    }

}
