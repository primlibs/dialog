/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Client;
import entities.Campaign;
import entities.ClientTagLink;
import entities.Event;
import entities.PersonalCabinet;
import entities.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class ClientDao extends Dao<Client> {
    
    @Autowired
    private TagDao tagDao;

    @Override
    public Class getSupportedClass() {
        return Client.class;
    }

    public Client getClientByUniqueIdInLk(String uid, Long cabinetId) {

        String hql = "from Client as cu where cu.uniqueId= :uniqueId and cu.cabinet.pkId= :cabinetId ";
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
        //   String hql = "from EventClientLink as ev where ev.event.eventId= :event and ev.cabinet.pkId= :cabinet and ev.client.clientId= :client";
        // String hql = "select ev.client  from EventClientLink as ev where ev.event= :event and ev.cabinet= :cabinet";
        String hql = "select Client  from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinetId";
        Query query = getCurrentSession().createQuery(hql);
        query.setEntity("campaignId", campaign.getId());
        query.setEntity("cabinetId", pk.getId());
        List<Client> clist = query.list();
        return clist;
    }

    public List<Client> getNotAssignedClientsByCampaign(Long pkId, Long campaignId) {
        //   String hql = "from EventClientLink as ev where ev.event.eventId= :event and ev.cabinet.pkId= :cabinet and ev.client.clientId= :client";
        String hql = "select ev.client from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :pkId and ev.user is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        List<Client> clist = query.list();
        return clist;
    }

    public List<Client> getCabinetClients(Long pkId) {
        String hql = "from Client c where c.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        List<Client> clist = query.list();
        return clist;
    }

    public List<Event> getFinishedEventsByClient(Long clientId) {
        String hql = "from Event e where e.client.clientId=:clientId and e.finalComment is not null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("clientId", clientId);
        List<Event> elist = query.list();
        return elist;
    }

    public List<Event> getUnfinishedEventsByClient(Long clientId) {
        String hql = "from Event e where e.client.clientId=:clientId and e.finalComment is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("clientId", clientId);
        List<Event> elist = query.list();
        return elist;
    }

    public List<Client> getClientsBySearchRequest(Long pkId, String uid, String adress, String nameCompany, String name, String phone,Long[] tagIds) {
        List<Client> result = new ArrayList();
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("pkId", pkId);
        String hql = "from Client с where с.cabinet.pkId=:pkId";
        if (uid != null && !uid.equals("")) {
            hql += " and с.uniqueId=:uid";
            paramMap.put("uid", uid);
        }
        if (adress != null && !adress.equals("")) {
            hql += " and lower(с.address) like :address";
            paramMap.put("address", adress.toLowerCase());
        }
        if (nameCompany != null && !nameCompany.equals("")) {
            hql += " and lower(с.nameCompany) like :nameCompany";
            paramMap.put("nameCompany", nameCompany.toLowerCase());
        }
        if (name != null && !name.equals("")) {
            hql += " and (lower(с.nameSecretary)=:name or lower(с.nameLpr) like :name)";
            paramMap.put("name", name.toLowerCase());
        }
        if (phone != null && !phone.equals("")) {
            hql += " and (с.phoneSecretary=:phone or с.phoneLpr=:phone)";
            paramMap.put("phone", phone);
        }
        Query query = getCurrentSession().createQuery(hql);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        
        //TO DO: remove this pron
        result=query.list();
        if(tagIds!=null&&tagIds.length>0){
        ArrayList<Long> tagIdList = new ArrayList();
            List<Client> resWithTags = new ArrayList();
            for(Long tagId:tagIds){
                Tag tag = tagDao.find(tagId);
                tagIdList.add(tag.getTagId());
            }
            for(Client client:result){
                List<Long>subList=new ArrayList();
                for(ClientTagLink ctl:client.getTagLinks()){
                    if(tagIdList.contains(ctl.getTag().getTagId())){
                        subList.add(ctl.getTag().getTagId());
                    }
                }
                if(subList.size()==tagIdList.size()){
                    resWithTags.add(client);
                }
            }
            return resWithTags;
        }
        return result;
    }

}
