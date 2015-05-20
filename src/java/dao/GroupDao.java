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
    
     //лист групп по персональному кабинету и strategyId
    public List<Group> getGroupListByStrategyId(Long strategyId, Long cabinetId) {
        String hql = "from Group gr where gr.strategy.strategyId= :strategyId and gr.cabinet.personalCabinetId= :cabinetId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategyId", strategyId);
        query.setParameter("cabinetId", cabinetId);
        List<Group> ev = query.list();
        return ev;
    }
}
