/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.put("strategytList", eventService.eventList(cabinetId));
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
}
