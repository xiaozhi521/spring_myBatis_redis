package com.cn.controller;

import com.cn.bean.Role;
import com.cn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    public RoleController() {
        System.out.println("RoleController Constructor...");
    }

    @RequestMapping("/{id}.mvc")
    @ResponseBody
    public Object getRole(@PathVariable Long id){
        Role role = roleService.getRole(id);

        return role;
    }
}
