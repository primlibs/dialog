/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.ModuleEventClient;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class ModuleEventClientDao extends Dao<ModuleEventClient>   {

    @Override
    public Class getSupportedClass() {
       return ModuleEventClient.class;
    }
    
    public List<Object[]> getCountedFailedEventsModuleData(Long pkId){
        String hql="select mec.module.moduleId,mec.event.user.userId,count(mec.module) from ModuleEventClient mec inner join (select event,max(insertDate) mid from ModuleEventClient group by event) supsel where mec.event.status=:failed and mec.event=supsel.event and mec.insertDate=supsel.insertDate and mec.cabinet.pkId=:pkId group by mec.event.user,mec.module";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Object[]> getCountedAllEventsIncludingModulesData(Long pkId){
        String hql = "select mec.module.moduleId,mec.event.user.userId,count(distinct mec.event) from ModuleEventClient mec where mec.cabinet.pkId=:pkId group by mec.module,mec.event.user";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
}
