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
import java.util.Date;
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
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист не назначеных ссылок ECL  
    public List<Event> getUnassignedEvent(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is null and ev.cabinet.pkId= :cabinetId order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    public Event getEvent(Client cl, PersonalCabinet pk, Campaign campaign) {
        //   String hql = "from Event as ev where ev.event.campaignId= :event and ev.cabinet.pkId= :cabinet and ev.client.clientId= :client";
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
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user is not null group by ev.user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    // получить лист назначенных ссылок ECL 
    public List<Event> getAssignedEvent(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId= :cabinetId order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ ОБРАБОТАНЫХ
    public List<Event> getEventListNotProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinetId and ev.finalComment is null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventLisSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.successDate is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventLisNotSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.drain is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId  ОБРАБОТАНЫХ
    public List<Event> getEventListProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.finalComment is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , не обработанных
    public List<Event> getAssignedEventNotProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId= :cabinet and ev.finalComment is null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL ,  обработанных
    public List<Event> getAssignedEventProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId= :cabinet and ev.finalComment is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , не успешно обработанных
    public List<Event> getAssignedEventNotSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId= :cabinet and ev.drain is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , успешно обработанных
    public List<Event> getAssignedEventSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId=:campaignId and ev.user is not null and ev.cabinet.pkId=:cabinet and ev.successDate is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ ОБРАБОТАНЫХ по userId
    public List<Event> getNotProcessedEventsByUserIdAndCampaignId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.finalComment is null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getSuccessEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.successDate is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getFailedEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.drain is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId  ОБРАБОТАНЫХ по userId
    public List<Event> getProcessedEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.finalComment is not null order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //Ссылка ECL по campaignId , по userId
    public List<Event> getEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId order by ev.eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //клиенты назначение юзерам не обработанные
    public List<Object[]> getAssignedNotProcessedClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinetId and ev.user is not null and ev.finalComment is null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные
    public List<Object[]> getAssignedProcessedClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user is not null and ev.finalComment is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Успешно
    public List<Object[]> getAssignedProcessedSuccessClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user is not null and ev.successDate is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Не успешно
    public List<Object[]> getAssignedProcessedFailedClientsByUserId(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user is not null and ev.drain is not null group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //Оператору: список ссылок по kичному кабинету и userId
    public List<Object[]> getCampaignByCabinetAndUserId(Long cabinetId, Long userId) {
        String hql = "select ev.campaign,count(ev.eventId) from Event as ev where ev.cabinet.pkId= :cabinetId and ev.user.userId= :userId and ev.finalComment is null group by ev.campaign order by ev.campaign.creationDate";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("userId", userId);
        //List<Event> ev = query.list();
        List<Object[]> clist = query.list();
        return clist;
    }
    
    public List<Campaign> getCampaignsByUserAndCabinet(Long cabinetId, Long userId){
        String hql = "select ev.campaign from Event ev where ev.cabinet.pkId= :cabinetId and ev.user.userId= :userId group by ev.campaign order by ev.campaign.creationDate";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("userId", userId);
        return query.list();
    }
    
     // ДОПИСАТЬ ЗАПРОС лист Ссылкок event по campaignId НЕ ОБРАБОТАНЫХ по userId
    public List<Event> getEventListByUserByCampaign(Long campaignId, Long cabinetId, Long userId) {
        Date dt = new Date();
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.finalComment is null order by ev.postponedDate";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        // query.setParameter("date", dt);
        List<Event> ev = query.list();
        return ev;
    }
    
    public List<Object[]> getUsersAndSuccessfulFailedPerformancesForReport(Date dateCampaignFrom,Date dateCampaignTo,Long pkId){
        String hql = "select ev.user,count(ev.successDate),count(ev.drain),count(ev.eventId) from Event ev where ev.cabinet.pkId=:pkId and ev.campaign.creationDate between :dateCampaignFrom and :dateCampaignTo group by ev.user order by ev.user.surname";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("dateCampaignFrom", dateCampaignFrom);
        query.setParameter("dateCampaignTo", dateCampaignTo);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Event> getPostponedEvents(Date dateFrom,Date dateTo,Long pkId){
        String hql = "from Event ev where ev.cabinet.pkId=:pkId and ev.postponedDate between :dateFrom and :dateTo order by ev.postponedDate asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
}
