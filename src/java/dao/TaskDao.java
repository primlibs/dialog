/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Group;
import entities.Task;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import support.DateAdapter;

@Repository
public class TaskDao extends Dao<Task>{

    
    @Override
    public Class getSupportedClass() {
       return Task.class;
    }
    
    public List<Task> getActiveTask(Date from,Date to,Long pkId){
        String hql = "from Task where cabinet.pkId=:pkId and performDate > :dateFrom and performDate < :dateTo";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        query.setParameter("dateFrom", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(from)));
        query.setParameter("dateTo", DateAdapter.getDateFromString(DateAdapter.getDateInMysql(to)));
        return query.list();
    }
    
}
