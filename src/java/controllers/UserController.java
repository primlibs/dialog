/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.UserService;
import support.SendMail;
import support.ServiceResult;
import support.StringAdapter;

/**
 *
 * @author Юрий
 */
@Controller
@RequestMapping("/User")
public class UserController extends WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendMail sendMail;

    @Autowired
    private LkController lk;

    @RequestMapping(value = {"/userAdd"})
    public String showAddUserPage(Map<String, Object> model, String submit,
            String email, String phone, String name, String surname, String role, String patronymic, HttpServletRequest request,RedirectAttributes ras) throws Exception {

        lk.dataByUserAndCompany(request, model);

        if (submit != null) {

            Object cabinetId = request.getSession().getAttribute(LkController.CABINET_ID_SESSION_NAME);
            userService.addUser(email, phone, name, surname, patronymic, role, cabinetId);
            if (userService.getError().isEmpty()) {
                model.put("message", "Пользователь добавлен");
                return "redirect:/User/userList";
            } else {
                model.put("errors", userService.getError());
            }

        } else {
            model.put("errors", userService.getError());
        }
        model.put("email", email);
        model.put("name", name);
        model.put("patronymic", patronymic);
        model.put("surname", surname);
        return "userAdd";
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
                String text = "Вы восcтнавливаите пароль от CallAssistent. Пройдите по ссылке для восстановления: " + link + "?hash=" + recoverHash;
                sendMail.sendMail(email, text);
                model.put("message", "Ссылка с востановлением отправлена на почту");
            }
            model.put("errors", userService.getError());
        }
        return "recoveryPassword";

    }

    @RequestMapping("/recoverPassword")
    public String recoverPassword(Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "hash", required = false) String hash,
            @RequestParam(value = "newPassword", required = false) String password,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            String submit) throws Exception {

        model.put("hash", hash);
        /*
         if (StringAdapter.isNull(hash)) {
         model.put("errors", "Не переданы идентифицирующие параметры");
         }
         */
        if (submit != null) {
            userService.recoverPassword(hash, password, confirmPassword);
            if (userService.getError().isEmpty()) {
                return "redirect:/login";
            }
            model.put("errors", userService.getError());

        }

        return "recoverPassword";

    }

    @RequestMapping("/userList")
    public String showListUserPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("cabinetUserList", userService.cabinetUserList(cabinetId));
        model.put("errors", userService.getError());
        return "userList";
    }
    
    @RequestMapping("/updateParamInUser")
    @ResponseBody
    public String updateParamInUser(Map<String, Object> model,@RequestParam(value = "cabinetUserId") Long cabinetUserId,@RequestParam(value = "param") String paramType,@RequestParam(value = "newVal") String newVal, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        boolean performed = userService.updateUserField(paramType, cabinetUserId, newVal,cabinetId);
        if(performed){
            return StringAdapter.getString(performed);
        } else{
            String err = "Ошибка: ";
            for (String s : userService.getError()) {
                err += s + "; ";
            }
            return err;
        }
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(Map<String, Object> model, HttpServletRequest request,@RequestParam(value = "cabinetUserIdtoDelete") Long cabinetUserIdtoDelete,
            @RequestParam(value = "cabinetUserIdtoAssign",required = false) Long cabinetUserIdtoAssign,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        /*if (cabinetUserId != null ) {
            userService.deleteUser(cabinetUserId);
        }*/
        
        userService.deleteUser(cabinetUserIdtoDelete,cabinetUserIdtoAssign,cabinetId);

        /*model.put("cabinetUserList", userService.cabinetUserList(cabinetId));
        model.put("errors", userService.getError());
        return "userList";*/
        ras.addFlashAttribute(ERRORS_LIST_NAME, userService.getError());
        return "redirect:/User/userList";
    }
}
