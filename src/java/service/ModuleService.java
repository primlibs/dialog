/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupsDao;
import dao.ModulesDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.Module;
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
public class ModuleService extends PrimService {

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    @Autowired
    private GroupsDao groupDao;

    @Autowired
    private ModulesDao moduleDao;
    
    
    public void deletModul(Long moduleId){
        Module modul = moduleDao.find(moduleId);
        moduleDao.delete(modul);
    }

}
