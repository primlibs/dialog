/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PersonalCabinetDao;
import dao.TaskDao;
import entities.Task;
import entities.User;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import service.parent.PrimService;
import support.StringAdapter;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TaskService extends PrimService {

    @Autowired
    private TaskDao taskDao;

    @Autowired 
    private PersonalCabinetDao personalCabinetDao;

    
    public List<Task> getTaskList(Date from,Date to,Long pkId){
        return taskDao.getActiveTask(Date from,Date to,Long pkId);
    }
    
    
    
    public void closeTask(Long taskId, Long pkId, Integer closeType, Date closeDate) {
        if (taskId != null) {
            Task task = taskDao.find(taskId);
            if (task != null) {
                if (closeType != null && closeDate != null) {
                    if (task.getCabinet().getPkId().equals(pkId)) {
                        task.setCloseType(closeType);
                        task.setCloseDate(closeDate);
                        taskDao.update(task);
                    } else {
                        addError("задача не относится к личному кабинету");
                    }
                } else {
                    addError("передайте дату и тип закрытия");
                }
            } else {
                addError("Задача не найдена по ИД: " + taskId);
            }
        } else {
            addError("Ид задачи не передан");
        }
    }

    public void uncloseTask(Long taskId, Long pkId) {
        if (taskId != null) {
            Task task = taskDao.find(taskId);
            if (task != null) {
                if (task.getCabinet().getPkId().equals(pkId)) {
                    task.setCloseType(null);
                    task.setCloseDate(null);
                    taskDao.update(task);
                } else {
                    addError("задача не относится к личному кабинету");
                }
            } else {
                addError("Задача не найдена по ИД: " + taskId);
            }
        } else {
            addError("Ид задачи не передан");
        }
    }


    public void update(String name, User performer,Long taskId,Date performDate, Long pkId) {
        if (StringAdapter.NotNull(name,performer,taskId,performDate,pkId)) {
            Task task = taskDao.find(taskId);
            if (task != null) {
                if (task.getCabinet().getPkId().equals(pkId)) {
                    task.setName(name);
                    task.setPerformer(performer);
                    task.setPerformDate(performDate);
                    taskDao.update(task);
                } else {
                    addError("задача не относится к личному кабинету");
                }
            } else {
                addError("Задача не найдена по ИД: " + taskId);
            }
        } else {
            addError("Один из параметров не передан");
        }
    }
    
    public void save(String name, User performer,Date performDate, Long pkId) {
        if (StringAdapter.NotNull(name,performer,performDate,pkId)) {
            Task task= new Task();
            task.setName(name);
            task.setPerformer(performer);
            task.setPerformDate(performDate);
            taskDao.update(task);
        } else {
            addError("Один из параметров не передан");
        }
    }



}
