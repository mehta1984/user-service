package com.company.users.mapper;

import com.company.users.dto.Address;
import com.company.users.dto.User;
import com.company.users.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToEntityMapper {

    public UserEntity mapDtoToEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setTitle(user.getTitle());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setGender(user.getGender());
        userEntity.setEmpid(user.getEmpid());

        Address address = user.getAddress();
        userEntity.setCity(address.getCity());
        userEntity.setStreet(address.getStreet());
        userEntity.setState(address.getState());
        userEntity.setPostcode(address.getPostcode());

        return userEntity;
    }

    public User mapEntityToDto(UserEntity userEntity){
        User user = new User();
        user.setTitle(userEntity.getTitle());
        user.setFirstname(userEntity.getFirstname());
        user.setLastname(userEntity.getLastname());
        user.setGender(userEntity.getGender());
        user.setEmpid(userEntity.getEmpid());

        Address address = new Address();
        address.setCity(userEntity.getCity());
        address.setStreet(userEntity.getStreet());
        address.setState(userEntity.getState());
        address.setPostcode(userEntity.getPostcode());

        user.setAddress(address);

        return user;
    }
}
