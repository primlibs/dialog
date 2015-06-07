/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import entities.Strategy;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.FailReasonService;
import service.ModuleService;
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

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private FailReasonService failReasonService;

    @RequestMapping("/show")
    public String showStrategyListPage(Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "strategyName", required = false) String strategyName,
            String submit) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (submit != null) {
            strategyService.saveStrategy(strategyName, cabinetId);
            if (strategyService.getError().isEmpty()) {
                model.put("message", "Стратегия " + strategyName + " создана");
            }
        }
        if (!strategyService.getError().isEmpty()) {
            model.put("errors", strategyService.getError());
            model.put("strategyName",strategyName);
        }
        model.put("StrategyList", strategyService.getActiveStrategyList(cabinetId));
        return "strategyList";
    }

    @RequestMapping("/strategy")
    public String showStrategyPage(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "moduleName", required = false) String moduleName,
            @RequestParam(value = "moduleId", required = false) Long moduleId,
            @RequestParam(value = "strategyId") Long strategyId) throws Exception {

        lk.dataByUserAndCompany(request, model);
        //Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Strategy strategy = strategyService.findStrategy(strategyId);

        model.put("module", moduleService.showModule(moduleId));
        model.put("moduleId", moduleId);
        if (!strategyService.getError().isEmpty()) {
            model.put("errors", strategyService.getError());
        }
        //model.put("GroupList", strategyService.getGroupList(strategyId));
        //model.put("strategyId", strategyId);
        //model.put("strategyName", strategyService.findStrategy(strategyId).getStrategyName());
        model.put("strategy", strategy);
        return "strategy";
    }

    @RequestMapping("/renameStrategy")
    public String renameStrategy(Map<String, Object> model, HttpServletRequest request, @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "name") String name, RedirectAttributes ras) {
        strategyService.reanameStrategy(strategyId, name);
        ras.addFlashAttribute("errors", strategyService.getError());
        return "redirect:/Strategy/show";
    }

    @RequestMapping("/addGroup")
    public String addGroup(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "groupName") String groupName,
            RedirectAttributes ras) throws Exception {

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        strategyService.saveGroup(strategyId, groupName, cabinetId);
        if (strategyService.getError().isEmpty()) {
            ras.addFlashAttribute("message", "Группа " + groupName + " создана");
        }

        ras.addAttribute("strategyId", strategyId);
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/addModule")
    public String addModule(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "groupId") Long groupId,
            @RequestParam(value = "moduleName") String moduleName,
            RedirectAttributes ras) throws Exception {

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Long moduleId=strategyService.saveModule(groupId, moduleName, cabinetId);
        ras.addFlashAttribute("errors", strategyService.getError());
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("groupId", groupId);
        ras.addAttribute("moduleId", moduleId);
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/deleteStrategy")
    public String delStrategy(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId") Long strategyId,
            RedirectAttributes ras) throws Exception {
        //Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        strategyService.deleteStrategy(strategyId);

        ras.addFlashAttribute("errors", strategyService.getError());

        return "redirect:/Strategy/show";
    }

    @RequestMapping("/showModule")
    public String showModule(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "moduleId") Long moduleId,
            @RequestParam(value = "strategyId") Long strategyId,
            RedirectAttributes ras) {

        ras.addFlashAttribute("module", moduleService.showModule(moduleId));
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("moduleId", moduleId);
        ras.addFlashAttribute("errors", moduleService.getError());
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/addBodyModule")
    public String addBodyModule(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "moduleId") Long moduleId,
            @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "bodyText") String bodyText,
            RedirectAttributes ras) {

        if (moduleId != null) {
            moduleService.addBodyText(moduleId, bodyText);
        } else {
            ras.addFlashAttribute("errors", "Выберите модуль");
        }

        ras.addFlashAttribute("module", moduleService.showModule(moduleId));
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("moduleId", moduleId);
        ras.addFlashAttribute("errors", moduleService.getError());
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/failReasonEditor")
    public String editFailReason(Map<String, Object> model,
            HttpServletRequest request,
            //  @RequestParam(value = "moduleName", required = false) String moduleName,
            //  @RequestParam(value = "moduleId", required = false) Long moduleId,
            @RequestParam(value = "strategyId") Long strategyId) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Strategy strategy = strategyService.findStrategy(strategyId);

        model.put("actualReasons", failReasonService.getActiveFailReasonsByStrategy(strategyId));
        model.put("errors", failReasonService.getError());
        model.put("strategyId", strategyId);

        return "failReasons";
    }

    @RequestMapping("/createFailReason")
    public String createFailReason(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "failReasonName") String failReasonName,
            @RequestParam(value = "strategyId") Long strategyId) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Strategy strategy = strategyService.findStrategy(strategyId);

        failReasonService.saveFailReason(failReasonName, strategyId);
        
        if (failReasonService.getError().isEmpty()) {
            model.put("message", "Модуль отказа " + failReasonName + " создан");
        }

        model.put("actualReasons", failReasonService.getActiveFailReasonsByStrategy(strategyId));
        model.put("errors", failReasonService.getError());
        model.put("strategyId", strategyId);
        return "failReasons";
    }

    @RequestMapping("/deleteFailReason")
    public String deleteFailReason(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "failReasonId") Long failReasonId,
            @RequestParam(value = "strategyId") Long strategyId) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Strategy strategy = strategyService.findStrategy(strategyId);
        failReasonService.delete(failReasonId);
          if (failReasonService.getError().isEmpty()) {
            model.put("message", "Модуль отказа " + failReasonService.getFailReasons(failReasonId) + " удален");
        }
        model.put("actualReasons", failReasonService.getActiveFailReasonsByStrategy(strategyId));
        model.put("errors", failReasonService.getError());
        model.put("strategyId", strategyId);
        return "failReasons";
    }
}
