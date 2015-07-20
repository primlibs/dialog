/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.FailReason;
import entities.Strategy;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class FailReasonDao extends Dao<FailReason> {

    @Override
    public Class getSupportedClass() {
        return FailReason.class;
    }

    public List<FailReason> getActiveFailReasons(Long strategyId) {
        //   String hql = "from Event as ev where ev.event.campaignId= :event and ev.cabinet.pkId= :cabinet and ev.client.clientId= :client";
        String hql = "from FailReason fr where fr.strategy.strategyId=:strategyId and fr.dateDelete is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategyId", strategyId);
        List<FailReason> clist = query.list();
        return clist;

    }
    
    public List<FailReason> getAllFailReasons(Long strategyId,Long pkId){
        String hql = "from FailReason fr where fr.strategy.strategyId=:strategyId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategyId", strategyId);
        List<FailReason> clist = query.list();
        return clist;
    }

    public List<Object[]> getDataForFailReasonReport(Long campaignId, Long pkId) {
        String sql = "select fail_reason_id,count(event_id) from event where personal_cabinet_id=:pkId and campaign_id=:campaignId group by fail_reason_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        return query.list();
    }

}
