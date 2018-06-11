package com.fxg.userservice.controller;

import com.fxg.spring.api.HttpResult;
import com.fxg.spring.api.HttpStatus;
import com.fxg.spring.validation.Params;
import com.fxg.userservice.domain.User;
import com.fxg.userservice.service.UserService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1.0/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class UserController {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  UserService service;

  @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public HttpResult<User> getAll(@Validated(Params.class) @RequestBody User user) {
    return new HttpResult<>(HttpStatus.OK, "ok", service.add(user));
  }

  @GetMapping(value = "get")
  public HttpResult<User> getAll(@RequestParam Integer id) {
    return new HttpResult<>(HttpStatus.OK, "ok", service.get(id));
  }

  @GetMapping("/test/cookie")
  public HttpResult<Void> cookie(@RequestParam("browser") String browser, HttpServletRequest request, HttpSession session) {
    //取出session中的browser
    Object sessionBrowser = session.getAttribute("browser");
    if (sessionBrowser == null) {
      System.out.println("不存在session，设置browser=" + browser);
      session.setAttribute("browser", browser);
    } else {
      System.out.println("存在session，browser=" + sessionBrowser.toString());
    }
    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        System.out.println(cookie.getName() + " : " + cookie.getValue());
      }
    }
    return new HttpResult<>(HttpStatus.OK, "ok");
  }

  @Autowired
  JdbcTemplate template;

  @GetMapping("/get/all")
  public List<Map<String, Object>> getAll() {
    List<Map<String, Object>> mapList = template.queryForList("SELECT * FROM a1_user where mobile enabled = :enabled", true);
    return mapList;
  }
}
