/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Client;
import entities.Campaign;
import entities.Event;
import entities.PersonalCabinet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class ClientDao extends Dao<Client> {

    @Override
    public Class getSupportedClass() {
        return Client.class;
    }

    public Client getClientByUniqueIdInLk(String uid, Long cabinetId) {

        String hql = "from Client as cu where cu.uniqueId= :uniqueId and cu.cabinet.personalCabinetId= :cabinetId ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("uniqueId", uid);
        query.setParameter("cabinetId", cabinetId);
        List<Client> clist = query.list();
        if (clist.isEmpty()) {
            return null;
        } else {
            return clist.get(0);
        }
    }

    public List<Client> getClientsByCampaign(PersonalCabinet pk, Campaign campaign) {
        //   String hql = "from EventClientLink as ev where ev.event.eventId= :event and ev.cabinet.personalCabinetId= :cabinet and ev.client.clientId= :client";
      // String hql = "select ev.client  from EventClientLink as ev where ev.event= :event and ev.cabinet= :cabinet";
          String hql = "select Client  from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinetId";
        Query query = getCurrentSession().createQuery(hql);
        query.setEntity("campaignId", campaign.getId());
        query.setEntity("cabinetId", pk.getId());
        List<Client> clist = query.list();
        return clist;
    }

    public List<Client> getNotAssignedClientsByCampaign(Long pkId, Long campaignId) {
        //   String hql = "from EventClientLink as ev where ev.event.eventId= :event and ev.cabinet.personalCabinetId= :cabinet and ev.client.clientId= :client";
        String hql = "select ev.client from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :pkId and ev.user is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        List<Client> clist = query.list();
        return clist;
    }
    
    public List<Client> getCabinetClients(Long pkId){
        String hql = "from Client c where c.cabinet.personalCabinetId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        List<Client> clist = query.list();
        return clist;
    }
    
    public List<Event> getFinishedEventsByClient(Long clientId){
        String hql = "from Event e where e.client.clientId=:clientId and e.status is not null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("clientId", clientId);
        List<Event> elist = query.list();
        return elist;
    }
    
    public List<Event> getUnfinishedEventsByClient(Long clientId){
        String hql = "from Event e where e.client.clientId=:clientId and e.status is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("clientId", clientId);
        List<Event> elist = query.list();
        return elist;
    }
    
    public List<Client> getClientsBySearchRequest(Long pkId,String uid,String adress,String nameCompany,String name,Long phone){
            HashMap<String,Object> paramMap = new HashMap();
            paramMap.put("pkId",pkId);
            String hql = "from Client с where с.cabinet.personalCabinetId=:pkId";
            //Boolean conditionBefore = false;
            if(uid!=null&&!uid.equals("")){
                hql+=" and с.uniqueId=:uid";
                paramMap.put("uid",uid);
                //conditionBefore=true;
            }
            if(adress!=null&&!adress.equals("")){
                //if(conditionBefore){
                    hql+=" and с.address=:address";
                /*}else{
                    hql+=" where Client.address=:address";
                }*/
                paramMap.put("address",adress);
                //conditionBefore=true;
            }
            if(nameCompany!=null&&!nameCompany.equals("")){
                //if(conditionBefore){
                    hql+=" and с.nameCompany=:nameCompany";
                /*}else{
                    hql+=" where Client.nameCompany=:nameCompany";
                }*/
                paramMap.put("nameCompany",nameCompany);
                //conditionBefore=true;
            }
            if(name!=null&&!name.equals("")){
                //if(conditionBefore){
                    hql+=" and (с.nameSecretary=:name or с.nameLpr=:name)";
                /*}else{
                    hql+=" where (Client.nameSecretary=:name or Client.nameLpr=:name)";
                }*/
                paramMap.put("name",name);
                //conditionBefore=true;
            }
            if(phone!=null){
                //if(conditionBefore){
                    hql+=" and (с.phoneSecretary=:phone or с.phoneLpr=:phone)";
                /*}else{
                    hql+=" where (Client.phoneSecretary=:phone or Client.phoneLpr=:phone)";
                }*/
                paramMap.put("phone",phone);
                //conditionBefore=true;
            }
            Query query = getCurrentSession().createQuery(hql);
            for(Map.Entry<String,Object> entry:paramMap.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
        return query.list();
    }
    
}
