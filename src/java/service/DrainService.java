/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DrainDao;
import dao.StrategyDao;
import entities.Drain;
import entities.Strategy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;

/**
 *
 * @author Ra
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DrainService extends PrimService {

    @Autowired
    private DrainDao drainDao;

    @Autowired
    private StrategyDao strategyDao;

    public List<Drain> getDrainActiveListByStrategy(Strategy strategy) {
        List<Drain> drainList = drainDao.getDrainListActive(strategy);
        return drainList;

    }

    public void saveDrain(String drainName, Long strategyId) {

        Strategy str = strategyDao.find(strategyId);
        List<Drain> drList = str.getDrainList();

        boolean existName = false;
        for (Drain drain : drList) {
            existName = drain.getName().equals(drainName);
            if (existName == true) {
                addError("Имя занято");
                break;
            }
        }

        if (drainName != null & existName == false) {
            Drain drain = new Drain();
            drain.setName(drainName);
            if (validate(drain)) {
                drainDao.save(drain);
            } else {
                addError("модуль отказа не сохранился");
            }
        }
    }
}
