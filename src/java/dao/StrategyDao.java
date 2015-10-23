/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Campaign;
import entities.Event;
import entities.FailReason;
import entities.Group;
import entities.InCall;
import entities.Strategy;
import java.util.List;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class StrategyDao extends Dao<Strategy> {

    @Autowired
    CampaignDao camDao;

    @Autowired
    FailReasonDao flDao;

    @Autowired
    EventDao eventDao;

    @Autowired
    GroupDao groupDao;

    @Autowired
    InCallDao inCallDao;

    @Override
    public Class getSupportedClass() {
        return Strategy.class;
    }

    public List<Strategy> getActiveStrategies(Long pkId) {
        String hql = "from Strategy where cabinet.pkId=:pkId and deleteDate is null order by strategyName asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }

    public void delete(Strategy str) {
        List<Campaign> camList = str.getCampaigns();
        for (Campaign cam : camList) {
            List<Event> evlist = cam.getEvents();
            for (Event event : evlist) {
                eventDao.delete(event);
            }
            camDao.delete(cam);
        }
        List<FailReason> flrList = str.getFailReasons();
        for (FailReason flres : flrList) {
            flDao.delete(flres);
        }

        List<InCall> inCallList = inCallDao.getFromStrategy(str.getStrategyId());
        for (InCall inc : inCallList) {
            inCallDao.delete(inc);
        }

        List<Group> grlist = str.getGroupList();
        for (Group group : grlist) {
            groupDao.delete(group);
        }
        getCurrentSession().delete(str);
    }

}
