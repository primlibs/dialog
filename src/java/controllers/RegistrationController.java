/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.UserService;

/**
 *
 * @author Юрий
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/registration"})
    public String showRegistrationPage(Map<String, Object> model, String submit,
            String company, String email, String phone, String password, String name, String surname, String patronymic,
            String emailCompany) {
        if (submit != null) {
            userService.save(company, email, phone, password, name, surname, patronymic, emailCompany);
            return "redirect:/successRegistration";
        }
        return "registration";
    }

    @RequestMapping(value = {"/successRegistration"}, method = RequestMethod.GET)
    public String showIndexPage(Map<String, Object> model) {
        return "successRegistration";
    }
}
