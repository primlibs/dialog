/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static antlr.Utils.error;
import controllers.parent.WebController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

/**
 *
 * @author Юрий
 */
@Controller
public class RegistrationController extends WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private LkController lk;

    @RequestMapping(value = {"/registration"})
    public String showRegistrationPage(Map<String, Object> model, String submit,
            String company, String email, String phone, String password, String name, String surname, String patronymic,
            String emailCompany) {
         String error = userService.getError();
        if (submit != null) {
            userService.save(company, email, phone, password, name, surname, patronymic, emailCompany);
            return "redirect:/successRegistration";
        }else
        if (error.isEmpty()) {
             model.put("errors", error);
            
        }
        return "registration";

    }

    @RequestMapping(value = {"/successRegistration"})
    public String showCRPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        return "successRegistration";
    }
}
