/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDao;
import dao.ModuleDao;
import entities.Group;
import entities.Module;
import java.util.List;
import javax.transaction.Transactional;
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

        moduleList.removeAll(moduleList);
        groupDao.delete(group);
    }

}
