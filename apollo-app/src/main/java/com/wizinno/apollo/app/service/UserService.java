package com.wizinno.apollo.app.service;

import com.wizinno.apollo.app.domain.UserMapper;
import com.wizinno.apollo.app.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HP on 2017/9/2.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void register(User user) {
        userMapper.insert(user);
    }
}
