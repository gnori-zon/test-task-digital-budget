package org.gnori.testtaskdigitalbudget.service.access.storage.impl;

import java.util.Optional;
import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.converter.impl.UserConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.ConflictException;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.UserDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Unit test for UserService")
class UserServiceTest {

  private final UserDao userDaoMock;
  private final BaseConverter<UserDto, UserEntity> userConverter;
  private final UserService userService;

  private UserDto userDto;

  UserServiceTest() {
    userDaoMock = Mockito.mock(UserDao.class);
    userConverter = new UserConverter();
    this.userService = new UserService(userDaoMock, userConverter);
  }

  @BeforeEach
  void setUp() {
    userDto = UserDto.builder()
        .username("username")
        .email("email@mail.com")
        .name("name")
        .build();
  }

  @Test
  void createFromUserDtoSuccess() {

    var userEntity = userConverter.convertFrom(userDto);

    Mockito.when(userDaoMock.existsByUsername(userDto.getUsername())).thenReturn(false);
    Mockito.when(userDaoMock.existsByEmail(userDto.getEmail())).thenReturn(false);
    Mockito.when(userDaoMock.saveAndFlush(Mockito.any())).thenReturn(userEntity);


    var actualDto = userService.createFromUserDto(userDto);

    Assertions.assertEquals(userDto, actualDto);
    Mockito.verify(userDaoMock).saveAndFlush(Mockito.any());
  }

  @Test
  void createFromUserDtoSuccessWhenEmailIsExist() {

    Mockito.when(userDaoMock.existsByUsername(userDto.getUsername())).thenReturn(false);
    Mockito.when(userDaoMock.existsByEmail(userDto.getEmail())).thenReturn(true);

    Assertions.assertThrows(
        ConflictException.class, () -> userService.createFromUserDto(userDto)
    );
  }

  @Test
  void createFromUserDtoSuccessWhenUsernameIsExist() {

    Mockito.when(userDaoMock.existsByUsername(userDto.getUsername())).thenReturn(true);

    Assertions.assertThrows(
        ConflictException.class, () -> userService.createFromUserDto(userDto)
    );
  }

  @Test
  void getUserDtoByIdSuccess() {

    var userEntity = userConverter.convertFrom(userDto);
    userEntity.setId(1);
    userDto.setId(userEntity.getId());

    Mockito.when(userDaoMock.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));

    var actualDto = userService.getUserDtoById(userEntity.getId());

    Assertions.assertEquals(userDto, actualDto);
  }

  @Test
  void getUserDtoByIdWhenIdIsNotExist() {

    var userId = 1;

    Mockito.when(userDaoMock.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class, () -> userService.getUserDtoById(userId)
    );
  }

  @Test
  void updateByIdFromUserDtoSuccess() {

    var userEntity = userConverter.convertFrom(userDto);
    userEntity.setId(1);
    userDto.setId(userEntity.getId());
    userDto.setUsername("newUsername");

    Mockito.when(userDaoMock.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
    Mockito.when(userDaoMock.existsByUsername(userDto.getUsername())).thenReturn(false);
    Mockito.when(userDaoMock.saveAndFlush(Mockito.any())).thenReturn(userEntity);

    var actualDto = userService.updateByIdFromUserDto(userEntity.getId(), userDto);

    Assertions.assertEquals(userDto, actualDto);
  }

  @Test
  void updateByIdFromUserDtoWhenUserIsNotExist() {

    var userId = 1;

    Mockito.when(userDaoMock.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class, () -> userService.updateByIdFromUserDto(userId, userDto)
    );
  }

  @Test
  void updateByIdFromUserDtoWhenUsernameIsExist() {

    var userEntity = userConverter.convertFrom(userDto);
    userEntity.setId(1);
    userDto.setId(userEntity.getId());
    userDto.setUsername("newUsername");

    Mockito.when(userDaoMock.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
    Mockito.when(userDaoMock.existsByUsername(userDto.getUsername())).thenReturn(true);

    Assertions.assertThrows(
        ConflictException.class, () -> userService.updateByIdFromUserDto(userEntity.getId(), userDto)
    );
  }

  @Test
  void getUserEntityByIdSuccess() {

    var userEntity = userConverter.convertFrom(userDto);
    userEntity.setId(1);

    Mockito.when(userDaoMock.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));


    var actualDto = userService.getUserEntityById(userEntity.getId());

    Assertions.assertEquals(userEntity, actualDto);
  }

  @Test
  void getUserEntityByIdWhenIdIsNotExist() {

    var userId = 1;

    Mockito.when(userDaoMock.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class, () -> userService.getUserEntityById(userId)
    );
  }
}