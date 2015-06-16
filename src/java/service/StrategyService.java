/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FailReasonDao;
import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.FailReason;
import entities.Group;
import entities.Module;
import entities.PersonalCabinet;
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
public class StrategyService extends PrimService {

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private FailReasonDao failReasonDao;

    @Autowired
    private GroupService groupService;

    public void saveStrategy(String strategyName, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<Strategy> strategyList = getActiveStrategyList(cabinetId);

        boolean existName = false;
        for (Strategy stretagy : strategyList) {
            existName = stretagy.getStrategyName().equalsIgnoreCase(strategyName);
            if (existName == true) {
                break;
            }
        }

        if (strategyName != null && existName == false) {
            Strategy strategy = new Strategy();
            strategy.setStrategyName(strategyName);
            strategy.setCabinet(pk);
            if (validate(strategy)) {
                strategyDao.save(strategy);
            }
            if (getError().isEmpty()) {
                ArrayList<String> failReasons = new ArrayList<>();
                failReasons.add(0, "Нет потребности");
                failReasons.add(1, "Нет средств");
                //failReasons.add(2, "Не интересно");
                for (String str :failReasons ) {
                    FailReason fr = new FailReason();
                    fr.setStrategy(strategy);
                    fr.setName(str);
                    if (validate(fr)) {
                        failReasonDao.save(fr);
                    }else{
                        addError("Список сливов не сохранился");
                    }
                }
            }
        } else {
            addError("Cтратегия с названием " + strategyName + " уже существует");
        }
    }

    public List<Strategy> getActiveStrategyList(Long cabinetId) {
        return strategyDao.getActiveStrategies(cabinetId);
    }
    
    public List<Module> getActiveModules(Long groupId,Long pkId) {
        return moduleDao.getActiveModules(pkId, groupId);
    }

    public void saveGroup(Long strategyId,
            String groupName,
            Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Strategy stg = strategyDao.find(strategyId);

        Boolean exists = false;
        for (Group group : stg.getActiveGroupList()) {
            if (group.getGroupName().equalsIgnoreCase(groupName)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            Group gr = new Group();
            gr.setCabinet(pk);
            gr.setStrategy(stg);
            gr.setGroupName(groupName);
            if (validate(gr)) {
                groupDao.save(gr);
            }
        } else {
            addError("Такая группа уже есть");
        }

    }

    public Strategy findStrategy(Long strategyId) {
        return strategyDao.find(strategyId);

    }

    public Group findGroup(Long groupId) {
        return groupDao.find(groupId);
    }

    public Long saveModule(Long groupId,
            String moduleName,
            Long cabinetId) {
        
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Group gp = groupDao.find(groupId);
        Long moduleId = null;
        List<Module> moduleList = getActiveModules(groupId,cabinetId);
        List<String> nameList = new ArrayList<>();

        for (Module modul : moduleList) {
            nameList.add(modul.getModuleName());
        }

        if (!nameList.contains(moduleName) & moduleName != null) {
            Module ml = new Module();
            ml.setStrategy(gp.getStrategy());
            ml.setCabinet(pk);
            ml.setGroup(gp);
            ml.setModuleName(moduleName);
            if (validate(ml)) {
                moduleDao.save(ml);
            }
            moduleId=ml.getId();
        } else {
            addError("Такой модуль уже есть");
        }
        return moduleId;
    }

    public void deleteStrategy(Long strategyId) {

        Strategy strategy = strategyDao.find(strategyId);

        Date date = new Date();
        if (strategyId != null) {
            List<Group> groupList = strategy.getGroupList();
            if (groupList != null) {
                for (Group group : groupList) {
                    groupService.deleteGroup(group);
                }
            }
            strategy.setDeleteDate(date);
            strategyDao.update(strategy);

        } else {
            addError("Стратегия не найдена по: " + strategyId);
        }

    }

    public void renameStrategy(Long strategyId, String name) {
        Strategy str = strategyDao.find(strategyId);
        if(!name.equals("")){
            Boolean exists = false;
            for (Strategy strat : str.getCabinet().getActiveStrategyList()) {
                if (strat.getStrategyName().equalsIgnoreCase(name)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                str.setStrategyName(name);
                if(validate(str)){
                    updateStrategy(str);
                }
            } else {
                addError("Cтратегия с названием " + name + " уже существует");
            }
        }
    }

    private void updateStrategy(Strategy strategy) {
        if (validate(strategy)) {
            strategyDao.update(strategy);
        }
    }

    public Strategy getStrategy(Long strategyId) {
        Strategy str = strategyDao.find(strategyId);
        return str;
    }
}
