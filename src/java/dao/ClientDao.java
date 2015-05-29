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

    public List<Client> getClientsBySearchRequest(Long pkId, String uid, String adress, String nameCompany, String name, Long phone) {
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("pkId", pkId);
        String hql = "from Client с where с.cabinet.pkId=:pkId";
        if (uid != null && !uid.equals("")) {
            hql += " and с.uniqueId=:uid";
            paramMap.put("uid", uid);
        }
        if (adress != null && !adress.equals("")) {
            hql += " and с.address=:address";
            paramMap.put("address", adress);
        }
        if (nameCompany != null && !nameCompany.equals("")) {
            hql += " and с.nameCompany=:nameCompany";
            paramMap.put("nameCompany", nameCompany);
        }
        if (name != null && !name.equals("")) {
            hql += " and (с.nameSecretary=:name or с.nameLpr=:name)";
            paramMap.put("name", name);
        }
        if (phone != null) {
            hql += " and (с.phoneSecretary=:phone or с.phoneLpr=:phone)";
            paramMap.put("phone", phone);
        }
        Query query = getCurrentSession().createQuery(hql);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.list();
    }

}
