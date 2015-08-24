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
import entities.Client;
import entities.Event;
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
    
    @Autowired
    private AdminService adminService;

    public void saveStrategy(String strategyName, Long cabinetId,Boolean in) {
        if(adminService.tarifIsNotExpired(cabinetId)){
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
                if(in==true){
                    strategy.setIsin("in");
                }
                if (validate(strategy)) {
                    strategyDao.save(strategy);
                }
                if (getErrors().isEmpty()) {
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
        }else{
            addError("Не удалось добваить сценарий в связи с ограничениями тарифа");
        }
    }

    public List<Strategy> getActiveStrategyList(Long cabinetId) {
        return strategyDao.getActiveStrategies(cabinetId);
    }

    public Strategy findStrategy(Long strategyId) {
        return strategyDao.find(strategyId);

    }

    public void deleteStrategy(Long strategyId,Long pkId) {
        Strategy strategy = strategyDao.find(strategyId);
        Date date = new Date();
        if (strategyId != null) {
            List<Group> groupList = strategy.getGroupList();
            if (groupList != null) {
                for (Group group : groupList) {
                    groupService.deleteGroup(group.getId(),pkId);
                }
            }
            strategy.setDeleteDate(date);
            strategyDao.update(strategy);
        } else {
            addError("Сценарий не найден по ИД: " + strategyId);
        }
    }

    public void renameStrategy(Long strategyId, String name) {
        Strategy str = strategyDao.find(strategyId);
        if(!name.equals("")){
            Boolean exists = false;
            for (Strategy strat : str.getCabinet().getInActiveStrategyList()) {
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

    public Strategy getStrategy(Long strategyId,Long lkId) {
        Strategy str = strategyDao.find(strategyId);
        if(str!=null){
            if(!str.getCabinet().getId().equals(lkId)){
                str=null;
                addError("Сценарий не принадлежит кабинету");
            }
        }
        return str;
    }
    
    /*public void updateField(String field, Long clientId, Long eventId, String newVal) {
        //boolean performed=false;
        Client client = clientDao.find(clientId);
        Event ev = null;
        boolean inEvent = false;
        if (eventId != null) {
            ev = eventDao.find(eventId);
        }
        if (client != null) {
            switch (field) {
                case "nameCompany":
                    client.setNameCompany(newVal);
                    break;
                case "address":
                    client.setAddress(newVal);
                    break;
                case "nameSecretary":
                    client.setNameSecretary(newVal);
                    break;
                case "nameLpr":
                    client.setNameLpr(newVal);
                    break;
                case "phoneSecretary":
                    client.setPhoneSecretary(newVal);
                    break;
                case "phoneLpr":
                    client.setPhoneLpr(newVal);
                    break;
                case "comment":
                    if(ev!=null){
                        ev.setComment(newVal);
                    }
                    inEvent = true;
                    break;
                default:
                    client = null;
                    break;
            }
        } else {
            addError("Клиент с ИД:" + clientId + " не найден");
        }
        if (inEvent) {
            if (ev != null) {
                if (validate(ev)) {
                    eventDao.update(ev);
                }
            } else {
                addError("Эвент не найден");
            }
        } else {
            if (client != null) {
                if (validate(client)) {
                    clientDao.update(client);
                }
            } else {
                addError("Клиент не найден");
            }
        }
    }*/
    
}
