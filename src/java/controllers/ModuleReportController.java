/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import entities.Module;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.EventService;
import service.ModuleService;
import service.ReportService;
import service.UserService;

/**
 *
 * @author bezdatiuzer
 */
@Controller
@RequestMapping("/ModuleReport")
public class ModuleReportController extends WebController {
    
    @Autowired
    private EventService eventService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private LkController lk;
    
    @RequestMapping("/summarizedReport")
    public String getFullModuleReport(Map<String, Object> model,HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        LinkedHashMap<Module,HashMap> reportMap=null;
        
        //model.put("reportData", reportService.getDataForModuleReport(cabinetId));
        model.put("userMap",userService.getMakingCallsAndParticipatedUsersMap(cabinetId));
        model.put("moduleMap", moduleService.getAllModulesMap(cabinetId));
        model.put("errors", eventService.getErrors());
        return "moduleReport";
    }
    
    
    
}
