package org.gnori.testtaskdigitalbudget.converter.impl;

import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.model.dto.UserDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements BaseConverter<UserDto, UserEntity> {

  @Override
  public UserDto convertFrom(UserEntity userEntity) {

    return UserDto.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .email(userEntity.getEmail())
        .name(userEntity.getName())
        .build();
  }

  @Override
  public UserEntity convertFrom(UserDto userDto) {

    return UserEntity.builder()
        .username(userDto.getUsername())
        .email(userDto.getEmail())
        .name(userDto.getName())
        .build();
  }

}
