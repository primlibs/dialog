/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.lang.reflect.Array;
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

    @RequestMapping("/eventList")
    public String showEventListPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("eventList", eventService.eventList(cabinetId));
        model.put("errors", eventService.getError());
        return "eventList";
    }

    @RequestMapping("/eventAdd")
    public String eventAdd(Map<String, Object> model,
            HttpServletRequest request,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "strategyId", required = false) Long strategyId
    ) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        if (strategyId != null) {
            eventService.eventAdd(name, strategyId, cabinetId);
            if (eventService.getError().isEmpty()) {
                model.put("message", "Евент " + name + " успешно создан");
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
        model.put("eventClientLinkList", eventService.getEventClientLinkList(eventId, cabinetId));
        model.put("unassignedEventClientLinkList", eventService.getUnassignedEventClientLink(eventId, cabinetId));
        model.put("event", eventService.getEvent(eventId));
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
        ras.addFlashAttribute("event", eventService.getEvent(eventId));
        // model.put("listUser", eventService.listRoleUserActiveCabinetUser(cabinetId));
        return "redirect:/Event/eventTask";
    }

    @RequestMapping("/eventAppoint")
    public String appointEvent(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("clientList", eventService.getClientList(eventId, cabinetId));
        model.put("event", eventService.getEvent(eventId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("event", eventService.getEvent(eventId));
        return "eventAppoint";
    }

    @RequestMapping("/eventClient")
    public String eventClient(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("clientList", eventService.getClientList(eventId, cabinetId));
        model.put("event", eventService.getEvent(eventId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("event", eventService.getEvent(eventId));
        return "eventClient";
    }

    @RequestMapping("/eventAppointSave")
    public String saveAppointEvent(Map<String, Object> model,
            @RequestParam(value = "eventId") Long eventId,
            @RequestParam(value = "arrayClientIdUserId") Array arrayClientIdUserId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("clientList", eventService.getClientList(eventId, cabinetId));
        model.put("event", eventService.getEvent(eventId));
        model.put("cabinetUserList", eventService.listRoleUserActiveCabinetUser(cabinetId));
        ras.addAttribute("eventId", eventId);
        ras.addFlashAttribute("errors", eventService.getError());
        ras.addFlashAttribute("event", eventService.getEvent(eventId));
        return "eventAppoint";
    }

}
