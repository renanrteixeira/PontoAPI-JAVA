package com.controle.ponto.business.user;

import static com.controle.ponto.resources.utils.Password.EncodePassword;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.user.UserNotFoundException;
import com.controle.ponto.domain.mappers.user.UserMapper;
import com.controle.ponto.domain.entity.user.User;
import com.controle.ponto.persistence.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Component
public class UserBusiness {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> findAll(){
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> usersDTO = new ArrayList<>();

        for (User user: users){
            UserResponseDTO responseDTO = UserMapper.INSTANTE.toResponseDTO(user);
            usersDTO.add(responseDTO);
        }

        return usersDTO;
    }

    public Page<UserResponseDTO> findAllPaginated(Pageable pageable){
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(UserMapper.INSTANTE::toResponseDTO);
    }

    public User findByUsername(String login){

        return userRepository.findByUsername(login);
    }

    public UserResponseDTO findById(String id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }

        User userfound = user.get();

        return UserMapper.INSTANTE.toResponseDTO(userfound);
    }

    public UserResponseDTO post(UserRequestDTO data){

        Optional<User> userFound = Optional.ofNullable(userRepository.findByUsername(data.getUsername()));

        if (userFound.isPresent()){
            throw new BadRequestCustomException("Usuário já cadastrado.");
        }

        User user = UserMapper.INSTANTE.toResquestEntity(data);
        String password = EncodePassword(data.getPassword());
        user.setPassword(password);
        userRepository.save(user);

        return UserMapper.INSTANTE.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO put(UserRequestDTO data){
        Optional<User> user = userRepository.findById(data.getId());
        if (user.isEmpty()){
            throw new UserNotFoundException();
        }

        User newUser = user.get();
        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setStatus(data.getStatus());
        String password = EncodePassword(data.getPassword());
        newUser.setPassword(password);
        newUser.setAdmin(data.getAdmin());

        return UserMapper.INSTANTE.toResponseDTO(newUser);
    }

    public UserResponseDTO patch(UserRequestDTO data, String id){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (data.getName() != null) {
            user.setName(data.getName());
        }
        if (data.getEmail() != null) {
            user.setEmail(data.getEmail());
        }
        if (data.getPassword() != null) {
            user.setPassword(EncodePassword(data.getPassword()));
        }
        if (data.getAdmin() != null) {  // Assuming null means not provided for patch
            user.setAdmin(data.getAdmin());
        }
        if (data.getStatus() != null) {
            user.setStatus(data.getStatus());
        }

        userRepository.save(user);

        return UserMapper.INSTANTE.toResponseDTO(user);
    }
}
