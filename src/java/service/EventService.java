/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.EventDao;
import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.Event;
import entities.PersonalCabinet;
import entities.Strategy;
import java.util.Date;
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
public class EventService extends PrimService {

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private EventDao eventDao;

    public String numericName(Long cabinetId) {
        int n = 0;
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            List<Event> eventList = pk.getEventList();
            if (eventList == null) {
                return "0";
            }
            if (eventList != null) {
                for (int i = 0; i <= eventList.size(); i++) {
                    return i + 1+"оп оп";
                }
            }
        } else {
            addError("не найден по " + cabinetId);
        }
        return "";

    }

    public void eventAdd(String name, Long strategyId, Date insertDate, Date endDate, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
       
       
    
    }

}
