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

    //Ссылка ECL по eventId
    public List<EventClientLink> getEventClientLinkListByEventId(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    // получить лист не назначеных ссылок ECL  
    public List<EventClientLink> getUnassignedEventClientLink(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is null and ecl.cabinet.personalCabinetId= :cabinet order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
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

    public List<Object[]> getUserAssignedClient(Long eventId, Long cabinetId) {
        String hql = "select count(ecl.client.clientId) , ecl.user.userId  from EventClientLink ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.user is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    // получить лист назначенных ссылок ECL 
    public List<EventClientLink> getAssignedEventClientLink(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ ОБРАБОТАНЫХ
    public List<EventClientLink> getEventClientLinkListNotProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId УСПЕШНО ОБРАБОТАНЫХ
    public List<EventClientLink> getEventClientLinkLisSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status='success' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ УСПЕШНО ОБРАБОТАНЫХ
    public List<EventClientLink> getEventClientLinkLisNotSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status='fails' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId  ОБРАБОТАНЫХ
    public List<EventClientLink> getEventClientLinkListProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is not null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL , не обработанных
    public List<EventClientLink> getAssignedEventClientLinkNotProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL ,  обработанных
    public List<EventClientLink> getAssignedEventClientLinkProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is not null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL , не успешно обработанных
    public List<EventClientLink> getAssignedEventClientLinkNotSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet and ecl.status='fails' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL , успешно обработанных
    public List<EventClientLink> getAssignedEventClientLinkSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId=:eventId and ecl.user is not null and ecl.cabinet.personalCabinetId=:cabinet and ecl.status='success' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ ОБРАБОТАНЫХ по userId
    public List<EventClientLink> getUserIdByEventClientLinkListNotProcessed(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status is null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<EventClientLink> getUserIdByEventClientLinkLisSuccess(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status='success' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<EventClientLink> getUserIdByEventClientLinkLisNotSuccess(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status='fails' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId  ОБРАБОТАНЫХ по userId
    public List<EventClientLink> getUserIdByEventClientLinkListProcessed(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status is not null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //Ссылка ECL по eventId , по userId
    public List<EventClientLink> getUserIdByEventClientLinkList(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<EventClientLink> ecl = query.list();
        return ecl;
    }

    //клиенты назначение юзерам не обработанные
    public List<Object[]> getUserAssignedClientNotProcessed(Long eventId, Long cabinetId) {
        String hql = "select count(ecl.client.clientId) , ecl.user.userId  from EventClientLink ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.user is not null and ecl.status is null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные
    public List<Object[]> getUserAssignedClientProcessed(Long eventId, Long cabinetId) {
        String hql = "select count(ecl.client.clientId) , ecl.user.userId  from EventClientLink ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.user is not null and ecl.status is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Успешно
    public List<Object[]> getUserAssignedClientProcessedSuccess(Long eventId, Long cabinetId) {
        String hql = "select count(ecl.client.clientId) , ecl.user.userId  from EventClientLink ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.user is not null and ecl.status='success' group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Не успешно
    public List<Object[]> getUserAssignedClientProcessedFails(Long eventId, Long cabinetId) {
        String hql = "select count(ecl.client.clientId) , ecl.user.userId  from EventClientLink ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.user is not null and ecl.status='fails' group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }
}
