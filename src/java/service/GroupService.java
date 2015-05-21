/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDao;
import dao.ModuleDao;
import dao.StrategyDao;
import entities.Group;
import entities.Module;
import entities.Strategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import service.parent.PrimService;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GroupService extends PrimService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private StrategyDao strategyDao;

    public void deleteGroup(Long groupId) {

        Group group = groupDao.find(groupId);

        if (groupId != null) {
            deleteGroup(group);
        } else {
            addError("группа не найдена по: " + groupId);
        }
    }

    public void deleteGroup(Group group) {

        List<Module> moduleList = group.getModuleList();
        Date date = new Date();
        //  moduleList.removeAll(moduleList);

        for (Module modul : moduleList) {
            modul.setDeleteDate(date);
            moduleDao.update(modul);
        }

        group.setDeleteDate(date);
        groupDao.update(group);
    }


    public List<Group> getActiveGroupList(Long strategyId) {
        Strategy st = strategyDao.find(strategyId);
        List<Group> activeGroupList = new ArrayList();
        for (Group group : st.getGroupList()) {
            if (group.getDeleteDate() == null) {
                group.setModuleList(getActiveModuleList(group.getGroupId()));
                activeGroupList.add(group);
            }
        }
        return activeGroupList;
    }

    public List<Module> getActiveModuleList(Long groupId) {
        Group g = groupDao.find(groupId);
        List<Module> activeModeleList = new ArrayList<>();
        for (Module module : g.getModuleList()) {
            if (module.getDeleteDate() == null) {
                activeModeleList.add(module);
            }
        }
        return activeModeleList;
    }

}
