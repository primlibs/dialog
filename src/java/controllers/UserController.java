/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.parent.WebController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import static org.apache.coyote.http11.Constants.a;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.UserService;
import support.SendMail;
import support.ServiceResult;

/**
 *
 * @author Юрий
 */
@Controller
public class UserController extends WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendMail sendMail;

    @Autowired
    private LkController lk;

    @RequestMapping(value = {"/adduser"})
    public String showAddUserPage(Map<String, Object> model, String submit,
            String email, String phone, String name, String surname, String role, String patronymic, HttpServletRequest request) throws Exception {

        lk.dataByUserAndCompany(request, model);

        if (submit != null) {

            Object cabinetId = request.getSession().getAttribute(LkController.CABINET_ID_SESSION_NAME);
            userService.addUser(email, phone, name, surname, patronymic, role, cabinetId);
            if (userService.getError().isEmpty()) {
                model.put("message", "Пользователь добавлен");
            } else {
                model.put("errors", userService.getError());
            }

        } else {
            model.put("errors", userService.getError());
        }
        return "adduser";
    }

    @RequestMapping("/changePassword")
    public String changePassword(
            Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = false) String password,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            String submit
    ) throws Exception {
        lk.dataByUserAndCompany(request, model);
        if (submit != null) {
            ServiceResult result = userService.changePassword(oldPassword, password, confirmPassword);
            if (result.getErrors().isEmpty()) {

                return "/successChangePassword";
            } else {
                model.put("errors", result.getErrors());
                return "/changePassword";
            }
        }

        return "/changePassword";

    }

    @RequestMapping("/recoveryPassword")
    public String recoveryPassword(Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "email", required = false) String email, String submit) throws Exception {

        if (submit != null) {
            String recoverHash = userService.recoveryPassword(email);
            if (userService.getError().isEmpty()) {
                String link = "http://62.76.41.244/CallCentr/recoverPassword";
                String text = "Вы восcтнавливаите пароль от CallAssistent. Пройдите по ссылке для восстановления: " + link + "?name=hash&value=" + recoverHash+"text/plain; charset=UTF-8";
                sendMail.sendMail(email, text);
            }
            model.put("errors", userService.getError());
        }
        return "recoveryPassword";

    }

    @RequestMapping("/recoverPassword")
    public String recoverPassword(Map<String, Object> model, HttpServletRequest request, String hash,
            @RequestParam(value = "newPassword", required = false) String password,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            String submit) throws Exception {

        if (submit != null) {

            if (userService.getError().isEmpty()) {

            }
            model.put("errors", userService.getError());
        }

        return "recoverPassword";

    }

}
