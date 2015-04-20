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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.ModuleService;
import service.StrategyService;

/**
 *
 * @author Юрий
 */
@RequestMapping("/Module")
@Controller
public class ModuleController extends WebController {
    
    @Autowired
    private StrategyService strategyService;
    
    @Autowired
    private ModuleService moduleService;
    
     @RequestMapping("/deletModule")
    public String addModule(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "groupId") Long groupId,
             @RequestParam(value = "moduleId") Long moduleId,
            RedirectAttributes ras) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
      moduleService.deletModul(moduleId);
        
       // ras.addFlashAttribute("errors", strategyService.getError());
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("groupId", groupId);
        return "redirect:/Strategy/strategy";
    }
    
}
