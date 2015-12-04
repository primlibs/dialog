/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.parent.WebController;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.RightService;
import service.UserService;
import support.commons.Right;

/**
 *
 */
@Controller
@RequestMapping("/Right")
public class RightController extends WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private RightService RightService;
    
    @Autowired
    private LkController lk;

    
    @RequestMapping(value = {"/showAllSystemRights"})
    @Right(description="Права - модерация структуры",name="showAllSystemRights")
    public String showAllSystemRights(HttpServletRequest request, Map<String, Object> model) throws Exception{
        lk.dataByUserAndCompany(request, model);
        User user=userService.getCurrentUser();
        List<entities.Right> listRight=new ArrayList();
        if(user!=null && user.isSuperAdmin()){
            listRight=RightService.getRights();
        }
        model.put("listRight", listRight);
        model.put("error", RightService.getErrors());
        return "showAllSystemRights";
    }
    
    @RequestMapping(value = {"/change"})
    @Right(description="Права - изменение",name="change")
    public String change(HttpServletRequest request, Map<String, Object> model,@RequestParam("rightId") Long rightId) throws Exception{
        lk.dataByUserAndCompany(request, model);
        User user=userService.getCurrentUser();
        if(user!=null && user.isSuperAdmin()){
           RightService.change(rightId);
        }
        return "redirect:/Right/showAllSystemRights";
    }
    
}
