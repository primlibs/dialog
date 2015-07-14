/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.FailReasonDao;
import dao.StrategyDao;
import entities.FailReason;
import entities.Strategy;
import java.util.Date;
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
public class FailReasonService extends PrimService {

    @Autowired
    private FailReasonDao failReasonDao;

    @Autowired
    private StrategyDao strategyDao;

    public List<FailReason> getActiveFailReasonsByStrategy(Long strategyId) {
        List<FailReason> res = failReasonDao.getActiveFailReasons(strategyId);
        return res;
    }

    public void saveFailReason(String reason, Long strategyId) {
        //TO DO проверка удаленных и восстановление мб
        Strategy str = strategyDao.find(strategyId);
        List<FailReason> frs = getActiveFailReasonsByStrategy(strategyId);

        boolean existName = false;
        for (FailReason fr : frs) {
            existName = fr.getName().equals(reason);
            if (existName == true) {
                addError("Такая причина уже существует");
                break;
            }
        }

        if (reason != null && existName == false) {
            FailReason fr = new FailReason();
            fr.setName(reason);
            fr.setStrategy(str);
            if (validate(fr)) {
                failReasonDao.save(fr);
            }
        }
    }

    public void delete(Long failReasonId) {
            FailReason fr = failReasonDao.find(failReasonId);
            Date dt = new Date();
            fr.setDateDelete(dt);
            if (validate(fr)) {
                failReasonDao.update(fr);
            }
    }
    
    public FailReason getFailReason(Long failResonId){
        return failReasonDao.find(failResonId);
    }
    
}
