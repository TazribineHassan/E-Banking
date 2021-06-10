package com.ensas.ebanking.services.imlementations;

import com.ensas.ebanking.domains.User;
import com.ensas.ebanking.entities.Agent;
import com.ensas.ebanking.exceptions.domain.EmailExistException;
import com.ensas.ebanking.exceptions.domain.UserExistExistException;
import com.ensas.ebanking.exceptions.domain.UserNotFoundException;
import com.ensas.ebanking.repositories.AgentRepository;
import com.ensas.ebanking.services.AgentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.ensas.ebanking.constant.UserImplementationConstant.*;

@Service
@Transactional
@Qualifier("AgentDetailsService")
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent addAgent(Agent agent) {
        return (Agent) agentRepository.save(agent);
    }

    @Override
    public Agent findUserByUsername(String username) {
        return (Agent) this.agentRepository.findUserByUsername(username);
    }

    @Override
    public Agent findAgentByID(Long id) {
        return (Agent) this.agentRepository.findById(id).get();
    }

    @Override
    public Agent updateAgent(String current_username, Agent agent) throws UserNotFoundException, UserExistExistException, EmailExistException {
        validateNewUsernameAndEmail(current_username , agent.getUsername(), agent.getEmail());
        return (Agent) this.agentRepository.save(agent);
    }


    private User validateNewUsernameAndEmail(String currentUsername, String username, String  email) throws UserNotFoundException, UserExistExistException, EmailExistException {

        User userByUsername =  agentRepository.findUserByUsername(username);
        User userByEmail = agentRepository.findUserByEmail(email);

        if(StringUtils.isNotBlank(currentUsername)){
            User currentUser = agentRepository.findUserByUsername(currentUsername);
            if(currentUser == null){
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME +  currentUsername);
            }
            if(userByUsername != null && !(currentUser.getId() + "").equals(userByUsername.getId() + "")){
                throw new UserExistExistException(USERNAME_IS_ALREADY_EXIST);
            }
            if(userByEmail != null && !(currentUser.getId() + "").equals(userByEmail.getId() + "")){
                throw new EmailExistException(EMAIL_IS_ALREADY_EXIST);
            }
            return currentUser;
        }else {
            if(userByUsername != null ){
                throw new UserExistExistException(USERNAME_IS_ALREADY_EXIST);
            }
            if(userByEmail != null){
                throw new EmailExistException(EMAIL_IS_ALREADY_EXIST);
            }

            return null;
        }
    }

}
