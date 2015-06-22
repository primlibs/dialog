/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import entities.User;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.EventService;

/**
 *
 * @author bezdatiuzer
 */
@Controller
@RequestMapping("/UserReport")
public class UserReportController extends WebController {
    
    @Autowired
    private EventService eventService;
    @Autowired
    private LkController lk;
    
    @RequestMapping("/summarizedReport")
    public String getFullUserReport(Map<String, Object> model,@RequestParam(value = "dateCampaignFrom", required = false) Date dateCampaignFrom,@RequestParam(value = "dateCampaignTo", required = false) Date dateCampaignTo, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        if(dateCampaignFrom==null||dateCampaignFrom.toString().equals("")){
            Calendar cl = Calendar.getInstance();
            cl.set(Calendar.YEAR, Calendar.MONTH, 1, 0, 0, 0);
            dateCampaignFrom = cl.getTime();
        }
        if(dateCampaignTo==null||dateCampaignTo.toString().equals("")){
            dateCampaignTo = new Date();
        }
        LinkedHashMap<User,HashMap> reportMap = eventService.getUsersAndSuccessfulFailedPerformancesForReport(dateCampaignFrom, dateCampaignTo, cabinetId);
        
        model.put("reportMap", reportMap);
        model.put("errors", eventService.getErrors());
        return "summarizedUserReport";
    }
    
}
