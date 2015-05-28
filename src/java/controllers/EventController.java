/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import entities.CabinetUser;
import entities.User;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.ClientService;
import service.EventService;
import service.GroupService;
import service.ModuleService;
import service.StrategyService;
import support.AuthManager;
import support.StringAdapter;

/**
 *
 * @author Юрий
 */
@Controller
@RequestMapping("/Event")
public class EventController extends WebController {

    @Autowired
    private LkController lk;

    @Autowired
    private EventService eventService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private StrategyService strategyService;
    
    @Autowired
    private ClientService clientService;

    @RequestMapping("/campaignList")
    public String showCampaigns(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("campaignFinishedCallMap", eventService.getCampaignsAndFinishedCallsInCabinet(cabinetId));
        model.put("errors", eventService.getError());
        return "campaignList";
    }

    @RequestMapping("/createCampaign")
    public String createCampaign(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "strategyId", required = false) Long strategyId,
            RedirectAttributes ras
    ) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (strategyId != null) {
            eventService.createCampaign(name, strategyId, cabinetId);
            if (eventService.getError().isEmpty()) {
                ras.addFlashAttribute("message", "Евент " + name + " успешно создан");
                return "redirect:/Event/campaignList";
            }
        }
        model.put("numericName", eventService.numericName(cabinetId));
        model.put("strategytList", eventService.strategytList(cabinetId));
        model.put("errors", eventService.getError());
        return "createCampaign";
    }

    @RequestMapping("/campaignSpecification")
    public String showCampaignSpecification(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "campaignId"//, required = false
            ) Long campaignId) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("errors", eventService.getError());
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        model.put("userAssignedClient", eventService.userAssignedClient(campaignId, cabinetId));

        model.put("userAssignedClientNotProcessed", eventService.userAssignedClientNotProcessed(campaignId, cabinetId));
        model.put("userAssignedClientProcessed", eventService.userAssignedClientProcessed(campaignId, cabinetId));
        model.put("userAssignedClientProcessedSuccess", eventService.userAssignedClientProcessedSuccess(campaignId, cabinetId));
        model.put("userAssignedClientProcessedFails", eventService.userAssignedClientProcessedFails(campaignId, cabinetId));

        model.put("eventList", eventService.getEventList(campaignId, cabinetId));
        model.put("unassignedEventList", eventService.getUnassignedEvent(campaignId, cabinetId));
        model.put("campaign", eventService.getCampaign(campaignId));
        return "campaignSpecification";
    }

    @RequestMapping("/getShapeExcel")
    public void getShapeExcel(Map<String, Object> model, HttpServletResponse response, HttpServletRequest request) throws Exception {

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Clients.xls");
        eventService.getXls().write(response.getOutputStream());
    }

    @RequestMapping("/setXls")
    public String setXls(Map<String, Object> model,
            @RequestParam(value = "fileXls") MultipartFile fileXls,
            String checkbox, HttpServletRequest request,
            @RequestParam(value = "campaignId") Long campaignId,
            RedirectAttributes ras) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Boolean update = false;
        if (checkbox != null) {
            update = true;
        }
        eventService.readXls(fileXls, cabinetId, campaignId, update);
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getError());
        if (eventService.getError().isEmpty()) {
            ras.addFlashAttribute("message", "Клиенты успешно добавлены");
        }
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));
        return "redirect:/Event/campaignSpecification";
    }

    @RequestMapping("/eventAppointSave")
    public String saveAppointEvent(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "arrayClientIdUserId") String[] arrayClientIdUserId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.eventAppointSave(arrayClientIdUserId, cabinetId, campaignId);
        model.put("clientList", eventService.getClientList(campaignId, cabinetId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));
        return "redirect:/Event/campaignSpecification";
    }

    @RequestMapping("/eventShowAllAppoint")
    public String showAllAppointEvent(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("eventAllAppoint", eventService.eventAppointAll(campaignId, cabinetId));

        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));
        return "eventAppointAll";
    }

    @RequestMapping("/eventAppointSaveAll")
    public String saveAllAppointEvent(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "clientNum") String[] clientNumArray,
            @RequestParam(value = "userId") Long[] userIdArray,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        eventService.eventAppointSaveAll(campaignId, cabinetId, userIdArray, clientNumArray);

        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        model.put("campaign", eventService.getCampaign(campaignId));
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));

        if (eventService.getError().isEmpty()) {
            return "redirect:/Event/campaignSpecification";
        }
        return "redirect:/Event/eventShowAllAppoint";
    }

    @RequestMapping("/eventClient")
    public String eventClient(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "assigned", required = false) Integer assigned,
            @RequestParam(value = "processed", required = false) Integer processed,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("events", eventService.getEventFilter(campaignId, cabinetId, assigned, processed));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        model.put("assignedMap", getAssignedMap(eventService.listRoleUserActiveCabinetUser(cabinetId)));
        model.put("proceededMap", getProceededMap());
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getError());
        return "eventClient";
    }

    private LinkedHashMap<Long, String> getAssignedMap(List<CabinetUser> lcu) {
        LinkedHashMap<Long, String> result = new LinkedHashMap();
        result.put(Long.valueOf(0), "Не выбрано");
        result.put(Long.valueOf(-1), "Не назначено");
        result.put(Long.valueOf(-2), "Назначено");
        for (CabinetUser cu : lcu) {
            result.put(cu.getId(), cu.getUser().getSurname() + " " + cu.getUser().getName());
        }
        return result;
    }

    private LinkedHashMap<Long, String> getProceededMap() {
        LinkedHashMap<Long, String> result = new LinkedHashMap();
        result.put(Long.valueOf(0), "Не выбрано");
        result.put(Long.valueOf(-1), "Не обработано");
        result.put(Long.valueOf(-2), "Успешно");
        result.put(Long.valueOf(-3), "Не Успешно");
        result.put(Long.valueOf(-4), "Обработано");
        return result;
    }

    @RequestMapping("/event")
    public String eventPage(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "eventId", required = false) Long eventId,
            HttpServletRequest request/*,
            RedirectAttributes ras*/) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Long userId = user.getUserId();
        Long strategyId = eventService.getStrategyId(campaignId);

        if (eventId == null) {
            model.put("event", eventService.getEvenByUserByCampaign(campaignId, cabinetId, userId));
        } else {
            model.put("event", eventService.getEventById(eventId));
        }
        model.put("drainList", eventService.getDrainList(strategyId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("errors", eventService.getError());
        model.put("strategy", strategyService.getStrategy(strategyId));
        model.put("аctiveMap", groupService.getActiveMap(strategyId));
        /*ras.addFlashAttribute("eventId", eventId);
        ras.addFlashAttribute("campaignId", campaignId);
        ras.addFlashAttribute("strategyId", strategyId);
        ras.addFlashAttribute("userId", userId);
        ras.addFlashAttribute("cabinetId", cabinetId);*/
        return "event";
    }

    @RequestMapping("/campaign")
    public String campaignPage(Map<String, Object> model,
            HttpServletRequest request,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Long userId = user.getUserId();

        //model.put("campaigns", eventService.userShowPageEventClientList(cabinetId, userId));
        model.put("campaigns", eventService.userShowPageEventClientList(cabinetId, userId));
        return "campaign";
    }
    
    @RequestMapping("updateClientFromUser")
    @ResponseBody
    public String updateClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,@RequestParam(value = "param") String param,@RequestParam(value = "newVal") String newVal, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        boolean performed = clientService.updateClientField(param, clientId, newVal);
        return StringAdapter.getString(performed);
    }
    
    @RequestMapping("writeModuleInHistory")
    @ResponseBody
    public String writeModuleInHistory(Map<String, Object> model,@RequestParam(value = "moduleId") Long moduleId,@RequestParam(value = "eventId") Long eventId,@RequestParam(value = "date") Long datelong, HttpServletRequest request) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Date date = new Date(datelong);
        boolean performed = eventService.writeModuleInHistory(date, user.getId(), cabinetId, moduleId,eventId);
        return StringAdapter.getString(date);
    }
    
    @RequestMapping("/badFinish")
    public String badFinish(Map<String, Object> model,@RequestParam(value = "eventId") Long eventId,@RequestParam(value = "drainId") Long drainId,
            @RequestParam(value = "campaignId") Long campaignId,@RequestParam(value = "comment") String comment,RedirectAttributes ras,HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        if(comment.equals("")){
            comment="Без комментариев";
        }
        eventService.badFinish(eventId, drainId, comment);

        ras.addFlashAttribute("errors",eventService.getError());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/event";
    }
    
    @RequestMapping("/goodFinish")
    public String goodFinish(Map<String, Object> model,@RequestParam(value = "eventId") Long eventId,@RequestParam(value = "successDate") Date successDate,
            @RequestParam(value = "campaignId") Long campaignId,@RequestParam(value = "comment") String comment,RedirectAttributes ras,HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        if(comment.equals("")){
            comment="Без комментариев";
        }
        //Date successDate = new Date(successLongDate);
        eventService.goodFinish(eventId, successDate, comment);

        ras.addFlashAttribute("errors",eventService.getError());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/event";
    }

    /*@RequestMapping("/eventProcessing")
    public String eventProcessing(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "groupId") Long groupId,
            @RequestParam(value = "moduleId") Long moduleId,
            @RequestParam(value = "eventId") Long eventId,
            HttpServletRequest request,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Long userId = user.getUserId();
        Long strategyId = eventService.getStrategyId(campaignId);

        model.put("drainList", eventService.getDrainList(strategyId));
        model.put("module", moduleService.showModule(moduleId, cabinetId));
        model.put("strategy", strategyService.getStrategy(strategyId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("errors", eventService.getError());
        model.put("event", eventService.getEventById(eventId));
        model.put("аctiveMap", groupService.getActiveMap(strategyId));
        return "event";
    }*/
}
