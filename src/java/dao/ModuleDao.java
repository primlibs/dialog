/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Event;
import entities.Module;
import entities.PersonalCabinet;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class ModuleDao extends Dao<Module> {

    @Override
    public Class getSupportedClass() {
        return Module.class;
    }

    //получить модуль по moduleId 
    public Module getShowModule(Long moduleId, Long cabinetId) {
        String hql = "from Module as m where m.moduleId= :moduleId and m.cabinet.pkId= :cabinetId and m.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("moduleId", moduleId);
        query.setParameter("cabinetId", cabinetId);
        return (Module) query.uniqueResult();
    }
    
    public List<Module> getHistory(Long eventId){
        String hql = "select mec.module from ModuleEventClient as mec where mec.event.eventId= :eventId order by mec.insertDate asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        return query.list();
    }
    
    public List<Module> getActiveModules(Long pkId,Long groupId){
        String hql = "from Module where group.groupId=:groupId and cabinet.pkId=:pkId and deleteDate is null order by moduleName asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("groupId", groupId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public Module getActiveModule(Long moduleId,Long pkId){
        String hql = "from Module where moduleId=:moduleId and cabinet.pkId=:pkId and deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        query.setParameter("moduleId", moduleId);
        Module res =(Module)query.uniqueResult();
        return res;
    }
    
    public List<Module> getModules(Long groupId,Long pkId){
        String hql = "from Module where group.groupId=:groupId and cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("groupId", groupId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Module> getAllModules(Long pkId){
        String hql = "from Module where cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
}
