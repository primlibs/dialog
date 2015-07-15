/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.Group;
import entities.Module;
import entities.PersonalCabinet;
import entities.Strategy;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    public void deleteGroup(Long groupId,Long pkId) {
        if (groupId != null) {
            Group group = groupDao.find(groupId);
            if (group != null) {
                Date date = new Date();

                for (Module module : group.getModuleList()) {
                    module.setDeleteDate(date);
                    moduleDao.update(module);
                }
                Strategy s = group.getStrategy();
                group.setDeleteDate(date);
                if(validate(group)){
                    groupDao.update(group);
                    updatePositionsAndGetAvailable(s.getId(),pkId);
                }
            } else {
                addError("Группа не найдена по ИД: " + groupId);
            }
        }else{
            addError("Ид группы не передан");
        }
    }

    // получить лист активных(не отмеченных как удаленные) групп
    /*public List<Group> getActiveGroupList(Long strategyId, Long pkId) {
        return groupDao.getActiveGroups(strategyId, pkId);
    }*/

    public LinkedHashMap<Group, List<Module>> getActiveGroupMap(Long strategyId, Long pkId) {
        LinkedHashMap<Group, List<Module>> result = new LinkedHashMap();
        for (Group g : groupDao.getActiveGroups(strategyId, pkId)) {
            result.put(g, moduleDao.getActiveModules(g.getId(), pkId));
        }
        return result;
    }
    
    public void changePosition(Long groupId,Long newPosition,Long pkId){
        Group group = groupDao.find(groupId);
        Long strategyId=group.getStrategy().getId();
        updatePositionsAndGetAvailable(strategyId,pkId);
        TreeMap<Long,Group>map = new TreeMap();
        List<Group>groups=groupDao.getActiveGroups(strategyId, pkId);
        Long oldPosition=group.getPosition();
        for(Group g:groups){
            Long GID=g.getId();
            if(GID.equals(groupId)){
                map.put(newPosition,g);
            }else{
                //перемещение модуля вверх
                if(oldPosition>newPosition){
                    if(g.getPosition()<newPosition||g.getPosition()>oldPosition){
                        map.put(g.getPosition(),g);
                    }else{
                        map.put(g.getPosition()+1,g);
                    }
                    //вниз
                }else{
                    if(g.getPosition()>newPosition||g.getPosition()<oldPosition){
                        map.put(g.getPosition(),g);
                    }else{
                        map.put(g.getPosition()-1,g);
                    }
                }
            }
        }
        for(Map.Entry<Long,Group>entry:map.entrySet()){
            Long pos = entry.getKey();
            Group g = entry.getValue();
            g.setPosition(pos);
            if(validate(g)){
                groupDao.update(g);
            }
        }
    }

    public void updateName(String newName, Long groupId, Long pkId) {
        if (newName != null) {
            if (!newName.equals("")) {
                Group g = groupDao.getActiveGroup(groupId, pkId);
                if (g != null) {
                    if (isUniqueName(newName, g.getStrategy().getId(), pkId)) {
                        g.setGroupName(newName);
                        if (validate(g)) {
                            groupDao.update(g);
                        }
                    } else {
                        addError("Группа с таким наименованием уже существует в этом сценарии.");
                    }
                } else {
                    addError("Группа не найдена!");
                }
            } else {
                addError("Нужно ввести новое наименование группы.");
            }
        } else {
            addError("Наименование группы не передано.");
        }
    }

    public boolean isUniqueName(String newName, Long strategyId, Long pkId) {
        List<Group> gs = groupDao.getActiveGroups(strategyId, pkId);
        for (Group g : gs) {
            if (newName.equalsIgnoreCase(g.getGroupName())) {
                return false;
            }
        }
        return true;
    }

    public Group findGroup(Long groupId) {
        return groupDao.find(groupId);
    }

    public void saveGroup(Long strategyId,
            String groupName,
            Long pkId) {

        Boolean exists = false;
        for (Group group : groupDao.getActiveGroups(strategyId, pkId)) {
            if (group.getGroupName().equalsIgnoreCase(groupName)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            PersonalCabinet pk = personalCabinetDao.find(pkId);
            Strategy stg = strategyDao.find(strategyId);
            Group gr = new Group();
            gr.setCabinet(pk);
            gr.setStrategy(stg);
            gr.setGroupName(groupName);
            Long pos = updatePositionsAndGetAvailable(strategyId, pkId);
            gr.setPosition(pos);
            if (validate(gr)) {
                groupDao.save(gr);
            }
        } else {
            addError("Такая группа уже есть");
        }
    }

    private Long updatePositionsAndGetAvailable(Long strategyId, Long pkId) {
        long i = 1;
        for (Group g : groupDao.getActiveGroups(strategyId, pkId)) {
            g.setPosition(i++);
            if (validate(g)) {
                groupDao.update(g);
            }
        }
        return i;
    }

}
