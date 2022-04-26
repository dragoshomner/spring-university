package com.example.project.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class UserController {

    @RequestMapping("users")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/users");
//        modelAndView.addObject("users", )
        return modelAndView;
    }
}
