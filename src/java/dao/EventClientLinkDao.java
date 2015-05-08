/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Client;
import entities.Event;
import entities.EventClientLink;
import entities.PersonalCabinet;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class EventClientLinkDao extends Dao<EventClientLink> {

    @Override
    public Class getSupportedClass() {
        return EventClientLink.class;
    }

    public List<EventClientLink> getEventClientLinkListByEventId(Long eventId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        List<EventClientLink> ecl = query.list();
        return ecl;

    }

    // получить лист ЕвенКлиентов не назначеных
    public List<EventClientLink> getUnassignedEventClientLink(Long eventId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    public EventClientLink getEventClientLink(Client cl, PersonalCabinet pk, Event event) {
       

        //   String hql = "from EventClientLink as ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.client.clientId= :client";
        String hql = "from EventClientLink as ecl where ecl.event= :event and ecl.cabinet= :cabinet and ecl.client= :client";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", event);
        query.setParameter("cabinet", pk);
        query.setParameter("client", cl);
         List<EventClientLink> clist = query.list();
        if (clist.isEmpty()) {
            return null;
        } else {
            return clist.get(0);
        }
    }
}
