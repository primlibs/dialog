/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import entities.Module;
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
    
    public void deleteModule(Long moduleId) {
        Module module = moduleDao.find(moduleId);
        if (module != null) {
            if(!module.getModuleEventClientList().isEmpty()){
                Date date = new Date();
                module.setDeleteDate(date);
                moduleDao.update(module);
            }else{
                moduleDao.delete(module);
            }
        } else {
            addError("Модуль не найден по ИД: " + moduleId);
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
    
    public Long addBodyText(Long moduleId,
            String bodyText) {
        //TO DO check for links if null - delete
        Module module = moduleDao.find(moduleId);
        
        if (module != null) {
            if(!module.getModuleEventClientList().isEmpty()){
                Module nm=new Module();
                nm.setBodyText(bodyText);
                nm.setCabinet(module.getCabinet());
                nm.setGroup(module.getGroup());
                nm.setStrategy(module.getStrategy());
                nm.setModuleName(module.getModuleName());


                /*module.setBodyText(bodyText);
                if (validate(module)) {
                    moduleDao.update(module);
                }*/
                if(validate(nm)){
                    deleteModule(moduleId);
                    moduleDao.save(nm);
                    return nm.getId();
                }
            }else{
                module.setBodyText(bodyText);
                if (validate(module)) {
                    moduleDao.update(module);
                    //return moduleId;
                }
            }
            
        } else {
            addError("Ошибка модуль не найден по id " + moduleId);
        }
        return moduleId;
        
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
