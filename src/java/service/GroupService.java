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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

     // получить лист активных(не отмеченных как удаленные) групп
    public List<Group> getActiveGroupList(Long strategyId) {
        Strategy st = strategyDao.find(strategyId);
        List<Group> activeGroupList = new ArrayList();
        for (Group group : st.getGroupList()) {
            if (group.getDeleteDate() == null) {
                activeGroupList.add(group);
            }
        }
        return activeGroupList;
    }

    public Map<Group, List<Module>> getActiveMap(Long strategyId) {
        Map<Group, List<Module>> result = new HashMap();
        Strategy st = strategyDao.find(strategyId);
        //    List<Group> activeGroupList = new ArrayList();
        for (Group group : st.getGroupList()) {

            if (group.getDeleteDate() == null) {
                Long groupId = group.getGroupId();
                Group g = groupDao.find(groupId);
                result.put(g, getActiveModuleList(groupId));
            }
        }
        return result;
    }

    // получить лист активных(не отмеченных как удаленные) модулей
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
    
    public void updateName(String newName,Long groupId,Long pkId){
        if(newName!=null){
            if(!newName.equals("")){
                Group g = groupDao.getActiveGroup(groupId, pkId);
                if(g!=null){
                    if(isUniqueName(newName, g.getStrategy().getId(), pkId)){
                        g.setGroupName(newName);
                        if(validate(g)){
                            groupDao.update(g);
                        }
                    }else{
                        addError("Группа с таким наименованием уже существует в этом сценарии.");
                    }
                }else{
                    addError("Группа не найдена!");
                }
            }else{
                addError("Нужно ввести новое наименование группы.");
            }
        }else{
            addError("Наименование группы не передано.");
        }
    }
    
    public boolean isUniqueName(String newName,Long StrategyId,Long pkId){
        List<Group> gs = groupDao.getActiveGroups(pkId, StrategyId);
        for(Group g:gs){
            if(newName.equalsIgnoreCase(g.getGroupName())){
                return false;
            }
        }
        return true;
    }
    

}
