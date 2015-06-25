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
    
    public List<Object> getDataForfailReport(Long campaignId,Long pkId){
        String hql="select mec.module from ModuleEventClient mec where mec.event.status=:failed and mec.event.campaign.campaignId=:campaignId and mec.cabinet.pkId=:pkId";
        
        Query query = getCurrentSession().createQuery(hql);
        return query.list();
    }
    
}
