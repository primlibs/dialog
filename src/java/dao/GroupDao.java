/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Event;
import entities.Group;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class GroupDao extends Dao<Group>{

    @Override
    public Class getSupportedClass() {
       return Group.class;
    }
    
    public List<Group> getActiveGroups(Long pkId,Long strategyId){
        String hql = "from Group where strategy.strategyId=:strategyId and cabinet.pkId=:pkId and deleteDate is null order by groupName asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategyId", strategyId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
}
