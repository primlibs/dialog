/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Campaign;
import entities.Event;
import entities.EventComment;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import support.DateAdapter;

/**
 *
 * @author Юрий
 */
@Repository
public class EventCommentDao extends Dao<EventComment> {

    @Override
    public Class getSupportedClass() {
        return EventComment.class;
    }

    public List<Object[]> getUserIdPostponesCount(Campaign campaign, Date dateFrom, Date dateTo, Long pkId) {
        HashMap<String, Object> paramMap = new HashMap();
        //
        String sql = "select user_id,count(*) from (select * from event_comment where type=:postponed and campaign_id=:campaignId and personal_cabinet_id=:pkId";
        if (dateFrom != null) {
            sql += " and insert_date>=:dateFrom";
            paramMap.put("dateFrom", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateFrom)));
        }
        if (dateTo != null) {
            sql += " and insert_date<=:dateTo";
            paramMap.put("dateTo", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateTo)));
        }
        sql += " group by event_id,DATE_FORMAT(insert_date,'%Y-%m-%d'),user_id) supsel group by user_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("campaignId", campaign.getCampaignId());
        query.setParameter("pkId", pkId);
        query.setParameter("postponed", EventComment.POSTPONE);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.list();
    }

    public List<Object[]> getUserIdSuccessfulCount(Campaign campaign, Date dateFrom, Date dateTo, Long pkId) {
        HashMap<String, Object> paramMap = new HashMap();
        String sql = "select user_id,count(*) from event_comment where type=:successful and campaign_id=:campaignId and personal_cabinet_id=:pkId";
        if (dateFrom != null) {
            sql += " and insert_date>=:dateFrom";
            paramMap.put("dateFrom", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateFrom)));
        }
        if (dateTo != null) {
            sql += " and insert_date<=:dateTo";
            paramMap.put("dateTo", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateTo)));
        }
        sql += " group by user_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("campaignId", campaign.getCampaignId());
        query.setParameter("pkId", pkId);
        query.setParameter("successful", EventComment.SUCCESSFUL);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.list();
    }

    public List<Object[]> getUserIdFailedCount(Campaign campaign, Date dateFrom, Date dateTo, Long pkId) {
        HashMap<String, Object> paramMap = new HashMap();
        String sql = "select user_id,count(*) from event_comment where type=:failed and campaign_id=:campaignId and personal_cabinet_id=:pkId";
        if (dateFrom != null) {
            sql += " and insert_date>=:dateFrom";
            paramMap.put("dateFrom", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateFrom)));
        }
        if (dateTo != null) {
            sql += " and insert_date<=:dateTo";
            paramMap.put("dateTo", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateTo)));
        }
        sql += " group by user_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("campaignId", campaign.getCampaignId());
        query.setParameter("pkId", pkId);
        query.setParameter("failed", EventComment.FAILED);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.list();
    }

    public List<Event> getEventsForWorkReport(Integer status, Date dateFrom, Date dateTo, Long userId, Long campaignId, Long pkId) {
        HashMap<String, Object> paramMap = new HashMap();
        String hql = "select distinct ec.event from EventComment ec where ec.campaignId=:campaignId and ec.insertDate>=:dateFrom and ec.insertDate<=:dateTo and ec.cabinet.pkId=:pkId";
        if (userId != null) {
            hql += " and ec.author.userId=:userId";
            paramMap.put("userId", userId);
        }
        if (status != null) {
            hql += " and ec.type=:status";
            paramMap.put("status", status);
        } else {
            hql += " and (ec.type=4 or ec.type=5 or ec.type=1)";
        }
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        query.setParameter("dateFrom", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateFrom)));
        query.setParameter("dateTo", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateTo)));
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.list();
    }
    /*public List<Event> getEventsForWorkReport(Integer status, Date dateFrom, Date dateTo, Long userId, Long campaignId, Long pkId) {
     HashMap<String, Object> paramMap = new HashMap();
     String sql = "select * from event_comment where campaign_id=:campaignId and personal_cabinet_id=:pkId";
     if (userId != null) {
     sql += " and user_id=:userId";
     paramMap.put("userId", userId);
     }
     if (status != null) {
     sql += " and type=:status";
     paramMap.put("status", status);
     } else {
     sql += " and (type=4 or type=5 or type=1)";
     }
     sql += " group by event_id,DATE_FORMAT(insert_date,'%Y-%m-%d')";
     Query query = getCurrentSession().createQuery(sql);
     query.setParameter("campaignId", campaignId);
     query.setParameter("pkId", pkId);
     query.setParameter("dateFrom", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateFrom)));
     query.setParameter("dateTo", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(dateTo)));
     for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
     query.setParameter(entry.getKey(), entry.getValue());
     }
     return query.list();
     }*/
}
