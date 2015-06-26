/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

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

/**
 *
 * @author bezdatiuzer
 */
@Controller
@RequestMapping("/ModuleReport")
public class moduleReportController extends WebController {
    
    @Autowired
    private EventService eventService;
    @Autowired
    private LkController lk;
    
    @RequestMapping("/summarizedReport")
    public String getFullModuleReport(Map<String, Object> model,HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        
        LinkedHashMap<Module,HashMap> reportMap=null;
        
        model.put("reportMap", reportMap);
        model.put("errors", eventService.getErrors());
        return "moduleReport";
    }
    
}
