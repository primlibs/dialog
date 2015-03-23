/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.AddUserService;
import service.UserService;

/**
 *
 * @author Юрий
 */
@Controller
public class UserController {

    @Autowired
    private AddUserService userService;

    @RequestMapping(value = {"/adduser"})
    public String showAddUserPage(Map<String, Object> model, String submit,
            String email, String phone, String password, String name, String surname, String patronymic) {
        if (submit != null) {
            userService.save (email, phone, password, name, surname, patronymic);
            return "redirect:/successRegistration";
        }
        return "adduser";
    }

}
