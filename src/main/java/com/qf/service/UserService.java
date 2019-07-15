package com.qf.service;


import com.qf.pojo.User;

import java.util.List;

public interface UserService {

    User login(User user);

    int register(User user);

    String getPassword(String username);

    String getRoleByName(String username);

    List<String> getpromission(String role_name);
    String getUid(String username);


}
