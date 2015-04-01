/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.CabinetUser;
import entities.User;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.CabinetUserService;
import support.AuthManager;

/**
 *
 * @author Юрий
 */
@Controller
public class LkController {

    public static final String CABINET_ID_SESSION_NAME = "CabinetId";

    @Autowired
    private CabinetUserService service;

    @Autowired
    private AuthManager authManager;

    @Transactional
    @RequestMapping(value = {"/lk"})
    public String showLkPage(Map<String, Object> model, HttpServletRequest request) throws Exception {

        User user = authManager.getCurrentUser();
        DataByUserAndCompany(request, model);
        request.setCharacterEncoding("UTF-8");

        List<CabinetUser> list = service.getByUser(user);
        if (list.size() == 1) {
            CabinetUser cu = list.get(0);
            request.getSession().setAttribute(CABINET_ID_SESSION_NAME, cu.getCabinet().getPersonalCabinetId());
            request.getSession().setAttribute("role", service.getUserRole(user, cu.getCabinet().getPersonalCabinetId()));
            return "redirect:/";
        } else {
            model.put("list", list);
            return "lk";
        }

    }

    @RequestMapping(value = {"/selectLk"})
    public String selectPersonalCabinetId(HttpServletRequest request, String personalCabinetId) throws UnsupportedEncodingException {
        Long longId = Long.valueOf(personalCabinetId);
        request.getSession().setAttribute(CABINET_ID_SESSION_NAME, longId);

        return "redirect:/";
    }

    public String DataByUserAndCompany(HttpServletRequest request, Map<String, Object> model) throws Exception {
        String company = service.getNameCompany(request);
        String user = service.getNameUser();
        model.put("nameUser", user);
        model.put("nameCompany", company);
        return service.toString();
    }

    public void getRole(HttpServletRequest request, Map<String, Object> model) {
        Object role = request.getSession().getAttribute("role");
        model.put("role", role);
         
    }
}
