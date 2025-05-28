package com.controle.ponto.services.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.domain.entity.user.User;
import com.controle.ponto.interfaces.IService;
import com.controle.ponto.business.user.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IService<UserRequestDTO, UserResponseDTO> {

    @Autowired
    private UserBusiness userBusiness;

    public List<UserResponseDTO> findAll(){
        return userBusiness.findAll();
    }

    public User findByUsername(String login){
        return userBusiness.findByUsername(login);
    }

    public UserResponseDTO findById(String id){
        return userBusiness.findById(id);
    }

    public UserResponseDTO post(UserRequestDTO data){
        return userBusiness.post(data);
    }

    public UserResponseDTO put(UserRequestDTO data){
        return userBusiness.put(data);
    }

    public UserResponseDTO patch(UserRequestDTO data, String id){
        return userBusiness.patch(data, id);
    }

}
