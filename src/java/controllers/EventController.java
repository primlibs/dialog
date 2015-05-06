/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public String showEventTaskPage(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);

        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);

        model.put("errors", eventService.getError());
        model.put("listUser", eventService.listRoleUserActiveCabinetUser(cabinetId));
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
            @RequestParam(value = "eventId") Long eventId) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Boolean update=false;
        if(checkbox!=null){
            update = true;
        }
        eventService.readXls(fileXls, cabinetId, eventId, update);
        model.put("errors", eventService.getError());
        model.put("message", "Залилось удачно");
       // model.put("listUser", eventService.listRoleUserActiveCabinetUser(cabinetId));
        return "eventTask";
    }
}
