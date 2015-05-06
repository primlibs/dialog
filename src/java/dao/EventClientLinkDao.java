/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.EventClientLink;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class EventClientLinkDao extends Dao<EventClientLink>  {

    @Override
    public Class getSupportedClass() {
      return EventClientLink.class;
    }
     public List<EventClientLink> getEventClientLinkListByEventId(Long eventId) {
        String hql = "from EventClientLink as ecl where ecl.eventClientLinkId= :eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        return query.list();

    }
}
