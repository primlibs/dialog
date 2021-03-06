/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.InCall;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import support.DateAdapter;

/**
 *
 * @author bezdatiuzer
 */
@Repository
public class InCallDao extends Dao<InCall> {

    @Override
    public Class getSupportedClass() {
        return InCall.class;
    }

    public List<Object[]> getReport(Long strategyId, Date from, Date to) {
        String hql = "select inc.module , count(inc.module) as cnt from InCall inc where inc.strategy.strategyId=:strategyId ";
        if (from != null) {
            hql += " and inc.addDate>:from ";
        }
        if (to != null) {
            hql += " and inc.addDate<=:to ";
        }
        hql += " group by inc.module order by cnt desc ";

        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategyId", strategyId);
        if (from != null) {
            query.setParameter("from", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(DateAdapter.getStartOfDate(from))));
        }
        if (to != null) {
            query.setParameter("to", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(DateAdapter.getEndOfDate(to))));
        }
        return query.list();
    }
    
    public List<InCall> getReportDetail(Long moduleId, Date from, Date to,Long userId) {
        String hql = "from InCall inc where inc.module.moduleId=:moduleId ";
        if (from != null) {
            hql += " and inc.addDate>:from ";
        }
        if (to != null) {
            hql += " and inc.addDate<=:to ";
        }
        if(userId!=null){
            hql += " and inc.user.userId=:userId ";
        }
        hql += " order by inc.addDate asc ";

        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("moduleId", moduleId);
        if (from != null) {
            query.setParameter("from", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(DateAdapter.getStartOfDate(from))));
        }
        if (to != null) {
            query.setParameter("to", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(DateAdapter.getEndOfDate(to))));
        }
        if(userId!=null){
            query.setParameter("userId", userId);
        }
        return query.list();
    }

    public List<InCall> getFromStrategy(Long stratId) {
        String hql = "from InCall inc where inc.strategy.strategyId=:stratId ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("stratId", stratId);
        return query.list();
    }
    
    public List<InCall> getFromPersonalCabinet(Long pkId) {
        String hql = "from InCall inc where inc.cabinet.pkId=:pkId ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    
    
}
