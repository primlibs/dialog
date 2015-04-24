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

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    @Autowired
    private GroupDao groupDao;

    public void deletModule(Long moduleId) {
        Module modul = moduleDao.find(moduleId);
        if (moduleId != null) {
            moduleDao.delete(modul);
        } else {
            addError("Модуль не найден по: " + moduleId);
        }

    }

    public Module showModule(Long moduleId) {
        /*
         Module modul = moduleDao.find(moduleId);
         if (moduleId != null) {
         String name =  modul.getModuleName();
         return name;
         } else {
         addError("Модуль не найден по: " + moduleId);
         return "Модуль не найден по: "+ moduleId ;
         }
         */

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

}
