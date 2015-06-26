/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.PersonalCabinet;
import entities.User;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class UserDao extends Dao<User> {

    @Override
    public Class getSupportedClass() {
        return User.class; 
    }

     public User getUserByLogin(String login) {
        String queryString = "from User U where U.email = :email";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("email", login);
        
        return (User) query.uniqueResult();
    }
     
   public User getUserByHash(String hash){
        String queryString = "from User U where U.recoverHash = :recoverHash";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("recoverHash", hash);       
       
       return (User) query.uniqueResult();
   }
   
   //Принадлежит Юзер к ЛК?
       public User getUserBelongsPk(PersonalCabinet pk, Long userId) {
        //   String hql = "from EventClientLink as ecl where ecl.event.eventId= :event and ecl.cabinet.pkId= :cabinet and ecl.client.clientId= :client";
        String hql = "select ecl.user from CabinetUser as ecl where ecl.user.userId= :userId and ecl.cabinet= :cabinet";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setEntity("cabinet", pk);
      
    return (User) query.uniqueResult();
    }
       
       public List<User> getParticipatedUsers(Long campaignId,Long pkId){
        String hql = "select distinct ev.user from Event ev where ev.cabinet.pkId=:pkId and ev.campaign.campaignId=:campaignId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("campaignId", campaignId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<User> getParticipatedUsers(Long pkId){
        String hql = "select distinct ev.user from Event ev where ev.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<User> getMakingCallsUsers(Long pkId){
        String hql = "select cu.user from CabinetUser cu where cu.cabinet.pkId=:pkId and cu.makesCalls=1 and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    
       
}
