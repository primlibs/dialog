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

    public List<Client> getClientByEvent(PersonalCabinet pk, Campaign event) {
        //   String hql = "from EventClientLink as ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.client.clientId= :client";
      // String hql = "select ecl.client  from EventClientLink as ecl where ecl.event= :event and ecl.cabinet= :cabinet";
          String hql = "select ecl.client  from EventClientLink as ecl where ecl.event= :event and ecl.cabinet= :cabinet";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", event);
        query.setParameter("cabinet", pk);
        List<Client> clist = query.list();
        return clist;
    }

    public List<Client> getClientByEventNotAssigned(PersonalCabinet pk, Campaign event) {
        //   String hql = "from EventClientLink as ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.client.clientId= :client";
        String hql = "select ecl.client from EventClientLink as ecl where ecl.event= :event and ecl.cabinet= :cabinet and ecl.user is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", event);
        query.setParameter("cabinet", pk);
        List<Client> clist = query.list();
        return clist;
    }
}
