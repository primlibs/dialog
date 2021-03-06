/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Campaign;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class CampaignDao extends Dao<Campaign> {

    @Override
    public Class getSupportedClass() {
       return Campaign.class;
    }
    
    public List<Campaign> getAllCampaigns(Long pkId){
        String hql = "from Campaign where cabinet.pkId=:pkId order by status asc,creationDate desc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<String> getUniqs(Long campaignId,Long pkId){
        String hql = "select ev.client.uniqueId from Event ev where ev.cabinet.pkId=:pkId and ev.campaign.campaignId=:campaignId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    
    public Campaign getCampaign(Long campaignId,Long pkId){
        String hql = "from Campaign where cabinet.pkId=:pkId and campaignId=:campaignId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        return (Campaign) query.uniqueResult();
    }
    
}
