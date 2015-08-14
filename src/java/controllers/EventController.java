/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import dao.EventDao;
import entities.CabinetUser;
import entities.Campaign;
import entities.Event;
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
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
import service.ReportService;
import service.StrategyService;
import service.TagService;
import service.UserService;
import support.AuthManager;
import support.DateAdapter;
import support.JsonResponse;
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
    
    /*@Autowired
    private EventDao eventDao;*/

    @Autowired
    private GroupService groupService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private StrategyService strategyService;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private TagService tagService;

    @RequestMapping("/campaignList")
    public String showCampaigns(Map<String, Object> model,
            /*@RequestParam(value = "dateFrom", required = false) Date dateFrom,
            @RequestParam(value = "dateTo", required = false) Date dateTo,
            @RequestParam(value = "closed", required = false) String closed,*/
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        /*boolean showClosed = false;
        if(closed!=null){
            showClosed=true;
        }
        
        if(dateFrom==null){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            dateFrom=DateAdapter.getDateFromString(DateAdapter.formatByDate(c.getTime(),DateAdapter.SMALL_FORMAT));
        }
        if(dateTo==null){
            dateTo=DateAdapter.getDateFromString(DateAdapter.formatByDate(new Date(),DateAdapter.SMALL_FORMAT));
        }*/

        model.put("campaignsWithCountInfosMap", eventService.getCampaignsWithCountInfos(/*dateFrom,dateTo,showClosed,*/cabinetId));
        model.put("errors", eventService.getErrors());
        /*model.put("dateFrom", dateFrom);
        model.put("dateTo", dateTo);*/
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
            if (eventService.getErrors().isEmpty()) {
                ras.addFlashAttribute("message", "Кампания " + name + " успешно создана");
                return "redirect:/Event/campaignList";
            }
        }
        model.put("numericName", eventService.numericName(cabinetId));
        model.put("strategies", eventService.getStrategies(cabinetId));
        model.put("errors", eventService.getErrors());
        return "createCampaign";
    }

    @RequestMapping("/deleteCampaign")
    public String deleteCampaign(Map<String, Object> model, HttpServletRequest request,
            @RequestParam(value = "campaignId", required = false) Long campaignId, RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (campaignId != null) {
            eventService.deleteCampaign(campaignId, cabinetId);
            if (eventService.getErrors().isEmpty()) {
                return "redirect:/Event/campaignList";
            } else {
                ras.addFlashAttribute("errors", eventService.getErrors());
                ras.addAttribute("campaignId", campaignId);
                return "redirect:/Event/campaignSpecification";
            }
        }
        List<String> errors = new ArrayList();
        errors.addAll(eventService.getErrors());
        errors.add("Ид кампании не передан.");
        ras.addFlashAttribute("errors", errors);
        return "redirect:/Event/campaignList";
    }
    
    @RequestMapping("/closeCampaign")
    public String closeCampaign(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "campaignId", required = false) Long campaignId,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.closeCampaign(campaignId, cabinetId);
        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/campaignSpecification";
    }
    
    @RequestMapping("/openCampaign")
    public String openCampaign(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "campaignId", required = false) Long campaignId,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.openClosedCampaign(campaignId, cabinetId);
        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/campaignSpecification";
    }

    @RequestMapping("/campaignSpecification")
    public String showCampaignSpecification(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "dateFrom", required=false) Date dateFrom,
            @RequestParam(value = "dateTo", required=false) Date dateTo,
            @RequestParam(value = "wropen", required=false) Integer wropen) throws Exception {

        List<String> errors = (List<String>) model.get("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("participatedUsers",eventService.getUserWithAssignsList(campaignId,cabinetId));
        //model.put("participatedCUsers",eventService.getSurnameSortedCUListForCampaignSpecification(campaignId,cabinetId));
        model.put("cabinetUserList", eventService.getActiveMakingCallsUsers(cabinetId));
        model.put("userAssignedClient", eventService.userAssignedClient(campaignId, cabinetId));
        model.put("deleteble", true/*eventService.isDeleteble(campaignId, cabinetId)*/);

        model.put("userAssignedClientProcessedSuccess", eventService.userAssignedClientProcessedSuccess(campaignId, cabinetId));
        model.put("userAssignedClientProcessedFails", eventService.userAssignedClientProcessedFails(campaignId, cabinetId));
        model.put("userAssignedClientProcessed", eventService.userAssignedClientProcessed(campaignId, cabinetId));
        model.put("userAssignedClientNotProcessed", eventService.userAssignedClientNotProcessed(campaignId, cabinetId));

        model.put("eventList", eventService.getEventList(campaignId, cabinetId));
        model.put("unassignedEventList", eventService.getUnassignedEvent(campaignId, cabinetId));
        Campaign campaign = eventService.getCampaign(campaignId);
        model.put("campaign", campaign);
        
        model.put("moduleReportData",reportService.getDataByModules(campaignId,cabinetId));
        model.put("workReportData",reportService.getDataForWorkReport(cabinetId,campaignId,dateFrom,dateTo));
        model.put("failReasonReportData",reportService.getDataForFailReasonReport(campaignId,cabinetId));
        if(dateFrom==null){
            dateFrom=campaign.getCreationDate();
        }
        if(dateTo==null){
            if(campaign.getEndDate()!=null){
                dateTo=campaign.getEndDate();
            }else{
                dateTo=new Date();
            }
        }
        model.put("wropen",wropen);
        model.put("dateFrom",DateAdapter.formatByDate(dateFrom, DateAdapter.SMALL_FORMAT));
        model.put("dateTo",DateAdapter.formatByDate(dateTo, DateAdapter.SMALL_FORMAT));
        model.put("tags", tagService.getAllActiveTags(cabinetId));
        
        errors.addAll(eventService.getErrors());
        errors.addAll(reportService.getErrors());
        model.put("errors", errors);
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
            @RequestParam(value = "tagIds",required = false) Long[] tagIds,
            RedirectAttributes ras) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Boolean update = false;
        if (checkbox != null) {
            update = true;
        }
        eventService.readXls(fileXls,tagIds, cabinetId, campaignId, update);
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getErrors());
        if (eventService.getErrors().isEmpty()) {
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
        model.put("cabinetUserList", eventService.getActiveMakingCallsUsers(cabinetId));
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));
        return "redirect:/Event/campaignSpecification";
    }

    @RequestMapping("/eventShowAllAppoint")
    public String showAllAppointEvent(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        List<String> errors = (List<String>) model.get("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("eventAllAppoint", eventService.eventAppointAll(campaignId, cabinetId));
        model.put("clientCount", eventService.getNotAssignedClients(campaignId, cabinetId).size());
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("cabinetUserList", eventService.getActiveMakingCallsUsers(cabinetId));
        errors.addAll(eventService.getErrors());
        model.put("errors", errors);
        return "eventAppointAll";
    }

    @RequestMapping("/eventAppointSaveAll")
    public String saveAllAppointEvent(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "clientNum", required = false) String[] clientNumArray,
            @RequestParam(value = "userId", required = false) Long[] userIdArray,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        eventService.eventAppointSaveAll(campaignId, cabinetId, userIdArray, clientNumArray);

        model.put("cabinetUserList", eventService.getActiveMakingCallsUsers(cabinetId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("errors", eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));

        if (eventService.getErrors().isEmpty()) {
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
        List<String> errors = (List<String>) model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        
        model.put("events", eventService.getEventFilter(campaignId, cabinetId, assigned, processed));
        //errors.add("finalEvsCount="+eventService.getEventFilter(campaignId, cabinetId, assigned, processed).size()+";");
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("cabinetUserList", eventService.getActiveMakingCallsUsers(cabinetId));
        model.put("assignedMap", getAssignedMap(eventService.getActiveMakingCallsUsers(cabinetId)));
        model.put("proceededMap", getProceededMap());
        model.put("campaignId", campaignId);
        model.put("assigned", assigned);
        errors.addAll(eventService.getErrors());
        model.put("errors", errors);
        return "eventClient";
    }
    
    @RequestMapping("/eventClientXLS")
    public void geteventClientXLS(Map<String, Object> model,
            HttpServletResponse response,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "assigned", required = false) Integer assigned,
            @RequestParam(value = "processed", required = false) Integer processed,
            HttpServletRequest request) throws IOException, Exception{
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        if(assigned==null){
            assigned=0;
        }
        if(processed==null){
            processed=0;
        }
        String fileName ="campaign_report_";
        Campaign c = eventService.getCampaign(campaignId);
        fileName+=c.getId();
        if(assigned==-2){
            fileName+="_assigned";
        }else if(assigned==-1){
            fileName+="_notassigned";
        }else if(assigned==0){
            fileName+="_notchosen";
        }else{
            User u= userService.getUser(Long.valueOf(assigned));
            fileName+="_"+u.getEmail();
        }
        
        if(processed==-4){
            fileName+="_performed";
        }else if(processed==-3){
            fileName+="_failed";
        }else if(processed==-2){
            fileName+="_successful";
        }else if(processed==-1){
            fileName+="_notperformed";
        }else if(processed==0){
            fileName+="_notchosen";
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
        eventService.getEventClientXls(campaignId,assigned,processed,cabinetId).write(response.getOutputStream());
        //eventService.getXls().write(response.getOutputStream());
    }

    private LinkedHashMap<Long, String> getAssignedMap(List<CabinetUser> lcu) {
        LinkedHashMap<Long, String> result = new LinkedHashMap();
        result.put(Long.valueOf(0), "Не выбрано");
        result.put(Long.valueOf(-1), "Не назначено");
        result.put(Long.valueOf(-2), "Назначено");
        for (CabinetUser cu : lcu) {
            result.put(cu.getUser().getId(), cu.getUser().getSurname() + " " + cu.getUser().getName());
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
            HttpServletRequest request,
            RedirectAttributes ras) throws Exception {
        List<String>errors = (List<String>)model.get("errors");
        if(errors==null){
            errors = new ArrayList();
        }
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Long userId = user.getUserId();
        Long strategyId = eventService.getStrategyId(campaignId);

        if (eventId == null) {
            Event ev = eventService.getEventByUserAndCampaign(campaignId, cabinetId, userId);
            if (ev == null) {
                ras.addFlashAttribute("message", eventService.getMessages());
                return "redirect:/Event/campaign";
            }
            //eventService.clearHistory(ev.getEventId(),cabinetId);
            model.put("event", ev);
        } else {
            Event ev = eventService.getAvailableEventById(eventId);
            model.put("event", ev);
            //eventService.clearHistory(ev.getEventId(),cabinetId);
        }
        model.put("failReasons", eventService.getAllFailReasons(strategyId));
        model.put("campaign", eventService.getCampaign(campaignId));
        //model.put("errors", eventService.getErrors());
        model.put("strategy", strategyService.getStrategy(strategyId));
        model.put("аctiveMap", groupService.getActiveGroupMap(strategyId,cabinetId));
        errors.addAll(eventService.getErrors());
        model.put("errors", errors);
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
    public JsonResponse updateClientOrEvent(Map<String, Object> model, @RequestParam(value = "eventId",required = false) Long eventId,
            @RequestParam(value = "clientId") Long clientId, @RequestParam(value = "param") String param, 
            @RequestParam(value = "newVal") String newVal, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        JsonResponse res = JsonResponse.getInstance();

        clientService.updateClientField(param, clientId,eventId, newVal,cabinetId);
        List<String>errs=(List<String>)model.get("errors");
        if(errs==null){
            errs=new ArrayList();
        }
        List<String>serviceErrs=clientService.getErrors();
        if(serviceErrs==null){
            serviceErrs=new ArrayList();
        }
        errs.addAll(serviceErrs);
        
        if (errs.isEmpty()) {
            res.setStatus(Boolean.TRUE);
            //return StringAdapter.getString(true);
            return res;
        } else {
            res.setStatus(Boolean.FALSE);
            String err = "";
            for (String s : errs) {
                err += s + "; ";
            }
            //return "Ошибка: " + err;
            res.setMessage(err);
            return res;
        }
    }

    /*@RequestMapping("writeModuleInHistory")
    @ResponseBody
    public String writeModuleInHistory(Map<String, Object> model, @RequestParam(value = "moduleId") Long moduleId, @RequestParam(value = "eventId") Long eventId, @RequestParam(value = "date") Long datelong, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Date date = new Date(datelong);
        boolean performed = eventService.writeModuleInHistory(date, user.getId(), cabinetId, moduleId, eventId);
        return StringAdapter.getString(date);
    }*/

    @RequestMapping("/badFinish")
    public String badFinish(Map<String, Object> model, @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "failReasonId") Long failReasonId,@RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "comment") String finalComment,@RequestParam(value = "modules",required = false) Long[] modules,
            @RequestParam(value = "dates",required = false) Long[] dates,RedirectAttributes ras, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        
        eventService.badFinish(modules,dates,cabinetId,eventId, failReasonId, finalComment);

        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/event";
    }

    @RequestMapping("/goodFinish")
    public String goodFinish(Map<String, Object> model, @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "successDate") Date successDate,@RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "comment") String finalComment,@RequestParam(value = "modules",required = false) Long[] modules,
            @RequestParam(value = "dates",required = false) Long[] dates,RedirectAttributes ras, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        /*if (finalComment.equals("")) {
            finalComment = "Без комментариев";
        }*/
        //Date successDate = new Date(successLongDate);
        eventService.goodFinish(modules,dates,cabinetId,eventId, successDate, finalComment);

        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/event";
    }

    @RequestMapping("/postponeEvent")
    public String postponeEvent(Map<String, Object> model, @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "postponeDate") Date postponeDate,@RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "comment") String finalComment,@RequestParam(value = "modules",required = false) Long[] modules,
            @RequestParam(value = "dates",required = false) Long[] dates,RedirectAttributes ras, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        //Date successDate = new Date(successLongDate);
        eventService.postponeEvent(modules,dates,cabinetId,eventId, postponeDate, finalComment);

        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/event";
    }

    @RequestMapping("/postponedEvents")
    public String showPostponedEvents(Map<String, Object> model, @RequestParam(value = "dateTo", required = false) Date dateTo,
            @RequestParam(value = "dateFrom", required = false) Date dateFrom, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        User user = authManager.getCurrentUser();
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("postponedEvents", eventService.getPostponedEvents(dateFrom, dateTo,user.getId(), cabinetId));
        model.put("errors", eventService.getErrors());
        return "postponedEvents";
    }

    @RequestMapping("/assignEvent")
    public String assignEvent(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "arrayClientIdUserId") String[] arrayClientIdUserId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.eventAppointSave(arrayClientIdUserId, cabinetId, campaignId);
        model.put("clientList", eventService.getClientList(campaignId, cabinetId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("cabinetUserList", eventService.getActiveMakingCallsUsers(cabinetId));
        ras.addAttribute("campaignId", campaignId);
        ras.addFlashAttribute("errors", eventService.getErrors());
        ras.addFlashAttribute("campaign", eventService.getCampaign(campaignId));
        return "redirect:/Event/campaignSpecification";
    }

    @RequestMapping("/assignOneEvent")
    public String assignOneEvent(Map<String, Object> model, @RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "eventId") Long eventId, RedirectAttributes ras, HttpServletRequest request) throws Exception {
        List<String>errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.assignOneEvent(userId, eventId,cabinetId);
        ras.addAttribute("campaignId", campaignId);
        ras.addAttribute("assigned", userId);
        errors.addAll(eventService.getErrors());
        ras.addFlashAttribute("errors", errors);
        return "redirect:/Event/eventClient";
    }

    @RequestMapping("/showAssigningOneEvent")
    public String showAssigningOneEvent(Map<String, Object> model, @RequestParam(value = "campaignId") Long campaignId,
            @RequestParam(value = "eventId") Long eventId, RedirectAttributes ras, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        model.put("calingUsers", eventService.getActiveMakingCallsUsers(cabinetId));
        model.put("campaignId", campaignId);
        model.put("eventId", eventId);
        model.put("errors", eventService.getErrors());
        return "assignOneEvent";
    }
    
    @RequestMapping("/changeUserCampaignAssignation")
    public String changeUserCampaignAssignation(Map<String, Object> model, @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "userFromId", required = false) Long userFromId,@RequestParam(value = "userToId",required = false) Long userToId, RedirectAttributes ras, HttpServletRequest request) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.changeUserCampaignAssignation(campaignId, userFromId, userToId, cabinetId);
        ras.addFlashAttribute("errors",eventService.getErrors());
        ras.addAttribute("campaignId", campaignId);
        return "redirect:/Event/campaignSpecification";
    }
    
    @RequestMapping("/moduleReportDetalisation")
    public String moduleReportDetalisation(Map<String, Object> model, @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "moduleId",required = false) Long moduleId, RedirectAttributes ras, HttpServletRequest request) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        model.put("events", reportService.getEventDetalisationByModuleId(moduleId, campaignId, cabinetId));
        model.put("module",moduleService.getModule(moduleId));
        model.put("campaign",eventService.getCampaign(campaignId));
        return "failModuleReportDetalisation";
    }
    
    @RequestMapping("/moduleReportDetalisationXLS")
    public void moduleReportDetalisationXLS(Map<String, Object> model,
            @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "moduleId",required = false) Long moduleId,
             RedirectAttributes ras, 
            HttpServletResponse response, 
            HttpServletRequest request) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        String fileName = "module_report_on_"+campaignId;
        
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
        reportService.getModuleReportDetalisationXls(moduleId, campaignId, cabinetId).write(response.getOutputStream());
    }
    
    @RequestMapping("/workReportDetalisation")
    public String workReportDetalisation(Map<String, Object> model, @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "userId",required = false) Long userId,
             @RequestParam(value = "dateFrom",required = false) Date dateFrom,
             @RequestParam(value = "dateTo",required = false) Date dateTo,
             @RequestParam(value = "status",required = false) Integer status,
             RedirectAttributes ras, HttpServletRequest request) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        model.put("events", reportService.getDataForWorkDetalisation(status,dateFrom,dateTo,userId, campaignId, cabinetId));
        model.put("user",userService.getUser(userId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("errors",reportService.getErrors());
        model.put("userId", userId);
        model.put("status", status);
        model.put("dateFrom", dateFrom);
        model.put("dateTo", dateTo);
        return "workReportDetalisation";
    }
    
    @RequestMapping("/workReportDetalisationXLS")
    public void workReportDetalisationXLS(Map<String, Object> model, 
            HttpServletResponse response, 
            HttpServletRequest request,
            @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "userId",required = false) Long userId,
             @RequestParam(value = "dateFrom",required = false) Date dateFrom,
             @RequestParam(value = "dateTo",required = false) Date dateTo,
             @RequestParam(value = "status",required = false) Integer status) throws Exception {
        
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        response.setContentType("application/octet-stream");
        
        String fileName = "work_report_on_"+campaignId+"_"+StringAdapter.getString(dateFrom)+"-"+StringAdapter.getString(dateTo);
        
        if(userId!=null){
            fileName+="_"+userService.getUser(userId).getEmail();
        }else{
            fileName+="_all";
        }
        
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
        reportService.getWorkReportDetalisationXls(status,dateFrom,dateTo,userId, campaignId, cabinetId).write(response.getOutputStream());
    }
    
    @RequestMapping("/failReasonReportDetalisation")
    public String failReasonReportDetalisation(Map<String, Object> model, @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "failReasonId",required = false) Long failReasonId,
             RedirectAttributes ras, HttpServletRequest request) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        model.put("events", reportService.getDataForFailReasonDeatlisation(failReasonId, campaignId, cabinetId));
        model.put("campaign", eventService.getCampaign(campaignId));
        model.put("failReasonId", failReasonId);
        model.put("errors",reportService.getErrors());
        return "failReasonReportDetalisation";
    }
    
    @RequestMapping("/failReasonReportDetalisationXLS")
    public void failReasonReportDetalisationXLS(Map<String, Object> model, 
            HttpServletResponse response, 
            HttpServletRequest request,
            @RequestParam(value = "campaignId") Long campaignId,
             @RequestParam(value = "failReasonId",required = false) Long failReasonId,
             RedirectAttributes ras) throws Exception{
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        response.setContentType("application/octet-stream");
        
        String fileName = "fail_reason_report_on_"+campaignId;
        
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
        reportService.getFailReasonReportDetalisationXls(failReasonId, campaignId, cabinetId).write(response.getOutputStream());
    }
    
    @RequestMapping("setShowModulesWithText")
    @ResponseBody
    public JsonResponse setShowModulesWithText(Map<String, Object> model,
            @RequestParam(value = "campaignId",required = false) Long campaignId,
            @RequestParam(value = "show",required = false) String show, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        JsonResponse res = JsonResponse.getInstance();
        
        eventService.setShowModulesWithText(Boolean.valueOf(show),campaignId,cabinetId);
        List<String>errs=(List<String>)model.get("errors");
        if(errs==null){
            errs=new ArrayList();
        }
        
        if (errs.isEmpty()) {
            res.setStatus(Boolean.TRUE);
        } else {
            res.setStatus(Boolean.FALSE);
            String err = "";
            for (String s : errs) {
                err += s + "; ";
            }
            res.setMessage(err);
        }
        //String s = res.getMessage();
        //s+=":"+show+":"+Boolean.valueOf(show)+":";
        //res.setMessage(s);
        return res;
    }
    
    
    /*@RequestMapping("/summarizedModuleReport")
    public String showModuleReport(Map<String, Object> model,@RequestParam(value = "campaignId") Long campaignId, RedirectAttributes ras, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("reportData",reportService.getDataByModules(campaignId,cabinetId));
        //model.put("users",reportService.getUserList(cabinetId));
        //model.put("modules", moduleService.getAllModulesMap(cabinetId));
        model.put("errors", reportService.getErrors());
        return "moduleReport";
    }*/

}
