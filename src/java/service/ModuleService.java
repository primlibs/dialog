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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class ModuleService extends PrimService {
    
    @Autowired
    private ModuleDao moduleDao;
    
    @Autowired
    private PersonalCabinetDao personalCabinetDao;
    
    @Autowired
    private GroupDao groupDao;
    
    private final String deleteMarkBegin = " (редакция ";
    private final String dmb = deleteMarkBegin;
    private final String deleteMarkEnd = ")";
    private final String dme = deleteMarkEnd;
    
    public void deleteModule(Long moduleId,Long pkId) {
        if(moduleId!=null){
            Module module = moduleDao.find(moduleId);
            if (module != null) {
                if(!module.getModuleEventClientList().isEmpty()){
                    List<String>names= getExistingModuleNames(module.getGroup().getId(),pkId);
                    Date date = new Date();
                    String name = module.getModuleName();
                    boolean valid = false;
                    String newName=name+dmb+0+dme;
                    for(int i=0;valid==true;i++){
                        newName = name+dmb+i+dme;
                        valid = !names.contains(newName);
                    }
                    module.setModuleName(newName);
                    module.setDeleteDate(date);
                    if(validate(module)){
                        moduleDao.update(module);
                    }
                }else{
                    moduleDao.delete(module);
                }
            } else {
                addError("Модуль не найден по ИД: " + moduleId);
            }
        }else{
            addError("Ид модуля не передан");
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
    
    public Long addBodyText(Long moduleId, String bodyText,Long pkId) {
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
                    deleteModule(moduleId,pkId);
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
    
    public void updateName(String newName,Long moduleId,Long pkId){
        if(newName!=null){
            if(!newName.equals("")){
                Module m = moduleDao.getActiveModule(moduleId, pkId);
                if(m!=null){
                    if(isUniqueName(newName, m.getGroup().getId(), pkId)){
                        if(m.getModuleEventClientList().isEmpty()){
                            m.setModuleName(newName);
                            if(validate(m)){
                                moduleDao.update(m);
                            }
                        }else{
                            Module newm = new Module();
                            newm.setBodyText(m.getBodyText());
                            newm.setCabinet(m.getCabinet());
                            newm.setGroup(m.getGroup());
                            newm.setStrategy(m.getStrategy());
                            newm.setModuleName(newName);
                            if(validate(newm)){
                                deleteModule(moduleId, pkId);
                                moduleDao.save(newm);
                            }
                        }
                    }else{
                        addError("модуль с таким наименованием уже существует в этой группе.");
                    }
                }else{
                    addError("Модуль не найден!");
                }
            }else{
                addError("Нужно ввести новое наименование модуля.");
            }
        }else{
            addError("Наименование модуля не передано.");
        }
    }
    
    public boolean isUniqueName(String newName,Long groupId,Long pkId){
        List<Module> ms = moduleDao.getActiveModules(pkId, groupId);
        String err="";
        for(Module m:ms){
            //err+=m.getModuleName()+"; ";
            if(newName.equalsIgnoreCase(m.getModuleName())){
                return false;
            }
        }
        //addError(err);
        return true;
    }
    
    private List<String> getExistingModuleNames(Long groupId,Long pkId){
        List<Module> mods=moduleDao.getModules(groupId,pkId);
        List<String> names = new ArrayList();
        for(Module m:mods){
            names.add(m.getModuleName());
        }
        return names;
    }
    
    public HashMap<Long,Module>getAllModulesMap(Long pkId){
        List<Module>modules=moduleDao.getAllModules(pkId);
        HashMap<Long,Module>res=new HashMap();
        for(Module m:modules){
            res.put(m.getId(), m);
        }
        return res;
    }
    
}
