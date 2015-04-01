/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LkController lk;

    @RequestMapping(value = {"/adduser"})
    public String showAddUserPage(Map<String, Object> model, String submit,
            String email, String phone, String password, String name, String surname, String role, String patronymic, HttpServletRequest request) throws Exception {

          lk.DataByUserAndCompany(request, model);
          lk.getRole(request, model);
        
        if (submit != null) {

            Object cabinetId = request.getSession().getAttribute(LkController.CABINET_ID_SESSION_NAME);
            userService.addUser(email, phone, password, name, surname, patronymic, role, cabinetId);
            String error = userService.getError();
            if (error.isEmpty()) {
                return "redirect:/successRegistration";
            } else {
                model.put("error", error);
            }
        }
        return "adduser";
    }

}
