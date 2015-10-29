/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.CabinetUser;
import entities.PersonalCabinet;
import entities.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class CabinetUserDao extends Dao<CabinetUser> {

    @Override
    public Class getSupportedClass() {
        return CabinetUser.class;
    }

    public List<CabinetUser> getCabinetUserChoice(User user) {
        String hql = "from CabinetUser cu where cu.user=:user and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("user", user);
        return query.list();
    }

    public List<CabinetUser> getByUserAndCabinet(Long userId, Long pkId) {
        String hql = "from CabinetUser cu where cu.user.userId=:userId and cu.cabinet.pkId=:pkId and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<CabinetUser> getByCabinet(Long pkId) {
        String hql = "from CabinetUser cu where cu.cabinet.pkId=:pkId and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public List<CabinetUser> getMakingCallsCabUsers(Long pkId){
        String hql = "from CabinetUser cu where cu.cabinet.pkId=:pkId and cu.makesCalls=1 and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        return query.list();
    }
    
    public CabinetUser getCUByIdAndCabinet(Long cabinetUserId,Long pkId){
        String hql = "from CabinetUser cu where cu.cabinet.pkId=:pkId and cu.cabinetUserId=:cuId and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("pkId", pkId);
        query.setParameter("cuId", cabinetUserId);
        return (CabinetUser)query.uniqueResult();
    }


}
