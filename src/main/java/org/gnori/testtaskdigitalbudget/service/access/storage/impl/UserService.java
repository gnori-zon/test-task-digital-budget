package org.gnori.testtaskdigitalbudget.service.access.storage.impl;

import org.gnori.testtaskdigitalbudget.converter.BaseConverter;
import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.error.exception.impl.ConflictException;
import org.gnori.testtaskdigitalbudget.error.exception.impl.NotFoundException;
import org.gnori.testtaskdigitalbudget.model.dto.UserDto;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.gnori.testtaskdigitalbudget.service.access.storage.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserEntity, UserDao> {

  private final BaseConverter<UserDto, UserEntity> userConverter;

  protected UserService(UserDao dao, BaseConverter<UserDto, UserEntity> userConverter) {
    super(dao);
    this.userConverter = userConverter;
  }

  public UserDto createFromUserDto(UserDto userDto) {

    var userEntity = userConverter.convertFrom(userDto);

    checkUsernameForUniqueness(userEntity.getUsername());
    checkEmailForUniqueness(userEntity.getEmail());

    userEntity = create(userEntity);

    return userConverter.convertFrom(userEntity);
  }

  public UserDto getUserDtoById(Integer userId) {

    var userEntity = checkForExistAndGetById(userId);

    return userConverter.convertFrom(userEntity);
  }

  public UserDto updateByIdFromUserDto(Integer userId, UserDto userDto) {

    var userEntity = checkForExistAndGetById(userId);

    if (userDto.getUsername() != null &&
        !userEntity.getUsername().equals(userDto.getUsername())) {

      checkUsernameForUniqueness(userDto.getUsername());
      userEntity.setUsername(userDto.getUsername());
    }
    if (userDto.getName() != null) {
      userEntity.setName(userDto.getName());
    }
    update(userId, userEntity);

    return userConverter.convertFrom(userEntity);
  }

  public UserEntity getUserEntityById(Integer userId) {

    return checkForExistAndGetById(userId);
  }

  private UserEntity checkForExistAndGetById(Integer userId) {

    return get(userId)
        .orElseThrow(
            () -> new NotFoundException(
                String.format("user with id: %d is not exist", userId))
        );
  }

  private void checkUsernameForUniqueness(String username) {

    if (dao.existsByUsername(username)) {
      throw new ConflictException(
          String.format("user with username: %s is already exist", username));
    }
  }

  private void checkEmailForUniqueness(String email) {

    if (dao.existsByEmail(email)) {
      throw new ConflictException(
          String.format("user with email: %s is already exist", email));
    }
  }
}
