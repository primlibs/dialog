/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.UserService;
import support.JsonResponse;
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

    //to do po 4elove4eski bez etogo submit
    @RequestMapping(value = {"/userAdd"})
    public String showAddUserPage(Map<String, Object> model, String submit,
            @RequestParam(value = "email", required = false)String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "patronymic", required = false)String patronymic,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "role", required = false) String role,
            HttpServletRequest request,RedirectAttributes ras) throws Exception {

        lk.dataByUserAndCompany(request, model);

        if (submit != null) {

            Object cabinetId = request.getSession().getAttribute(LkController.CABINET_ID_SESSION_NAME);
            
            userService.addUser(email, phone, name, surname, patronymic, role, cabinetId);
            if (userService.getErrors().isEmpty()) {
                ras.addFlashAttribute("message", "Пользователь с логином: "+email+" и паролем: 0000 успешно добавлен");
                //model.put("message", );
                return "redirect:/User/userList";
            }

        } /*else {
            model.put("errors", userService.getErrors());
        }*/
        model.put("errors", userService.getErrors());
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
            if (userService.getErrors().isEmpty()) {
                String link = "http://dialogpl.com/User/recoverPassword";
                String text = "Вы восcтнавливаете пароль от CallAssistent. Пройдите по ссылке для восстановления: " + link + "?hash=" + recoverHash;
                sendMail.sendMail(email, text);
                model.put("message", "Ссылка с востановлением отправлена на почту");
            }
            model.put("email", email);
            model.put("errors", userService.getErrors());
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
            if (userService.getErrors().isEmpty()) {
                return "redirect:/login";
            }
            model.put("errors", userService.getErrors());

        }

        return "recoverPassword";

    }

    @RequestMapping("/userList")
    public String showListUserPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        List<String> errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("cabinetUserList", userService.cabinetUserList(cabinetId));
        errors.addAll(userService.getErrors());
        model.put("errors", errors);
        return "userList";
    }
    
    @RequestMapping("/updateParamInUser")
    @ResponseBody
    public String updateParamInUser(Map<String, Object> model,@RequestParam(value = "cabinetUserId") Long cabinetUserId,@RequestParam(value = "param") String paramType,@RequestParam(value = "newVal") String newVal, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        userService.updateUserField(paramType, cabinetUserId, newVal,cabinetId);
        List<String>serviceErrs=userService.getErrors();
        if(serviceErrs.isEmpty()){
            return StringAdapter.getString(true);
        } else{
            String err = "Ошибка: ";
            for (String s : serviceErrs) {
                err += s + "; ";
            }
            return err;
        }
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(Map<String, Object> model, HttpServletRequest request,@RequestParam(value = "cabinetUserIdtoDelete") Long cabinetUserIdtoDelete,
            @RequestParam(value = "cabinetUserIdtoAssign") Long cabinetUserIdtoAssign,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        User thisUser = authManager.getCurrentUser();
        
        /*if (cabinetUserId != null ) {
            userService.deleteUser(cabinetUserId);
        }*/
        
        userService.deleteUser(cabinetUserIdtoDelete,cabinetUserIdtoAssign,thisUser.getId(),cabinetId);

        /*model.put("cabinetUserList", userService.cabinetUserList(cabinetId));
        model.put("errors", userService.getErrors());
        return "userList";*/
        ras.addFlashAttribute(ERRORS_LIST_NAME, userService.getErrors());
        return "redirect:/User/userList";
    }
    
    
    
    @RequestMapping(value = {"/changeOrgName"})
    @ResponseBody
    public JsonResponse changeOrgName(HttpServletRequest request, Map<String, Object> model,
            @RequestParam(value = "newval", required = false)String newName) throws Exception{
        lk.dataByUserAndCompany(request, model);
        
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        userService.changeCabinetName(newName, cabinetId);
        JsonResponse res = JsonResponse.getInstance();
        if(userService.getErrors().isEmpty()){
           res.setStatus(Boolean.TRUE);
        }else{
            String err = "";
            for(String s:userService.getErrors()){
                err+=s+"; ";
            }
            res.setStatus(Boolean.FALSE);
            res.setMessage(err);
        }
        return res;
        
    }
    
}
