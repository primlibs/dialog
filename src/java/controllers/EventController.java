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

/**
 *
 * @author Юрий
 */
@Controller
@RequestMapping("/Event")
public class EventController extends WebController {

    @Autowired
    private LkController lk;

    @RequestMapping("/eventList")
    public String showEventListPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        
        return "eventList";
    }

}
