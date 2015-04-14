/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.PersonalCabinet;
import entities.Strategy;
import java.util.ArrayList;
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
public class StrategyService extends PrimService {

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    public void saveStrategy(String strategyName, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        
        if (strategyName != null) {
            Strategy strategy = new Strategy();
            strategy.setStrategyName(strategyName);
            strategy.setCabinet(pk);
            if (validate(strategy)) {
                strategyDao.save(strategy);
            }
        } else {
            addError("несохранилось так как имя равно нулю");
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
    
}
