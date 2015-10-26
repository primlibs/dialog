/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CabinetUserDao;
import dao.ClientDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import dao.TagDao;
import dao.TarifDao;
import entities.CabinetUser;
import entities.Campaign;
import entities.Client;
import entities.PersonalCabinet;
import entities.Strategy;
import entities.Tag;
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
    CabinetUserDao cuDao;

    @Autowired
    StrategyDao stratDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    TarifDao tarifDao;
    
    @Autowired
    ClientDao clientDao;

    public List<PersonalCabinet> getPkList() {
        return pkDao.getCabinetList();
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
                if (validate(pk)) {
                    pkDao.update(pk);
                }
            } else {
                addError("Личный кабинет с ид " + pkId + " не был найден");
            }
        } else {
            addError("Ид личного кабинета не передан");
        }
    }

    public boolean tarifIsNotExpired(Long pkId) {
        if (pkId != null) {
            PersonalCabinet pk = pkDao.find(pkId);
            if (pk != null) {
                Date endDate = pk.getEndDate();
                if (endDate == null || endDate.compareTo(new Date()) > 0) {
                    return true;
                }
            } else {
                addError("Личный кабинет с ид " + pkId + " не был найден");
            }
        } else {
            addError("Ид личного кабинета не передан");
        }
        return false;
    }

    public boolean mayAddUser(Long pkId) {
        if (tarifIsNotExpired(pkId)) {
            if (pkId != null) {
                PersonalCabinet pk = pkDao.find(pkId);
                if (pk != null) {
                    Tarif t = pk.getTarif();
                    if (t != null) {
                        Long uc = t.getUserCount();
                        List<CabinetUser> culist = pkDao.getActiveUserList(pkId);
                        if (uc == null || culist.size() < uc.intValue()) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    addError("Личный кабинет с ид " + pkId + " не был найден");
                }
            } else {
                addError("Ид личного кабинета не передан");
            }
        }
        return false;
    }

    public boolean mayAddCampaign(Long pkId) {
        if (tarifIsNotExpired(pkId)) {
            if (pkId != null) {
                PersonalCabinet pk = pkDao.find(pkId);
                if (pk != null) {
                    Tarif t = pk.getTarif();
                    if (t != null) {
                        Long uc = t.getCampaignCount();
                        List<Campaign> clist = pkDao.getCampaignList(pkId);
                        if (uc == null || clist.size() < uc.intValue()) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    addError("Личный кабинет с ид " + pkId + " не был найден");
                }
            } else {
                addError("Ид личного кабинета не передан");
            }
        }
        return false;
    }

    public boolean mayAddClient(Long pkId) {
        if (tarifIsNotExpired(pkId)) {
            if (pkId != null) {
                PersonalCabinet pk = pkDao.find(pkId);
                if (pk != null) {
                    Tarif t = pk.getTarif();
                    if (t != null) {
                        Long uc = t.getClientCount();
                        List<Client> clist = pkDao.getClientList(pkId);
                        if (uc == null || clist.size() < uc.intValue()) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    addError("Личный кабинет с ид " + pkId + " не был найден");
                }
            } else {
                addError("Ид личного кабинета не передан");
            }
        }
        return false;
    }

    public void delete(Long pkId) {
        PersonalCabinet pk = pkDao.find(pkId);

        if (pk != null) {
            List<Strategy> strList = pk.getStrategyList();
            for (Strategy str : strList) {
                stratDao.delete(str);
            }

            List<Tag> tglist = tagDao.getAllTags(pkId);
            for (Tag tg : tglist) {
                tagDao.delete(tg);
            }

            List<Client> cllist = pkDao.getClientList(pkId);
            for (Client cl : cllist) {
                clientDao.delete(cl);
            }
            
            
            List<CabinetUser> lku = pk.getCabinetUserList();
            for (CabinetUser cu : lku) {
                cuDao.delete(cu);
            }

            pkDao.delete(pk);
        }
        
    }

}
