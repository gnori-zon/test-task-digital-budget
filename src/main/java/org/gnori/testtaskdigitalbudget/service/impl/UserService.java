package org.gnori.testtaskdigitalbudget.service.impl;

import org.gnori.testtaskdigitalbudget.dao.iml.UserDao;
import org.gnori.testtaskdigitalbudget.model.entity.impl.UserEntity;
import org.gnori.testtaskdigitalbudget.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserEntity, UserDao> {

  protected UserService(UserDao dao) {
    super(dao);
  }

}
