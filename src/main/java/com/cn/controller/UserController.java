package com.cn.controller;

import com.cn.bean.User;
import com.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userController")
public class  UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUerById.mvc")
    public String getUerById(Integer id){
        User user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("model",user);
        getRequest().setAttribute("model",user);
        return "user/user";
    }
}
