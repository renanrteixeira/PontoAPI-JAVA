package com.controle.ponto.business.user;

import static com.controle.ponto.resources.utils.Password.EncodePassword;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.user.UserNotFoundException;
import com.controle.ponto.domain.mappers.user.UserMapper;
import com.controle.ponto.domain.entity.user.User;
import com.controle.ponto.persistence.user.UserRepository;
import com.controle.ponto.resources.utils.MapperConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Component
public class UserBusiness {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperConverter<String> mapperConverter;

    public List<UserResponseDTO> findAll(){
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> usersDTO = new ArrayList<>();

        for (User user: users){
            UserResponseDTO responseDTO = UserMapper.INSTANTE.toResponseDTO(user);
            usersDTO.add(responseDTO);
        }

        return usersDTO;
    }

    public User findByUsername(String login){
        User user = userRepository.findByUsername(login);

        return user;
    }

    public UserResponseDTO findById(String id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
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
        if (!user.isPresent()){
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

        Map<String, Object> fields = mapperConverter.objectMap(data);
        fields.forEach((key, value) ->{
            try {
                Field field = User.class.getDeclaredField(key);
                Class<?> type = field.getType();
                if (!mapperConverter.validKey(field.getName())) {
                    return;
                }

                if (!mapperConverter.validValue(value)){
                    return;
                }

                if (field.getName() == "password") {
                    value = EncodePassword(value.toString());
                }
                field.setAccessible(true);
                if (type == char.class) {
                    char charValue = ((String) value).charAt(0);
                    field.set(user, charValue);
                    return;
                }
                field.set(user, value);
            } catch (Exception e) {
                throw new BadRequestCustomException("Erro ao atualizar campo: " + key);
            }
        });
        userRepository.save(user);

        return UserMapper.INSTANTE.toResponseDTO(user);
    }
}
