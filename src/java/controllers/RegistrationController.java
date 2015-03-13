/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Юрий
 */
@Controller
public class RegistrationController {
    @RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
    public String showRegistrationPage(Map<String, Object> model) {
        return "registration";
    }
}
