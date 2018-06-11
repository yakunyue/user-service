/*
 * Copyright 2017 - 数多科技
 * 
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。 本内容为保密信息，仅限本公司内部使用。 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.fxg.userservice.service;

import com.fxg.spring.service.AbstractServiceForBase;
import com.fxg.userservice.domain.User;
import com.fxg.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService extends AbstractServiceForBase<User> {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  private UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) throws Exception {
    super(User.class, repository, "id");
    this.repository = repository;
  }

  public void findOne(){
  }
}
