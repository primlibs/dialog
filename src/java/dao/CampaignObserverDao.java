/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.CampaignObserver;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class CampaignObserverDao extends Dao<CampaignObserver> {

    @Override
    public Class getSupportedClass() {
        return CampaignObserver.class;
    }


    public List<CampaignObserver> getByUser(Long userId, Long pkId) {
        String hql = "from CampaignObserver co where co.user.userId=:userId and co.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    
    public List<CampaignObserver> getByCampaign(Long campaignId,Long pkId) {
        String hql = "from CampaignObserver co where co.campaign.campaignId=:campaignId and co.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<CampaignObserver> getByUsrAngCampaign(Long campaignId,Long pkId,Long userId) {
        String hql = "from CampaignObserver co where co.campaign.campaignId=:campaignId and co.cabinet.pkId=:pkId and co.user.userId=:userId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        query.setParameter("userId", userId);
        return query.list();
    }

    
}
