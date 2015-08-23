/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.parent.WebController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.UserService;
import support.StringAdapter;

/**
 *
 * @author Юрий
 */
@Controller
@RequestMapping("/Registration")
public class RegistrationController extends WebController {

    @Autowired
    private UserService userService;

    /*@Autowired
    private AuthManager authManager;*/

    /*@RequestMapping(value = {"/registration"})
    public String showRegistrationPage(Map<String, Object> model, String submit, String checkbox,
            String company, String email, String phone, String password, String confirmPassword, String name, String surname, String patronymic,
            String emailCompany,RedirectAttributes ras) {

        if (submit != null) {
            if (checkbox != null) {
                if (password.equals(confirmPassword)) {
                    userService.save(null, email, null, password, null, null, null, null);
                    if (userService.getErrors().isEmpty()) {
                        ras.addFlashAttribute("username", email);
                        ras.addFlashAttribute("password",password);
                       // ras.addFlashAttribute("action","j_spring_security_check");
                        //ras.addAttribute("username", email);
                        //ras.addAttribute("password",password);
                        //ras.addAttribute("action","j_spring_security_check");
                        
                        //authRequest.setAuthenticated(true);
                        //AuthenticationManager am = new SampleAuthenticationManager();
                        //am.authenticate(authRequest);
                        //authRequest.
                        //authRequest.setAuthenticated(true);
                        //SecurityContextHolder.getContext().setAuthentication(authRequest);
                        setDetails(request, authRequest);
                        this.getAuthenticationManager().authenticate(authRequest);
                        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
                        support.AfterRegistrationAuthenticationManager am = new support.AfterRegistrationAuthenticationManager();
                        SecurityContextHolder.getContext().setAuthentication(am.authenticate(authRequest));
                        SecurityContext context = SecurityContextHolder.getContext();
                        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
                        
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                        return "redirect:/Registration/successRegistration";
                    } else {
                        model.put("errors", userService.getErrors());
                    }
                } else {
                    userService.addError("Пароли не совпадают");
                    model.put("errors", userService.getErrors());

                }
            } else {
                model.put("errors", "Ознакомтесь и согласитесь с условиями");
            }
        } else {
            model.put("errors", userService.getErrors());
        }

        return "registration";
    }*/
    
    @RequestMapping(value = {"/registration"})
    public String showRegistrationPage(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "regemail")String email,
            @RequestParam(value = "regphone")String phone,
            @RequestParam(value = "regpassword")String password,
            @RequestParam(value = "regconfirmPassword")String confirmPassword,
            RedirectAttributes ras) throws Exception {
                if (password.equals(confirmPassword)) {
                    //try{
                        userService.save(null, email, phone, password, null, null, null, null);
                    /*}catch (Exception ex){
                        ras.addAttribute("regerrors", StringAdapter.getStackTraceException(ex));
                        return "redirect:/login.jsp";
                    }*/
                    if (userService.getErrors().isEmpty()) {
                        ras.addFlashAttribute("username", email);
                        ras.addFlashAttribute("phone", phone);
                        ras.addFlashAttribute("password",password);
                        
                        return "redirect:/Registration/successRegistration";
                    }
                } else {
                    userService.addError("Пароли не совпадают");
                }
        List<String> ers = new ArrayList();
        ers.addAll(userService.getErrors());
        ras.addAttribute("regerrors", ers);
        return "redirect:/login.jsp";
    }
    
    @RequestMapping(value = {"/saveContacts"})
    public String saveContacts(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "clemail")String email,
            @RequestParam(value = "clphone")String phone,
            @RequestParam(value = "clname")String name,
            RedirectAttributes ras) {
            userService.saveContacts(name, email, phone);        
        //ras.addFlashAttribute("errors", userService.getErrors());
        if(!userService.getErrors().isEmpty()){
            ras.addAttribute("serrors", userService.getErrors());
            ras.addAttribute("clemail", email);
            ras.addAttribute("clphone", phone);
            ras.addAttribute("clname", name);
            return "redirect:/login.jsp";
        }
        return "thanx4theinterest";
    }
    
    @RequestMapping(value = {"/closedregistration"})
    public String showClosedRegMessage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        return "closedreg";
    }

    @RequestMapping(value = {"/successRegistration"})
    public String showSRPage(Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "password", required = false) String password) throws Exception {
        
        return "successRegistration";
    }
}
