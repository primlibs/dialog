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
        String hql = "from Module as m where m.moduleId= :moduleId and m.cabinet.personalCabinetId= :cabinet and m.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("moduleId", moduleId);
        query.setParameter("cabinet", cabinetId);
        return (Module) query.uniqueResult();
    }
    
    public List<Module> getHistory(Long eventId){
        String hql = "select mec.module from ModuleEventClient as mec where mec.event.eventId= :eventId order by mec.insertDate asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("eventId", eventId);
        return query.list();
    }

 
}
