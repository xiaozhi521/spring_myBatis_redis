package com.cn.service;

import com.cn.bean.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User getUserById(int id);
}
