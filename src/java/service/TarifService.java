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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
import support.DateAdapter;
import support.StringAdapter;

/**
 *
 * @author bezdatiuzer
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TarifService extends PrimService {

    @Autowired
    TarifDao tarifDao;

    @Autowired
    PersonalCabinetDao personalCabinetDao;

    public List<Tarif> getAllTarifs() {
        return tarifDao.getAll();
    }

    public void create(String name, Long price, Long dayLength, Long userCount, Long campaignCount, Long clientCount) {
        if (isUniqueName(name)) {
            Tarif t = new Tarif();
            t.setName(name);
            t.setPrice(price);
            t.setClientCount(clientCount);
            t.setUserCount(userCount);
            t.setDayLength(dayLength);
            t.setCampaignCount(campaignCount);
            if (validate(t)) {
                tarifDao.save(t);
            }
        } else {
            addError("Такой тариф уже существует, выберите другое название");
        }
    }

    public boolean isUniqueName(String name) {
        Tarif t = tarifDao.getByName(name);
        if (t == null) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(Long tarifId) {
        if (tarifId != null) {
            Tarif t = tarifDao.find(tarifId);
            tarifDao.delete(t);
        } else {
            addError("Ид тарифа не передан");
        }
    }

    public String getTarifInfo(Long pkId) {
        String tarifInfo = "Нет назначенного тарифа";
        PersonalCabinet pk = personalCabinetDao.find(pkId);
        Tarif t = pk.getTarif();
        if (t != null) {
            String date = "не установлена";
            if (pk.getEndDate() != null) {
                date = DateAdapter.formatByDate(pk.getEndDate(), DateAdapter.SMALL_FORMAT);
            }
            String u = StringAdapter.getString(t.getUserCount());
            if (u.equals("")) {
                u = "не ограничено";
            }
            String camps = StringAdapter.getString(t.getCampaignCount());
            if (camps.equals("")) {
                camps = "не ограничено";
            }
            String cls = StringAdapter.getString(t.getClientCount());
            if (cls.equals("")) {
                cls = "не ограничено";
            }
            tarifInfo = "Тариф: " + t.getName() + ", дата окончания:" + date + ", пользователей:" + u + ", кампаний:" + camps + ", клиентов:" + cls + ".";
        }
        return tarifInfo;
    }

}
