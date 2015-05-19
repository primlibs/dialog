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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.EventService;
import support.AuthManager;

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
    private AuthManager authManager;

    @RequestMapping("/eventList")
    public String showEventListPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("eventList", eventService.campaignList(cabinetId));
        model.put("errors", eventService.getError());
        return "eventList";
    }

    @RequestMapping("/eventAdd")
    public String eventAdd(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "strategyId", required = false) Long strategyId,
            RedirectAttributes ras
    ) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (strategyId != null) {
            eventService.eventAdd(name, strategyId, cabinetId);
            if (eventService.getError().isEmpty()) {
                ras.addFlashAttribute("message", "Евент " + name + " успешно создан");
                return "redirect:/Event/eventList";
            }
        }
        model.put("numericName", eventService.numericName(cabinetId));
        model.put("strategytList", eventService.strategytList(cabinetId));
        model.put("errors", eventService.getError());
        return "eventAdd";
    }

    @RequestMapping("/eventTask")
    public String showEventTaskPage(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "eventId"//, required = false
            ) Long eventId) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("errors", eventService.getError());
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        model.put("userAssignedClient", eventService.userAssignedClient(eventId, cabinetId));

        model.put("userAssignedClientNotProcessed", eventService.userAssignedClientNotProcessed(eventId, cabinetId));
        model.put("userAssignedClientProcessed", eventService.userAssignedClientProcessed(eventId, cabinetId));
        model.put("userAssignedClientProcessedSuccess", eventService.userAssignedClientProcessedSuccess(eventId, cabinetId));
        model.put("userAssignedClientProcessedFails", eventService.userAssignedClientProcessedFails(eventId, cabinetId));

        model.put("eventList", eventService.getEventList(eventId, cabinetId));
        model.put("unassignedEventList", eventService.getUnassignedEvent(eventId, cabinetId));
        model.put("event", eventService.getCampaign(eventId));
        return "eventTask";
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
            @RequestParam(value = "eventId") Long eventId,
            RedirectAttributes ras) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Boolean update = false;
        if (checkbox != null) {
            update = true;
        }
        eventService.readXls(fileXls, cabinetId, eventId, update);
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        if (eventService.getError().isEmpty()) {
            ras.addFlashAttribute("message", "Клиенты успешно добавлены");
        }
        ras.addFlashAttribute("event", eventService.getCampaign(eventId));
        return "redirect:/Event/eventTask";
    }

    @RequestMapping("/eventAppointSave")
    public String saveAppointEvent(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "arrayClientIdUserId") String[] arrayClientIdUserId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        eventService.eventAppointSave(arrayClientIdUserId, cabinetId, eventId);
        model.put("clientList", eventService.getClientList(eventId, cabinetId));
        model.put("event", eventService.getCampaign(eventId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("event", eventService.getCampaign(eventId));
        return "redirect:/Event/eventTask";
    }

    @RequestMapping("/eventShowAllAppoint")
    public String showAllAppointEvent(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("eventAllAppoint", eventService.eventAppointAll(eventId, cabinetId));

        model.put("event", eventService.getCampaign(eventId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("event", eventService.getCampaign(eventId));
        return "eventAppointAll";
    }

    @RequestMapping("/eventAppointSaveAll")
    public String saveAllAppointEvent(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "clientNum") String[] clientNumArray,
            @RequestParam(value = "userId") Long[] userIdArray,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        eventService.eventAppointSaveAll(eventId, cabinetId, userIdArray, clientNumArray);

        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        model.put("event", eventService.getCampaign(eventId));
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("event", eventService.getCampaign(eventId));

        if (eventService.getError().isEmpty()) {
            return "redirect:/Event/eventTask";
        }
        return "redirect:/Event/eventShowAllAppoint";
    }

    @RequestMapping("/eventClient")
    public String eventClient(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "assigned", required = false) Integer assigned,
            @RequestParam(value = "processed", required = false) Integer processed,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("eventClientLink", eventService.getEventFilter(eventId, cabinetId, assigned, processed));
        model.put("event", eventService.getCampaign(eventId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        model.put("assignedMap", getAssignedMap(eventService.listRoleUserActiveCabinetUser(cabinetId)));
        model.put("proceededMap", getProceededMap());
        ras.addAttribute("eventId", eventId);
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
        result.put(Long.valueOf(-1), "Не назначено");
        result.put(Long.valueOf(-2), "Назначено");
        return result;
    }

    @RequestMapping("/event")
    public String eventPage(Map<String, Object> model,
            @RequestParam(value = "eventId", required = false) Long eventId,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        return "event";
    }

    @RequestMapping("/campaign")
    public String campaignPage(Map<String, Object> model,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        User user = authManager.getCurrentUser();
        Long userId = user.getUserId();

        model.put("eventClientList", eventService.userShowPageEventClientList(cabinetId, userId));

        return "campaign";
    }

}
