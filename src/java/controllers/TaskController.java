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
import service.TaskService;
import support.DateAdapter;


@RequestMapping("/Task")
@Controller
public class TaskController extends WebController {

    @Autowired
    private TaskService taskService;


    @RequestMapping("/taskList")
    public String taskList(Map<String, Object> model,
            
            HttpServletRequest request) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Date from=DateAdapter.getStartOfDate(new Date());
        Date to=DateAdapter.getEndOfDate(new Date());
        taskService.getTaskList(from,to,cabinetId);
        model.put("errors", taskService.getErrors());
        return "taskList";
    }

}
