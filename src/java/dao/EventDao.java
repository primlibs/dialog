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
public class EventDao extends Dao<Event> {

    @Override
    public Class getSupportedClass() {
        return Event.class;
    }

    //Ссылка ECL по campaignId
    public List<Event> getEventListByCampaignId(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист не назначеных ссылок ECL  
    public List<Event> getUnassignedEvent(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is null and ev.cabinet.personalCabinetId= :cabinetId order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    public Event getEvent(Client cl, PersonalCabinet pk, Campaign campaign) {
        //   String hql = "from Event as ev where ev.event.campaignId= :event and ev.cabinet.personalCabinetId= :cabinet and ev.client.clientId= :client";
        String hql = "from Event as ev where ev.campaign= :campaign and ev.cabinet= :cabinet and ev.client= :client";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaign", campaign);
        query.setParameter("cabinet", pk);
        query.setParameter("client", cl);
        List<Event> clist = query.list();
        if (clist.isEmpty()) {
            return null;
        } else {
            return clist.get(0);
        }
    }

    public List<Object[]> getUserAssignedClient(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.client.clientId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    // получить лист назначенных ссылок ECL 
    public List<Event> getAssignedEvent(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.personalCabinetId= :cabinetId order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ ОБРАБОТАНЫХ
    public List<Event> getEventListNotProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinetId and ev.status is null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventLisSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.status='success' order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventLisNotSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.status='fails' order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId  ОБРАБОТАНЫХ
    public List<Event> getEventListProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.status is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , не обработанных
    public List<Event> getAssignedEventNotProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.personalCabinetId= :cabinet and ev.status is null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL ,  обработанных
    public List<Event> getAssignedEventProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.personalCabinetId= :cabinet and ev.status is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , не успешно обработанных
    public List<Event> getAssignedEventNotSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.personalCabinetId= :cabinet and ev.status='fails' order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , успешно обработанных
    public List<Event> getAssignedEventSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId=:campaignId and ev.user is not null and ev.cabinet.personalCabinetId=:cabinet and ev.status='success' order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ ОБРАБОТАНЫХ по userId
    public List<Event> getNotProcessedEventsByUserIdAndCampaignId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user.userId= :userId and ev.status is null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getSuccessEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user.userId= :userId and ev.status='success' order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getFailedEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user.userId= :userId and ev.status='fails' order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId  ОБРАБОТАНЫХ по userId
    public List<Event> getProcessedEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user.userId= :userId and ev.status is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //Ссылка ECL по campaignId , по userId
    public List<Event> getEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user.userId= :userId order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //клиенты назначение юзерам не обработанные
    public List<Object[]> getAssignedNotProcessedClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.client.clientId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinetId and ev.user is not null and ev.status is null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные
    public List<Object[]> getAssignedProcessedClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.client.clientId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user is not null and ev.status is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Успешно
    public List<Object[]> getAssignedProcessedSuccessClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.client.clientId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user is not null and ev.status='success' group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Не успешно
    public List<Object[]> getAssignedProcessedFailedClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.client.clientId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.personalCabinetId= :cabinet and ev.user is not null and ev.status='fails' group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //Оператору: список ссылок по kичному кабинету и userId
    public List<Object[]> getCampaidnByCabinetAndUserId(Long cabinetId, Long userId) {
        String hql = "select ev.campaign,count(ev.eventId) from Event as ev where ev.cabinet.personalCabinetId= :cabinetId and ev.user.userId= :userId group by ev.campaign";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("userId", userId);
        //List<Event> ev = query.list();
        List<Object[]> clist = query.list();
        return clist;
    }
}
