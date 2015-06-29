/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Event;
import entities.ModuleEventClient;
import java.util.Date;
import java.util.HashMap;
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
    
    /*public List<Object[]> getCountedFailedEventsModuleDataByUser(Long pkId){
        String hql="select mec.module.moduleId,mec.event.user.userId,count(mec.module) from ModuleEventClient mec inner join (select event,max(insertDate) mid from ModuleEventClient group by event) supsel where mec.event.status=:failed and mec.event=supsel.event and mec.insertDate=supsel.insertDate and mec.cabinet.pkId=:pkId group by mec.event.user,mec.module";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Object[]> getCountedAllEventsIncludingModulesDataByUser(Long pkId){
        String hql = "select mec.module.moduleId,mec.event.user.userId,count(distinct mec.event) from ModuleEventClient mec where mec.cabinet.pkId=:pkId group by mec.module,mec.event.user";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Object[]> getCountedFailedEventsModuleDataByAll(Long pkId){
        String hql="select mec.moduleId,ev.userId from ModuleEventClient mec inner join (select eventId,max(insertDate) mid from ModuleEventClient group by eventId) supsel left join Event ev on mec.eventId=ev.eventId where mec.eventId=supsel.eventId and supsel.mid=mec.insertDate and ev.status=4;";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Object[]> getCountedAllEventsIncludingModulesDataByAll(Long pkId){
        String hql="moduleId,count(distinct mec.eventId) from ModuleEventClient mec where mec.cabinet.pkId=:pkId mec.event.status=4;";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }*/
    
    
    public List<Object[]> getCountedFailedEventsModuleDataByUser(/*Long campaignId,Long strategyId,Date fromdate,Date toDate,*/Long pkId){
        HashMap<String,String>paramMap=new HashMap();
        String sql = "select mec.module_id,ev.user_id,count(mec.module_id) from module_event_client mec inner join (select event_id,max(insert_date) mid from module_event_client group by event_id) supsel left join event ev on mec.event_id=ev.event_id where mec.event_id=supsel.event_id and supsel.mid=mec.insert_date and ev.status=4 and mec.personal_cabinet_id=:pkId group by ev.user_id,mec.module_id";
        /*if(campaignId!=null){
            sql+=" and ";
        }*/
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Object[]> getCountedAllEventsIncludingModulesDataByUser(Long pkId){
        String sql = "select mec.module_id,ev.user_id,count(distinct mec.event_id) from module_event_client mec left join event ev on mec.event_id=ev.event_id where mec.personal_cabinet_id=:pkId and (ev.status=:successful or ev.status=:failed) group by mec.module_id,ev.user_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("pkId", pkId);
        query.setParameter("successful",Event.SUCCESSFUL);
        query.setParameter("failed",Event.FAILED);
        return query.list();
    }
    
    public List<Object[]> getCountedFailedEventsModuleDataByAll(Long pkId){
        String sql="select mec.module_id,count(distinct mec.event_id) from module_event_client mec inner join (select event_id,max(insert_date) mid from module_event_client group by event_id) supsel left join event ev on mec.event_id=ev.event_id where mec.event_id=supsel.event_id and supsel.mid=mec.insert_date and ev.status=4 and mec.personal_cabinet_id=:pkId group by mec.module_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<Object[]> getCountedAllEventsIncludingModulesDataByAll(Long pkId){
        String sql="select mec.module_id,count(distinct mec.event_id) from module_event_client mec left join event ev on mec.event_id=ev.event_id where mec.personal_cabinet_id=:pkId and (ev.status=:successful or ev.status=:failed) group by mec.module_id";
        Query query = getCurrentSession().createSQLQuery(sql);
        query.setParameter("pkId", pkId);
        query.setParameter("successful",Event.SUCCESSFUL);
        query.setParameter("failed",Event.FAILED);
        return query.list();
    }
    
}
