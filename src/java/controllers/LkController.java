/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.CabinetUser;
import entities.User;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = {"/lk"}, method = RequestMethod.GET)
    public String showLkPage(Map<String, Object> model, HttpServletRequest request) {

        User user = authManager.getCurrentUser();

        List<CabinetUser> list = service.getByUser(user);
        if (list.size() == 1) {
            CabinetUser cu = list.get(0);
            request.getSession().setAttribute(CABINET_ID_SESSION_NAME, cu.getCabinet().getPersonalCabinetId());
            return "redirect:/";
        } else {
            model.put("list", list);
            return "lk";
        }

    }
    

}
