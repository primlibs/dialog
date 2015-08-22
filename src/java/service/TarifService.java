/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.TarifDao;
import entities.Tarif;
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
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TarifService extends PrimService {
    
    @Autowired
    TarifDao tarifDao;
    
    public List<Tarif> getAllTarifs() {
        return tarifDao.getAll();
    }
    
    public void create(String name,Long price,Long dayLength,Long userCount,Long campaignCount,Long clientCount){
        if(isUniqueName(name)){
            Tarif t = new Tarif();
            t.setName(name);
            t.setPrice(price);
            t.setClientCount(clientCount);
            t.setUserCount(userCount);
            t.setDayLength(dayLength);
            t.setCampaignCount(campaignCount);
            if(validate(t)){
                tarifDao.save(t);
            }
        }else{
            addError("Такой тариф уже существует, выберите другое название");
        }
    }
    
    public boolean isUniqueName(String name){
        Tarif t = tarifDao.getByName(name);
        if(t==null){
            return true;
        }else{
            return false;
        }
    }
    
    public void delete(Long tarifId){
        if(tarifId!=null){
            Tarif t = tarifDao.find(tarifId);
            tarifDao.delete(t);
        }else{
            addError("Ид тарифа не передан");
        }
    }
}
