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
public class EventClientLinkDao extends Dao<Event> {

    @Override
    public Class getSupportedClass() {
        return Event.class;
    }

    //Ссылка ECL по eventId
    public List<Event> getEventClientLinkListByEventId(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    // получить лист не назначеных ссылок ECL  
    public List<Event> getUnassignedEventClientLink(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is null and ecl.cabinet.personalCabinetId= :cabinet order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    public Event getEventClientLink(Client cl, PersonalCabinet pk, Campaign event) {
        //   String hql = "from EventClientLink as ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.client.clientId= :client";
        String hql = "from EventClientLink as ecl where ecl.event= :event and ecl.cabinet= :cabinet and ecl.client= :client";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("event", event);
        query.setParameter("cabinet", pk);
        query.setParameter("client", cl);
        List<Event> clist = query.list();
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
    public List<Event> getAssignedEventClientLink(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ ОБРАБОТАНЫХ
    public List<Event> getEventClientLinkListNotProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventClientLinkLisSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status='success' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventClientLinkLisNotSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status='fails' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId  ОБРАБОТАНЫХ
    public List<Event> getEventClientLinkListProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is not null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL , не обработанных
    public List<Event> getAssignedEventClientLinkNotProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL ,  обработанных
    public List<Event> getAssignedEventClientLinkProcessed(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet and ecl.status is not null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL , не успешно обработанных
    public List<Event> getAssignedEventClientLinkNotSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.user is not null and ecl.cabinet.personalCabinetId= :cabinet and ecl.status='fails' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    // получить лист назначенных ссылок ECL , успешно обработанных
    public List<Event> getAssignedEventClientLinkSuccess(Long eventId, Long cabinetId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId=:eventId and ecl.user is not null and ecl.cabinet.personalCabinetId=:cabinet and ecl.status='success' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ ОБРАБОТАНЫХ по userId
    public List<Event> getUserIdByEventClientLinkListNotProcessed(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status is null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getUserIdByEventClientLinkLisSuccess(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status='success' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId НЕ УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getUserIdByEventClientLinkLisNotSuccess(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status='fails' order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //лист Ссылкок ECL по eventId  ОБРАБОТАНЫХ по userId
    public List<Event> getUserIdByEventClientLinkListProcessed(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId and ecl.status is not null order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ecl = query.list();
        return ecl;
    }

    //Ссылка ECL по eventId , по userId
    public List<Event> getUserIdByEventClientLinkList(Long eventId, Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.event.eventId= :eventId and ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ecl = query.list();
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

    //Оператору: список ссылок по kичному кабинету и userId
    public List<Event> getECLListByUserId(Long cabinetId, Long userId) {
        String hql = "from EventClientLink as ecl where ecl.cabinet.personalCabinetId= :cabinet and ecl.user.userId= :userId order by ecl.eventClientLinkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ecl = query.list();
        return ecl;
    }
}
