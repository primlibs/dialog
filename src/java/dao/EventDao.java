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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import support.DateAdapter;
import support.StringAdapter;

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
    
    public Event getEvent(Long eventId,Long pkId){
        String hql = "from Event ev where ev.cabinet.pkId=:pkId and ev.eventId=:eventId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("pkId", pkId);
        return (Event) query.uniqueResult();
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
    
    public List<Event> getActiveEvents(Long userId,Long pkId){
        String hql = "from Event ev where ev.user.userId=:userId and ev.cabinet.pkId=:pkId and ev.status!=4 and ev.status!=3";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public Integer getAssignedEventsCount(Long campaignId, Long cabinetId){
        String hql = "select count(ev.eventId) from Event ev where ev.campaign.campaignId=:campaignId and ev.cabinet.pkId=:pkId and ev.user is not null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", cabinetId);
        Long count = (Long)query.uniqueResult();
        return count.intValue();
    }

    public List<Object[]> getUserAssignedClient(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId=:campaignId and ev.cabinet.pkId=:pkId and ev.user is not null group by ev.user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", cabinetId);
        List<Object[]> clist = query.list();
        return clist;
    }

    //Ссылка ECL по campaignId
    public List<Event> getEventListByCampaignId(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист не назначеных ссылок ECL  
    public List<Event> getUnassignedEvent(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is null and ev.cabinet.pkId= :cabinetId order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL 
    public List<Event> getAssignedEvents(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinetId and ev.status=:assigned order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("assigned", Event.ASSIGNED);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ ОБРАБОТАНЫХ
    public List<Event> getEventListNotProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinetId and ev.finalComment is null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventLisSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.status=:successful order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("successful", Event.SUCCESSFUL);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ УСПЕШНО ОБРАБОТАНЫХ
    public List<Event> getEventLisNotSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.status=:failed order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("failed", Event.FAILED);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId  ОБРАБОТАНЫХ
    public List<Event> getEventListProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.finalComment is not null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , не обработанных
    public List<Event> getAssignedNotClosedEvents(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId=:cabinet and ev.finalComment is null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }
    
    // получить лист назначенных ссылок ECL , не обработанных
    public List<Event> getAssignedNotClosedUserEvents(Long campaignId, Long userId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user.userId=:userId and ev.cabinet.pkId=:cabinetId and ev.status!=:failed and ev.status!=:succsesseful order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("userId", userId);
        query.setParameter("failed", Event.FAILED);
        query.setParameter("succsesseful", Event.SUCCESSFUL);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL ,  обработанных
    public List<Event> getAssignedEventProcessed(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId= :cabinet and ev.finalComment is not null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , не успешно обработанных
    public List<Event> getAssignedEventNotSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.user is not null and ev.cabinet.pkId= :cabinet and ev.failReason is not null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    // получить лист назначенных ссылок ECL , успешно обработанных
    public List<Event> getAssignedEventSuccess(Long campaignId, Long cabinetId) {
        String hql = "from Event as ev where ev.campaign.campaignId=:campaignId and ev.user is not null and ev.cabinet.pkId=:cabinet and ev.status=:succsesseful order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("succsesseful", Event.SUCCESSFUL);
        query.setParameter("cabinet", cabinetId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ ОБРАБОТАНЫХ по userId
    public List<Event> getNotProcessedEventsByUserIdAndCampaignId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.finalComment is null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getSuccessEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.status=:succsesseful order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("succsesseful", Event.SUCCESSFUL);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId НЕ УСПЕШНО ОБРАБОТАНЫХ по userId
    public List<Event> getFailedEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.status=:failed order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("succsesseful", Event.FAILED);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //лист Ссылкок ECL по campaignId  ОБРАБОТАНЫХ по userId
    public List<Event> getProcessedEventsByUserId(Long campaignId, Long cabinetId, Long userId) {
        String hql = "from Event as ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user.userId= :userId and ev.finalComment is not null order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("userId", userId);
        List<Event> ev = query.list();
        return ev;
    }

    //Ссылка ECL по campaignId , по userId
    public List<Event> getEventsByUserId(Long campaignId, Long userId, Long pkId) {
        String hql = "from Event ev where ev.campaign.campaignId=:campaignId and ev.cabinet.pkId=:pkId and ev.user.userId=:userId";// order by ev.client.nameCompany,ev.client.address";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        query.setParameter("userId", userId);
        return query.list();
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
    public List<Object[]> getUserIdListWithCountedAssignedProcessedSuccessClients(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinetId and ev.user is not null and ev.status=:successful group by ev.user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("successful", Event.SUCCESSFUL);
        List<Object[]> clist = query.list();
        return clist;
    }

    //клиенты назначение юзерам обработанные Не успешно
    public List<Object[]> getUserIdListWithCountedAssignedProcessedFailedClients(Long campaignId, Long cabinetId) {
        String hql = "select count(ev.eventId) , ev.user.userId  from Event ev where ev.campaign.campaignId= :campaignId and ev.cabinet.pkId= :cabinet and ev.user is not null and ev.status=:failed group by user.userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinet", cabinetId);
        query.setParameter("failed", Event.FAILED);
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
    
     // Лист для выбора эвента с выводом оператору
    public List<Event> getEventListByUserByCampaign(Long campaignId, Long cabinetId, Long userId) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);
        String hql = "from Event ev where ev.campaign.campaignId=:campaignId and ev.cabinet.pkId=:cabinetId and ev.user.userId=:userId and (ev.postponedDate<=:dt or ev.postponedDate is null) and (ev.status=1 or ev.status=2) order by ev.postponedDate desc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("cabinetId", cabinetId);
        query.setParameter("userId", userId);
        query.setParameter("dt", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(cal)));
        List<Event> ev = query.list();
        return ev;
    }
    
    public List<Object[]> getUsersAndSuccessfulFailedPerformancesForReport(Date dateCampaignFrom,Date dateCampaignTo,Long pkId){
        String hql = "select ev.user,sum(case when ev.status=:successful then 1 else 0 end),count(ev.failReason),count(ev.eventId) from Event ev where ev.cabinet.pkId=:pkId and ev.campaign.creationDate between :dateCampaignFrom and :dateCampaignTo group by ev.user order by ev.user.surname";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("dateCampaignFrom", dateCampaignFrom);
        query.setParameter("dateCampaignTo", dateCampaignTo);
        query.setParameter("successful", Event.SUCCESSFUL);
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
    
    public LinkedHashMap<Long,HashMap<String,String>> getFinishedAndUnassignedEventCountsInCampaignsAsMap(/*Date dateFrom,Date dateTo,boolean showClosed,*/Long pkId){
        String hql="select ev.campaign,sum(case when ev.user is not null then 1 else 0 end),sum(case when ev.finalComment is not null then 1 else 0 end) from Event ev where ev.cabinet.pkId=:pkId";// and ev.campaign.creationDate between :dateFrom and :dateTo";
        /*if(!showClosed){
            hql+=" and ev.campaign.endDate is null";
        }else{
            hql+=" and ev.campaign.endDate is not null";
        }*/
        hql+=" group by ev.campaign order by ev.campaign.creationDate,ev.campaign.status desc";
        Query query = getCurrentSession().createQuery(hql);
        /*query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);*/
        query.setParameter("pkId", pkId);
        LinkedHashMap<Long,HashMap<String,String>> res=new LinkedHashMap();
        List<Object[]> list = query.list();
        for(Object[] o:list){
            Campaign c = (Campaign)o[0];
            HashMap<String,String> infoMap = new HashMap();
            infoMap.put("unassignedCount",StringAdapter.getString(c.getEvents().size()-Integer.valueOf(StringAdapter.getString(o[1]))));
            infoMap.put("finishedCount",StringAdapter.getString(Integer.valueOf(StringAdapter.getString(o[2]))));
            res.put(c.getId(), infoMap);
        }
        return res;
    }
    
    // получить лист не обработанных эвентов юзера
    public List<Event> getNotProcessedUserEvents(Long userId, Long cabinetId) {
        String hql = "from Event as ev where ev.user.userId=:userId and ev.cabinet.pkId= :cabinetId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("cabinetId", cabinetId);
        return query.list();
    }  
    
    public void clearHistory(Long eventId,Long pkId){
        String hql = "delete from ModuleEventClient mec where mec.event.eventId=:eventId and mec.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("pkId", pkId);
    }
    
    public Integer countReplicas(Long eventId,Long pkId){
        String hql="select count(*) from ModuleEventClient where event.eventId=:eventId and cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        query.setParameter("pkId", pkId);
        Long res = (Long)query.uniqueResult();
        return res.intValue();
    }
    
    public List<Event> getPostponedEvents(Long userId,Long pkId){
        String hql="from Event ev where ev.user.userId=:userId and ev.cabinet.pkId=:pkId and ev.status=2";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Event> getPostponedEvents(Long campaignId,Long userId,Long pkId){
        String hql="from Event ev where ev.user.userId=:userId and ev.cabinet.pkId=:pkId and ev.status=2 and ev.campaign.campaignId=:campaignId order by ev.postponedDate desc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("userId", userId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Event> getFailedEvents(Long failReasonId,Long campaignId,Long pkId){
        HashMap<String, Object> paramMap = new HashMap();
        String hql="from Event ev where ev.cabinet.pkId=:pkId and ev.campaign.campaignId=:campaignId and ev.status=:failed";
        if(failReasonId!=null){
            hql+=" and ev.failReason.failReasonId=:failReasonId";
            paramMap.put("failReasonId", failReasonId);
        }
        hql+=" order by ev.setStatusDate";
        Query query = getCurrentSession().createQuery(hql);
        for(Map.Entry<String,Object>entry:paramMap.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        query.setParameter("failed", Event.FAILED);
        return query.list();
    }
    
    /*public List<Object> getUserAndAssignedAndSuccAndFailedByaDateAndCampaign(List<Long>campaignIds,Long pkId){
        HashMap<String,Object>paramMap=new HashMap();
        
        String hql = "select distinct(ev.user),sum(case when ev.status=:succsessful then 1 else 0 end),sum(case when ev.status=:failed then 1 else 0 end) from Event ev "
                + "where ev.cabinet.pkId=:pkId";
        if(campaignIds!=null&&!campaignIds.isEmpty()){
            paramMap.put("campaignIds",campaignIds);
            hql+=" and ev.campaignId in (:campaignIds)";
        }
        Query query = getCurrentSession().createQuery(hql);
        for(Map.Entry<String,Object> entry:paramMap.entrySet()){
            query.setParameterList(entry.getKey(), (List<Long>)entry.getValue());
        }
        query.setParameter("pkId", pkId);
        return query.list();
    }*/
    
}
