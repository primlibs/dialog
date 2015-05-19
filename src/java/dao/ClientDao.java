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
import java.util.List;
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

        String hql = "from Client as cu where cu.uniqueId= :uniqueId and cu.cabinet.personalCabinetId= :cabinet ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("uniqueId", uid);
        query.setParameter("cabinet", cabinetId);
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
          String hql = "select ev.client  from Event as ev where ev.campaign= :campaign and ev.cabinet= :cabinet";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaign", campaign);
        query.setParameter("cabinet", pk);
        List<Client> clist = query.list();
        return clist;
    }

    public List<Client> getNotAssignedClientsByCampaign(PersonalCabinet pk, Campaign campaign) {
        //   String hql = "from EventClientLink as ev where ev.event.eventId= :event and ev.cabinet.personalCabinetId= :cabinet and ev.client.clientId= :client";
        String hql = "select ev.client from Event as ev where ev.campaign= :campaign and ev.cabinet= :cabinet and ev.user is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaign", campaign);
        query.setParameter("cabinet", pk);
        List<Client> clist = query.list();
        return clist;
    }
}
