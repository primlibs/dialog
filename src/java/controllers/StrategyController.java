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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.FailReasonService;
import service.GroupService;
import service.ModuleService;
import service.StrategyService;
import support.JsonResponse;
import support.StringAdapter;

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
    @Autowired
    private GroupService groupService;

    @RequestMapping("/show")
    public String showStrategyListPage(Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "strategyName", required = false) String strategyName,
            String submit) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (submit != null) {
            strategyService.saveStrategy(strategyName, cabinetId);
            if (strategyService.getErrors().isEmpty()) {
                model.put("message", "Сценарий " + strategyName + " создан");
            }
        }
        if (!strategyService.getErrors().isEmpty()) {
            model.put("errors", strategyService.getErrors());
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
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Strategy strategy = strategyService.findStrategy(strategyId);
        
        
        model.put("strategy", strategy);
        model.put("module", moduleService.showModule(moduleId));
        model.put("moduleId", moduleId);
        model.put("groupMap", groupService.getActiveGroupMap(strategyId, cabinetId));
        if (!strategyService.getErrors().isEmpty()) {
            model.put("errors", strategyService.getErrors());
        }
        //model.put("GroupList", strategyService.getGroupList(strategyId));
        //model.put("strategyId", strategyId);
        //model.put("strategyName", strategyService.findStrategy(strategyId).getStrategyName());
        return "strategy";
    }

    @RequestMapping("/renameStrategy")
    @ResponseBody
    public String renameStrategy(Map<String, Object> model, HttpServletRequest request, @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "name") String name, RedirectAttributes ras) {
        strategyService.renameStrategy(strategyId, name);
        Boolean result = false;
        if(strategyService.getErrors().isEmpty()){
            result=true;
        }
        return StringAdapter.getString(result);
    }

    @RequestMapping("/addGroup")
    public String addGroup(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "groupName") String groupName,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        groupService.saveGroup(strategyId, groupName, cabinetId);
        if (groupService.getErrors().isEmpty()) {
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

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Long moduleId=moduleService.saveModule(groupId, moduleName, cabinetId);
        ras.addFlashAttribute("errors", moduleService.getErrors());
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("groupId", groupId);
        ras.addAttribute("moduleId", moduleId);
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/deleteStrategy")
    public String delStrategy(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyIdtoDelete") Long strategyId,
            RedirectAttributes ras) throws Exception {
        //Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        strategyService.deleteStrategy(strategyId,cabinetId);

        ras.addFlashAttribute("errors", strategyService.getErrors());

        return "redirect:/Strategy/show";
    }

    @RequestMapping("/showModule")
    public String showModule(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "moduleId") Long moduleId,
            @RequestParam(value = "strategyId") Long strategyId,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        ras.addFlashAttribute("module", moduleService.showModule(moduleId));
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("moduleId", moduleId);
        ras.addFlashAttribute("errors", moduleService.getErrors());
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/addBodyModule")
    public String addBodyModule(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "moduleId") Long moduleId,
            @RequestParam(value = "strategyId") Long strategyId,
            @RequestParam(value = "bodyText") String bodyText,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        if (moduleId != null) {
            moduleId = moduleService.addBodyText(moduleId, bodyText, cabinetId);
        } else {
            ras.addFlashAttribute("errors", "Выберите модуль");
        }

        ras.addFlashAttribute("module", moduleService.showModule(moduleId));
        ras.addAttribute("strategyId", strategyId);
        ras.addAttribute("moduleId", moduleId);
        ras.addFlashAttribute("errors", moduleService.getErrors());
        return "redirect:/Strategy/strategy";
    }

    @RequestMapping("/failReasonEditor")
    public String editFailReason(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "strategyId") Long strategyId) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("actualReasons", failReasonService.getActiveFailReasonsByStrategy(strategyId));
        model.put("errors", failReasonService.getErrors());
        model.put("strategyId", strategyId);

        return "failReasons";
    }

    @RequestMapping("/createFailReason")
    public String createFailReason(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "failReasonName") String failReasonName,
            @RequestParam(value = "strategyId") Long strategyId,
            RedirectAttributes ras) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        failReasonService.saveFailReason(failReasonName, strategyId);
        
        /*if (failReasonService.getErrors().isEmpty()) {
            ras.addFlashAttribute("message", "Модуль отказа " + failReasonName + " создан");
        }*/
        ras.addFlashAttribute("errors", failReasonService.getErrors());
        ras.addAttribute("strategyId", strategyId);
        return "redirect:/Strategy/failReasonEditor";
    }

    @RequestMapping("/deleteFailReason")
    public String deleteFailReason(Map<String, Object> model,RedirectAttributes ras,
            HttpServletRequest request,
            @RequestParam(value = "failReasonIdtoDelete") Long failReasonId,
            @RequestParam(value = "strategyId") Long strategyId) throws Exception {

        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);;
        failReasonService.delete(failReasonId);
          /*if (failReasonService.getErrors().isEmpty()) {
            ras.addFlashAttribute("message", "Модуль отказа " + failReasonService.getFailReason(failReasonId) + " удален");
        }*/
        ras.addFlashAttribute("errors", failReasonService.getErrors());
        ras.addAttribute("strategyId", strategyId);
        return "redirect:/Strategy/failReasonEditor";
    }
    
    @RequestMapping("/changegroupname")
    @ResponseBody
    public JsonResponse changeGroupName(Map<String, Object> model,HttpServletRequest request,
            @RequestParam(value = "groupid") Long groupId, @RequestParam(value = "newval",required = false) String newVal) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        groupService.updateName(newVal, groupId, cabinetId);
        JsonResponse res = new JsonResponse();
        res.setStatus(Boolean.TRUE);
        if(!groupService.getErrors().isEmpty()){
            res.setMessage(groupService.getErrorsAsString());
            res.setStatus(Boolean.FALSE);
        }
        return res;
    }
    
    @RequestMapping("/changemodulename")
    @ResponseBody
    public JsonResponse changeModuleName(Map<String, Object> model,HttpServletRequest request,
            @RequestParam(value = "moduleid") Long moduleId, @RequestParam(value = "newval",required = false) String newVal) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        moduleService.updateName(newVal, moduleId, cabinetId);
        JsonResponse res = new JsonResponse();
        res.setStatus(Boolean.TRUE);
        if(!moduleService.getErrors().isEmpty()){
            res.setMessage(moduleService.getErrorsAsString());
            res.setStatus(Boolean.FALSE);
        }
        return res;
    }
    
    @RequestMapping("/changeGroupPosition")
    @ResponseBody
    public JsonResponse changeGroupPosition(Map<String, Object> model,HttpServletRequest request,
            @RequestParam(value = "groupId") Long groupId, 
            @RequestParam(value = "newPosition") Long newPosition) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        groupService.changePosition(groupId, newPosition, cabinetId);
        JsonResponse res = new JsonResponse();
        res.setStatus(Boolean.TRUE);
        if(!groupService.getErrors().isEmpty()){
            res.setMessage(moduleService.getErrorsAsString());
            res.setStatus(Boolean.FALSE);
        }
        return res;
    }
    
    @RequestMapping("/changeModulePosition")
    @ResponseBody
    public JsonResponse changeModulePosition(Map<String, Object> model,HttpServletRequest request,
            @RequestParam(value = "moduleId") Long moduleId, 
            @RequestParam(value = "newPosition") Long newPosition) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        moduleService.changePosition(moduleId, newPosition, cabinetId);
        JsonResponse res = new JsonResponse();
        res.setStatus(Boolean.TRUE);
        if(!groupService.getErrors().isEmpty()){
            res.setMessage(moduleService.getErrorsAsString());
            res.setStatus(Boolean.FALSE);
        }
        return res;
    }
    
}
