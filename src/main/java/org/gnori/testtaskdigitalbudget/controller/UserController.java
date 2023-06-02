package org.gnori.testtaskdigitalbudget.controller;

import lombok.RequiredArgsConstructor;
import org.gnori.testtaskdigitalbudget.service.impl.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

}
