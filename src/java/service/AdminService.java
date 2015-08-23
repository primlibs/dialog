/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PersonalCabinetDao;
import dao.TarifDao;
import entities.PersonalCabinet;
import entities.Tarif;
import entities.User;
import java.util.Calendar;
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
 * @author bezdatiuzer
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdminService extends PrimService {

    @Autowired
    PersonalCabinetDao pkDao;

    @Autowired
    TarifDao tarifDao;

    public List<PersonalCabinet> getPkList() {
        return pkDao.getAll();
    }

    public PersonalCabinet getPk(Long pkId) {
        return pkDao.find(pkId);
    }

    public void setTarif(Long tarifId, Long pkId) {
        if (tarifId != null && pkId != null) {
            Tarif t = tarifDao.find(tarifId);
            PersonalCabinet pk = pkDao.find(pkId);
            if (t != null && pk != null) {
                User u = authManager.getCurrentUser();
                Date endDate = null;
                if (t.getDayLength() != null) {
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, t.getDayLength().intValue());
                    endDate = c.getTime();
                }
                pk.setTarif(t);
                pk.setBeginDate(new Date());
                pk.setEndDate(endDate);
                pk.setUserSet(u);
                if (validate(pk)) {
                    pkDao.update(pk);
                }
            } else {
                if (t == null) {
                    addError("Тариф с ид " + tarifId + " не был найден");
                }
                if (pk == null) {
                    addError("Личный кабинет с ид " + pkId + " не был найден");
                }
            }
        } else {
            if (tarifId == null) {
                addError("Ид тарифа не передан");
            }
            if (pkId == null) {
                addError("Ид личного кабинета не передан");
            }
        }
    }

    public void setEndDate(Long pkId, Date newDate) {
        if (pkId != null) {
            PersonalCabinet pk = pkDao.find(pkId);
            if (pk != null) {
                pk.setEndDate(newDate);
                if(validate(pk)){
                    pkDao.update(pk);
                }
            } else {
                addError("Личный кабинет с ид " + pkId + " не был найден");
            }
        } else {
            addError("Ид личного кабинета не передан");
        }
    }

}
