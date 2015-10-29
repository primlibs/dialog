/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.parent;


import entities.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import support.AuthManager;
import support.editors.DateEditor;
import support.ServiceResult;

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
    private DateEditor dateEditor;

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
    
}
