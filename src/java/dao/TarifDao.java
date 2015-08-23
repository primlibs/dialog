/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Tarif;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bezdatiuzer
 */
@Repository
public class TarifDao extends Dao<Tarif> {
    
    @Override
    public Class getSupportedClass() {
       return Tarif.class;
    }
    
    public Tarif getByName(String name){
        String hql = "from Tarif t where t.name=:name";
        Query q = getCurrentSession().createQuery(hql);
        q.setParameter("name", name);
        return (Tarif) q.uniqueResult();
    }
    
    public List<Tarif> getDefaults(){
        String hql = "from Tarif t where t.defaultMark=:def";
        Query q = getCurrentSession().createQuery(hql);
        q.setParameter("def", Tarif.DEFAULTMARK);
        return q.list();
    }
    
}
