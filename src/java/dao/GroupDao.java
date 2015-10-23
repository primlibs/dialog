/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Group;
import entities.InCall;
import entities.Module;
import entities.ModuleEventClient;
import java.util.List;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class GroupDao extends Dao<Group>{

    @Autowired
    ModuleDao moduleDao;
    
    @Autowired
    ModuleEventClientDao moduleeventClientDao;
    

    
    
    @Override
    public Class getSupportedClass() {
       return Group.class;
    }
    
    public List<Group> getActiveGroups(Long strategyId,Long pkId){
        String hql = "from Group where strategy.strategyId=:strategyId and cabinet.pkId=:pkId and deleteDate is null order by position asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("strategyId", strategyId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public Group getActiveGroup(Long groupId,Long pkId){
        String hql = "from Group where groupId=:groupId and cabinet.pkId=:pkId and deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        query.setParameter("groupId", groupId);
        Group res =(Group)query.uniqueResult();
        return res;
    }
    
    public Long getMaxGroupPosition(Long strategyId,Long pkId){
        String hql = "select max(position) from Group where cabinet.pkId=:pkId and strategy.strategyId=:strategyId and deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        query.setParameter("strategyId", strategyId);
        Long res =(Long)query.uniqueResult();
        return res;
    }
    
    public void delete(Group group) {
        List<ModuleEventClient> moduleEVCLList = group.getModuleEventClientList();
        for (ModuleEventClient mecl : moduleEVCLList) {
             moduleeventClientDao.delete(mecl);
        }
        
        List<Module> moduleList = group.getModuleList();
        for (Module ml : moduleList) {
             moduleDao.delete(ml);
        }
        getCurrentSession().delete(group);
    }
    
}
