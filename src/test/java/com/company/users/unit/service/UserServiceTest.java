package com.company.users.unit.service;

import com.company.users.dto.User;
import com.company.users.entity.UserEntity;
import com.company.users.mapper.UserDtoToEntityMapper;
import com.company.users.repository.UserRepository;
import com.company.users.service.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    CircuitBreakerFactory circuitBreakerFactory;

    @InjectMocks
    UserDtoToEntityMapper userDtoToEntityMapper;

    @BeforeEach
    public void init(){
        ReflectionTestUtils.setField(userService,"userDtoToEntityMapper",userDtoToEntityMapper);
    }

    @Test
    public void testFindById(){
        EasyRandom easyRandom = new EasyRandom();
        UserEntity userEntity = easyRandom.nextObject(UserEntity.class);

        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));
        User user = userService.findById1(1l);
        Assert.assertEquals("firstname",user.getFirstname(),userEntity.getFirstname());
        Assert.assertEquals("lastname",user.getLastname(),userEntity.getLastname());
        Assert.assertEquals("empId",user.getEmpid(),userEntity.getEmpid());

    }

    @Test
    public void updateUser(){
        EasyRandom easyRandom = new EasyRandom();
        User user = easyRandom.nextObject(User.class);
        UserEntity userEntity = userDtoToEntityMapper.mapDtoToEntity(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);
        user = userService.updateUser(user,1l);
        Assert.assertEquals("firstname",user.getFirstname(),userEntity.getFirstname());
        Assert.assertEquals("lastname",user.getLastname(),userEntity.getLastname());
        Assert.assertEquals("empId",user.getEmpid(),userEntity.getEmpid());

    }
}
