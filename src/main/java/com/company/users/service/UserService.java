package com.company.users.service;

import com.company.users.dto.Address;
import com.company.users.dto.User;
import com.company.users.entity.UserEntity;
import com.company.users.exception.UserNotFoundException;
import com.company.users.logging.annotation.LogEntryExit;
import com.company.users.mapper.UserDtoToEntityMapper;
import com.company.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;


@Service
public class UserService {

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

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
    public User findById(Long id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        //UserEntity userEntity =userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        UserEntity userEntity = (UserEntity) circuitBreaker.run(() -> userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)), throwable -> getDefaultUser());

        User user = userDtoToEntityMapper.mapEntityToDto(userEntity);

        return user;
    }

    private UserEntity getDefaultUser() {
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
        return userEntity;

    }

    //Implement this
    //https://www.baeldung.com/spring-cloud-netflix-hystrix
    //https://www.baeldung.com/spring-cloud-circuit-breaker
}
