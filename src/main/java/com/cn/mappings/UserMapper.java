package com.cn.mappings;


import com.cn.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseDao{
    User getUserById(Integer id);

}
