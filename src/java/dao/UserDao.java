/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.PersonalCabinet;
import entities.User;
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
        //   String hql = "from EventClientLink as ecl where ecl.event.eventId= :event and ecl.cabinet.personalCabinetId= :cabinet and ecl.client.clientId= :client";
        String hql = "select ecl.user from CabinetUser as ecl where ecl.user.userId= :userId and ecl.cabinet= :cabinet";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("cabinet", pk);
      
    return (User) query.uniqueResult();
    }
}
