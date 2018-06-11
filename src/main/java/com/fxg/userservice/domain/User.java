package com.fxg.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fxg.spring.annotation.WriteControl;
import com.fxg.spring.enums.CanWrite;
import com.fxg.spring.validation.New;
import com.fxg.spring.validation.Params;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "a1_user")
@DynamicInsert
@DynamicUpdate
@Data
public class User {

  @Id
  @GeneratedValue
  @WriteControl(allow = {CanWrite.NONE})
  private Integer id;

  private String openId;

  private String unionId;

  private String mobile;

  private String email;

  private String maskedMobile;

  private String maskedEmail;

  private String nickName;

  @NotNull(groups = {Params.class, New.class}, message = "生日不能为空")
  private LocalDate birthday;

  @NotBlank(groups = {Params.class, New.class}, message = "用户密码不能为空")
  private String passwordHash;

  @JsonIgnore
  private Boolean enabled;

  @JsonIgnore
  private Integer enabledId;

  private LocalDateTime createTime;

  private LocalDateTime lastTime;

  @Version
  @JsonIgnore
  private Integer version;
}