package org.gnori.testtaskdigitalbudget.converter.impl;

import org.gnori.testtaskdigitalbudget.model.dto.UserDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Unit test for UserConverter")
class UserConverterTest {

  private final UserConverter converter = new UserConverter();
  private UserEntity rawUserEntity;
  private UserDto rawUserDto;

  @BeforeEach
  void setUp() {
    rawUserEntity = UserEntity.builder()
        .username("usernameEntity")
        .email("emailEntity")
        .name("nameEntity")
        .build();
    rawUserEntity.setId(1);

    rawUserDto = UserDto.builder()
        .id(2)
        .username("usernameDto")
        .email("emailDto")
        .name("nameDto")
        .build();
  }

  @Test
  void convertFromUserDto() {

    var actualEntity = converter.convertFrom(rawUserDto);

    Assertions.assertEquals(rawUserDto.getUsername(), actualEntity.getUsername());
    Assertions.assertEquals(rawUserDto.getEmail(), actualEntity.getEmail());
    Assertions.assertEquals(rawUserDto.getName(), actualEntity.getName());
    Assertions.assertNotEquals(rawUserDto.getId(), actualEntity.getId());
  }

  @Test
  void ConvertFromUserEntity() {

    var actualDto = converter.convertFrom(rawUserEntity);

    Assertions.assertEquals(rawUserEntity.getUsername(), actualDto.getUsername());
    Assertions.assertEquals(rawUserEntity.getEmail(), actualDto.getEmail());
    Assertions.assertEquals(rawUserEntity.getName(), actualDto.getName());
    Assertions.assertEquals(rawUserEntity.getId(), actualDto.getId());
  }
}