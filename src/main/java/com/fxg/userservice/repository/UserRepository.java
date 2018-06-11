package com.fxg.userservice.repository;

import com.fxg.spring.repository.DomainBaseRepository;
import com.fxg.userservice.domain.User;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends DomainBaseRepository<User> {

  @Query("select a from User a where a.enabled = true and a.id = :id")
  User loadOne(@Param("id") Integer id);

  @Query("select a from User a where a.enabled = true")
  Page<User> loadAll(Pageable pageable);

  @Query("select a from User a where a.enabled = true and a.id in :ids")
  List<User> loadAll(@Param("ids") Collection<Integer> ids);

  @Modifying
  @Query("update User a set a.enabled = false ,a.enabledId = a.id where a.id = :id")
  void disable(@Param("id") Integer id);

  @Modifying
  @Query("update User a set a.enabled = false ,a.enabledId = a.id where a.id in :ids")
  void disable(@Param("ids") Collection<Integer> ids);

  @Query("update User a set a.enabled = false ,a.enabledId = a.id where a.id in :ids")
  int countExist(@Param("ids") Integer id);

  @Query("update User a set a.enabled = false ,a.enabledId = a.id where a.id in :ids")
  int countExist(@Param("ids") Collection<Integer> ids);

}
