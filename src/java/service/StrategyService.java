/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DrainDao;
import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.Drain;
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
    private DrainDao drainDao;

    @Autowired
    private GroupService groupService;

    public void saveStrategy(String strategyName, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<Strategy> strategyList = strategyList(cabinetId);

        boolean existName = false;
        for (Strategy stretagy : strategyList) {
            existName = stretagy.getStrategyName().equals(strategyName);
            if (existName == true) {
                break;
            }
        }

        if (strategyName != null & existName == false) {
            Strategy strategy = new Strategy();
            strategy.setStrategyName(strategyName);
            strategy.setCabinet(pk);
            if (validate(strategy)) {
                strategyDao.save(strategy);
            } else {
                addError("Стратегия не сохранилась");
            }
            if (getError().isEmpty()) {
                ArrayList<String> drainList = new ArrayList<>();
                drainList.add(0, "Нет потребности");
                drainList.add(1, "Нет средств");
                //drainList.add(2, "Не интересно");
                for (String str :drainList ) {
                    Drain drain = new Drain();
                    drain.setStrategy(strategy);
                    drain.setName(str);
                    if (validate(drain)) {
                        drainDao.save(drain);
                    }else{
                        addError("Список сливов не сохранился");
                    }
                }
            }
        } else {
            addError("Cтратегия с названием " + strategyName + " уже существует");
        }
    }

    public List<Strategy> strategyList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return pk.getStrategyList();
        } else {
            addError("Кабинет не найден по ид " + cabinetId);
        }
        return new ArrayList();
    }

    public List<Strategy> getActiveStrategyList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            List<Strategy> listStrategy = pk.getStrategyList();
            List<Strategy> activeStrategyList = new ArrayList<>();
            for (Strategy strategy : listStrategy) {
                if (strategy.getDeleteDate() == null) {
                    activeStrategyList.add(strategy);
                }
            }
            return activeStrategyList;

        } else {
            addError("Кабинет не найден по ид " + cabinetId);
        }
        return new ArrayList();
    }

    /*public List<Group> getGroupList(Long strategyId) {
     Strategy stg = strategyDao.find(strategyId);
     if (stg != null) {
     return stg.getActiveGroupList();
     } else {
     addError("Стратегия не найдена по ид " + strategyId);
     }
     return new ArrayList();
     }*/
    public List<Module> moduleList(Long groupId) {
        Group gr = groupDao.find(groupId);
        if (gr != null) {
            return gr.getActiveModuleList();
        } else {
            addError("Группа не найден по ид " + groupId);
        }
        return new ArrayList();
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

    public void saveModule(Long groupId,
            String moduleName,
            Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Group gp = groupDao.find(groupId);

        List<Module> moduleList = moduleList(groupId);
        List<String> nameList = new ArrayList<>();

        for (Module modul : moduleList) {
            nameList.add(modul.getModuleName());
        }

        if (!nameList.contains(moduleName) & moduleName != null) {
            Module ml = new Module();
            ml.setCabinet(pk);
            ml.setGroup(gp);
            ml.setModuleName(moduleName);
            if (validate(ml)) {
                moduleDao.save(ml);
            }
        } else {
            addError("Такой модуль уже есть");
        }

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

    public void reanameStrategy(Long strategyId, String name) {
        Strategy str = strategyDao.find(strategyId);

        Boolean exists = false;
        for (Strategy strat : str.getCabinet().getActiveStrategyList()) {
            if (strat.getStrategyName().equalsIgnoreCase(name)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            str.setStrategyName(name);
            updateStrategy(str);
        } else {
            addError("Cтратегия с названием " + name + " уже существует");
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
