/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import entities.User;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.TaskService;
import service.UserService;
import support.DateAdapter;
import support.commons.Right;


@RequestMapping("/Task")
@Controller
public class TaskController extends WebController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @RequestMapping("/taskList")
    @Right(description = "Задачи",name = "task")
    public String taskList(Map<String, Object> model,
        HttpServletRequest request) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Date from=DateAdapter.getStartOfDate(new Date());
        Date to=DateAdapter.getEndOfDate(new Date());
        model.put("userMap",userService.getMakingCallsAndParticipatedUsersMap(cabinetId));
        model.put("taskList",taskService.getTaskList(from,to,cabinetId));
        model.put("errors", taskService.getErrors());
        return "taskList";
    }
    
    @RequestMapping("/addTask")
    @Right(description = "Задачи",name = "task")
    public String addtask(Map<String, Object> model,HttpServletRequest request,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "taskDate", required = false) Date taskDate,  
        @RequestParam(value = "performerId", required = false) Long performerId,
        RedirectAttributes ras) throws Exception {
            Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
            User us=userService.getUser(performerId);
            taskService.save(name, currentUser, taskDate, cabinetId);
            ras.addFlashAttribute("errors", taskService.getErrors());
            return "redirect:/Task/taskList";
    }

}
