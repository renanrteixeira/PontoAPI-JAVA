package com.controle.ponto.services.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.domain.entity.user.User;
import com.controle.ponto.interfaces.ICrudService;
import com.controle.ponto.business.user.UserBusiness;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements ICrudService<UserRequestDTO, UserResponseDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserBusiness userBusiness;

    public List<UserResponseDTO> findAll(){
        logger.debug("Delegando busca de todos os usuários");
        return userBusiness.findAll();
    }

    public Page<UserResponseDTO> findAllPaginated(Pageable pageable){
        logger.debug("Delegando busca paginada de usuários com paginação {}", pageable);
        return userBusiness.findAllPaginated(pageable);
    }

    public UserResponseDTO findByUsername(String login){
        logger.debug("Delegando busca de usuário por username {}", login);
        return userBusiness.findByUsername(login);
    }

    public UserResponseDTO findById(String id){
        logger.debug("Delegando busca de usuário por ID {}", id);
        return userBusiness.findById(id);
    }

    public UserResponseDTO post(UserRequestDTO data){
        logger.debug("Delegando criação de usuário {}", data.getUsername());
        return userBusiness.post(data);
    }

    public UserResponseDTO put(UserRequestDTO data){
        logger.debug("Delegando atualização de usuário {}", data.getId());
        return userBusiness.put(data);
    }

    public UserResponseDTO patch(UserRequestDTO data, String id){
        logger.debug("Delegando patch de usuário {} com dados {}", id, data);
        return userBusiness.patch(data, id);
    }

}
