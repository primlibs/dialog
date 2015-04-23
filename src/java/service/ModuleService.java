/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ModuleDao;
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
    private ModuleDao moduleDao;

    public void deletModule(Long moduleId) {
        Module modul = moduleDao.find(moduleId);
        if (moduleId != null) {
            moduleDao.delete(modul);
        } else {
            addError("Модуль не найден по: " + moduleId);
        }

    }

    public String showModule(Long moduleId) {

        Module modul = moduleDao.find(moduleId);
        if (moduleId != null) {
          String name =  modul.getModuleName();
            return name;
        } else {
            addError("Модуль не найден по: " + moduleId);
            return "Модуль не найден по: "+ moduleId ;
        }
        
        
    }

}
