/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.parent;


import controllers.IndexController;
import entities.User;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.RightService;
import service.UserService;
import support.AuthManager;
import support.RightSeeker;
import support.editors.DateEditor;
import support.ServiceResult;
import support.StringAdapter;
import support.db.Persistence;
import support.logic.Right;
import support.logic.RightStack;

/**
 *
 * @author Rice Pavel
 */
@Controller
public class WebController {

    protected Logger log = Logger.getLogger(this.getClass());

    @Autowired
    protected AuthManager authManager;
    
    protected final String ERRORS_LIST_NAME = "errors";

    @Autowired
    RightService rightService;

    @Autowired
    UserService userService;
    
    @Autowired
    private DateEditor dateEditor;
    
    public RightStack userRights ;
    public RightStack systemRights;
    public User currentUser = new User();
    public RightSeeker rightSeeker = new RightSeeker();
    

    @ModelAttribute
    public void setOrderInfo(Map<String, Object> model) {
        
    }

    @ModelAttribute
    public void setDateFormatter(Map<String, Object> model) {
        model.put("dateFormatter", new DateFormatter());
    }


    @InitBinder
    public void standartInitBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, dateEditor);
    }
  
    protected void addErrors(RedirectAttributes ra, ServiceResult res) {
        ra.addFlashAttribute(ERRORS_LIST_NAME, res.getErrors());
    }

    protected void addErrors(Map<String, Object> model, ServiceResult res) {
        model.put(ERRORS_LIST_NAME, res.getErrors());
    }

    protected boolean isSuperAdmin(){
        return authManager.getCurrentUser().isSuperAdmin();
    }
    
    @ModelAttribute
    private void rightOn(Map<String, Object> model, HttpServletRequest request) throws Exception {
        userRights = RightStack.getInstance("controllers.");
        systemRights = RightStack.getInstance("controllers.");
        findActiveUser();
        if(currentUser.isSuperAdmin()){
            createRightsFromApp();
        }
        createUserRight();
        model.put("SERVICE_currentUser", currentUser);
        model.put("SERVICE_userRights", userRights);
        model.put("SERVICE_systemRights", systemRights);
        //if (!checkRight(request)) {
        //    throw new Exception("К сожалению у Вас недостаточно прав для выполнения данного действия");
        //}
    }

    private void createRightsFromApp() throws Exception {
        Persistence persistence = Persistence.getInstance();
        RightStack rst = rightSeeker.createSpringRightsFromJar(IndexController.class,"controllers.");
        systemRights = rst;
        rightService.update(rst);
        if (rightService.hasErrors()) {
            throw new Exception(StringAdapter.getStringFromList(rightService.getErrors()));
        }
    }

    private void findActiveUser() throws Exception {
        User us = userService.getCurrentUser();
        if (us != null) {
            currentUser = us;
        }
    }

    private void createUserRight() throws Exception {
        userRights = systemRights;
        /*if (currentUser != null) {
            Role rl = currentUser.getRole();
            if (currentUser.getAdmin() != null) {
                userRights = systemRights;
            } else {
                if (rl != null) {
                    for (Right right : rl.getRights()) {
                        userRights.add(right.object, right.action, right.objectDescription, right.actionDescription);
                    }
                }
            }

        }*/

    }

    private boolean checkRight(HttpServletRequest request) throws Exception {
        String req = request.getServletPath();
        support.logic.Right rt = null;
        String maps = "";
        for (String map : rightSeeker.mapping.keySet()) {
            maps += " " + map;
            if (req.equals(map)) {
                rt = rightSeeker.mapping.get(map);
                break;
            }
        }
        if (rt != null) {
            if (userRights.isRight(rt.getObject(), rt.getAction())) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
    
}
