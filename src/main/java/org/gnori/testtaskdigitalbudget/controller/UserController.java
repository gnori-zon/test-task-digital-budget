package org.gnori.testtaskdigitalbudget.controller;

import static org.gnori.testtaskdigitalbudget.controller.UserController.USER_URL;

import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.model.dto.UserDto;
import org.gnori.testtaskdigitalbudget.model.dto.UserDto.CreationValidation;
import org.gnori.testtaskdigitalbudget.model.dto.UserDto.UpdateValidation;
import org.gnori.testtaskdigitalbudget.service.access.storage.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(USER_URL)
@RequiredArgsConstructor
public class UserController {

  public static final String USER_URL = "/api/v1/users";
  public static final String AUTH_HEADER = "User-Id";

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto register(@Validated(CreationValidation.class) @RequestBody UserDto userDto) {

    return userService.createFromUserDto(userDto);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public UserDto get(@RequestHeader(AUTH_HEADER) Integer userId) {

    return userService.getUserDtoById(userId);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  public UserDto update(
      @RequestHeader(AUTH_HEADER) Integer userId,
      @Validated(UpdateValidation.class) @RequestBody UserDto userDto
  ) {

    return userService.updateByIdFromUserDto(userId, userDto);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@RequestHeader(AUTH_HEADER) Integer userId) {

    userService.delete(userId);
  }

}
