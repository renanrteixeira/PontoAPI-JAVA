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

    public User findById(String id){
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()){
            return null;
        }

        User userfound = user.get();

        return userfound;
    }

    private String EncodePassword(String password){
        HashPassword hashPassword = new HashPassword();
        String newPassword = hashPassword.EncodePassword(password);

        return newPassword;
    }

    public User postUser(UserRequestDTO data){

        Optional<User> userFound = Optional.ofNullable(repository.findByUsername(data.getUsername()));

        if (userFound.isPresent()){
            return new User(data);
        }

        User user_ = new User(data);
        String password = EncodePassword(data.getPassword());
        user_.setPassword(password);
        repository.save(user_);

        return user_;
    }

    @Transactional
    public User putUser(UserRequestDTO data){
        Optional<User> user = repository.findById(data.getId());
        if (!user.isPresent()){
            data.setId(null);
            return null;
        }

        User newUser = user.get();
        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setStatus(data.getStatus());
        String password = EncodePassword(data.getPassword());
        newUser.setPassword(password);
        newUser.setAdmin(data.getAdmin());

        return newUser;
    }

}
