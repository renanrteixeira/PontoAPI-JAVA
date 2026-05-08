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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Component
public class UserBusiness {

    private static final Logger logger = LoggerFactory.getLogger(UserBusiness.class);

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> findAll(){
        logger.info("Buscando todos os usuários");
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> usersDTO = new ArrayList<>();

        for (User user: users){
            UserResponseDTO responseDTO = UserMapper.INSTANTE.toResponseDTO(user);
            usersDTO.add(responseDTO);
        }

        logger.info("Encontrados {} usuários", usersDTO.size());
        return usersDTO;
    }

    public Page<UserResponseDTO> findAllPaginated(Pageable pageable){
        logger.info("Buscando usuários paginados com {}", pageable);
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(UserMapper.INSTANTE::toResponseDTO);
    }

    public User findByUsername(String login){
        logger.debug("Buscando usuário por username {}", login);
        return userRepository.findByUsername(login);
    }

    public UserResponseDTO findById(String id){
        logger.info("Buscando usuário por ID {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            logger.warn("Usuário não encontrado: {}", id);
            throw new UserNotFoundException();
        }

        User userfound = user.get();
        logger.info("Usuário encontrado: {}", id);

        return UserMapper.INSTANTE.toResponseDTO(userfound);
    }

    public UserResponseDTO post(UserRequestDTO data){
        logger.info("Criando novo usuário {}", data.getUsername());

        Optional<User> userFound = Optional.ofNullable(userRepository.findByUsername(data.getUsername()));

        if (userFound.isPresent()){
            logger.warn("Tentativa de criar usuário duplicado: {}", data.getUsername());
            throw new BadRequestCustomException("Usuário já cadastrado.");
        }

        User user = UserMapper.INSTANTE.toResquestEntity(data);
        String password = EncodePassword(data.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        logger.info("Usuário criado com sucesso");

        return UserMapper.INSTANTE.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO put(UserRequestDTO data){
        logger.info("Atualizando usuário {}", data.getId());
        Optional<User> user = userRepository.findById(data.getId());
        if (user.isEmpty()){
            logger.warn("Usuário não encontrado para atualização: {}", data.getId());
            throw new UserNotFoundException();
        }

        User newUser = user.get();
        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setStatus(data.getStatus());
        String password = EncodePassword(data.getPassword());
        newUser.setPassword(password);
        newUser.setAdmin(data.getAdmin());
        logger.info("Usuário atualizado com sucesso");

        return UserMapper.INSTANTE.toResponseDTO(newUser);
    }

    public UserResponseDTO patch(UserRequestDTO data, String id){
        logger.info("Aplicando patch no usuário {}", id);
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
        logger.info("Patch aplicado com sucesso no usuário {}", id);

        return UserMapper.INSTANTE.toResponseDTO(user);
    }
}
