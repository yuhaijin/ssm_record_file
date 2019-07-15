package com.qf.mapper;


import com.qf.pojo.User;

import java.util.List;

public interface UserMapper {

    User getUser(User user);

    int addUser(User user);

    String getUsername(String username);

    String getPassword(String username);

    String getRoleByName(String username);

    List<String> getpromission(String role_name);

    String getUid(String username);
}