package com.company.users.service;

import com.company.users.dto.User;
import com.company.users.entity.UserEntity;
import com.company.users.exception.UserNotFoundException;
import com.company.users.logging.annotation.LogEntryExit;
import com.company.users.mapper.UserDtoToEntityMapper;
import com.company.users.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;


@Service
public class UserService {
    private final String findById = "findById";

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDtoToEntityMapper userDtoToEntityMapper;

    @LogEntryExit(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public User createUser(User user) {
        UserEntity userEntity = userDtoToEntityMapper.mapDtoToEntity(user);
        userRepository.save(userEntity);
        user.setId(userEntity.getId());
        return user;
    }

    @LogEntryExit(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    public User updateUser(User user, Long id) {
        //Check user exist or not
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setId(id);
        UserEntity userEntity = userDtoToEntityMapper.mapDtoToEntity(user);
        userRepository.save(userEntity);
        return user;
    }

    @Transactional
    @LogEntryExit(showArgs = true, showResult = true, unit = ChronoUnit.MILLIS)
    @CircuitBreaker(name = findById, fallbackMethod = "getDefaultUser")
    public User findById1(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        User user = userDtoToEntityMapper.mapEntityToDto(userEntity);
        return user;
    }

    /**
     * Fallback method
     *
     * @return
     */
    public User getDefaultUser(Long id, java.lang.Throwable throwable) throws Throwable {
        if (throwable instanceof UserNotFoundException) {
            throw throwable; //This is to throw legitimate business errror
        } else { // Below is alternative data return on circuit open
            UserEntity userEntity = new UserEntity();
            userEntity.setTitle("DEFAULT");
            userEntity.setFirstname("FirstName");
            userEntity.setLastname("LastName");
            userEntity.setGender("Male");
            userEntity.setEmpid(2332L);

            userEntity.setCity("sydney");
            userEntity.setStreet("Street");
            userEntity.setState("State");
            userEntity.setPostcode("postcode");
            userEntity.setId(1L);
            User user = userDtoToEntityMapper.mapEntityToDto(userEntity);

            return user;
        }

    }
}
