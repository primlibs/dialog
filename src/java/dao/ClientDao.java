/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Client;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class ClientDao extends Dao<Client> {

    @Override
    public Class getSupportedClass() {
        return Client.class;
    }

    public Client getClientByUniqueIdInLk(String uid,Long cabinetId){
        
        String hql = "from Client as cu where cu.uniqueId= :uniqueId and cu.cabinet= :cabinet ";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("uniqueId", uid);
        query.setParameter("cabinet", cabinetId);
        List<Client> clist=query.list();
        if(clist.isEmpty()){
            return null;
        }else{
            return clist.get(0);
        }
    }
    
}
