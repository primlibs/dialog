/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import entities.Group;
import entities.Module;
import entities.PersonalCabinet;
import java.util.Date;
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
public class ModuleService extends PrimService {
    
    @Autowired
    private ModuleDao moduleDao;
    
    @Autowired
    private PersonalCabinetDao personalCabinetDao;
    
    @Autowired
    private GroupDao groupDao;
    
    public void deletModule(Long moduleId) {
        Module modul = moduleDao.find(moduleId);
        if (moduleId != null) {
            Date date = new Date();
            modul.setDeleteDate(date);
            moduleDao.update(modul);
        } else {
            addError("Модуль не найден по: " + moduleId);
        }
        
    }
    
    public Module showModule(Long moduleId) {
           
        if (moduleId != null) {
            Module modul = moduleDao.find(moduleId);
            return modul;
        } else {
            addError("Модуль не найден по: " + moduleId);
            return null;
        }
    }
    
    public void addBodyText(Long moduleId,
            String bodyText) {
        
        Module module = moduleDao.find(moduleId);
        
        if (moduleId != null) {
            module.setBodyText(bodyText);
            if (validate(module)) {
                moduleDao.update(module);
            }
            
        } else {
            addError("Ошибка модуль не найден по id " + moduleId);
        }
        
    }

    // вывести модуль в окно оператора
    public Module showModule(Long moduleId, Long cabinetId) {
        if (moduleId != null) {
            Module mo = moduleDao.getShowModule(moduleId, cabinetId);
            return mo;
        } else {            
            addError("Модуль не найден по " + moduleId);
        }
        return null;
    }
    
}
