/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Drain;
import entities.Strategy;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class DrainDao extends Dao<Drain>{

    @Override
    public Class getSupportedClass() {
        return Drain.class;
    }
    
     public List<Drain> getDrainListActive (Strategy strategy) {
        //   String hql = "from Event as ev where ev.event.campaignId= :event and ev.cabinet.personalCabinetId= :cabinet and ev.client.clientId= :client";
        String hql = "from Drain as dr where dr.strategy= :strategy and dr.dateDelete is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategy", strategy);
                List<Drain> clist = query.list();
                 return clist;
      
    }
}
