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
import service.StrategyService;

/**
 *
 * @author Юрий
 */
@RequestMapping("/Strategy")
@Controller
public class StrategyController extends WebController {

    @Autowired
    private LkController lk;

    @Autowired
    private StrategyService strategyService;

    @RequestMapping("/show")
    public String showStrategyListPage(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyName", required = false) String strategyName,
            String submit) throws Exception {

        lk.dataByUserAndCompany(request, model);
        //  Object cabinetId = request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (submit != null) {
            strategyService.saveStrategy(strategyName, cabinetId);
            model.put("message", "Стратегия " + strategyName + " создана");
        }

        model.put("errors", strategyService.getError());
        model.put("StrategyList", strategyService.strategyList(cabinetId));
        return "strategyList";
    }

    @RequestMapping("/strategy")
    public String showStrategyPage(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId", required = false) String strategyId,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "moduleName", required = false) String moduleName,
            String submit) throws Exception {
        lk.dataByUserAndCompany(request, model);
        model.put("strategyId", strategyId);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        

        return "strategy";
    }

}
