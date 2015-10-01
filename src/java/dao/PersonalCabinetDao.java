/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.CabinetUser;
import entities.Campaign;
import entities.Client;
import entities.PersonalCabinet;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class PersonalCabinetDao extends Dao<PersonalCabinet> {

    @Override
    public Class getSupportedClass() {
        return PersonalCabinet.class;
    }

    public PersonalCabinet getCabinetByLogin(String emailCompany) {
        String queryString = "from PersonalCabinet U where U.email = :email";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("email", emailCompany);

        return (PersonalCabinet) query.uniqueResult();
    }
    
    public List<CabinetUser>getAdmins(Long pkId){
        String queryString = "from CabinetUser cu where cu.cabinet.pkId=:pkId and cu.deleteDate is null and cu.userRole=:admin";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("pkId", pkId);
        query.setParameter("admin", "admin");   
        return query.list();
    }
    
    public List<CabinetUser>getActiveUserList(Long pkId){
        String queryString = "from CabinetUser cu where cu.cabinet.pkId=:pkId and cu.deleteDate is null";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("pkId", pkId);  
        return query.list();
    }
    
    public List<Client>getClientList(Long pkId){
        String queryString = "from Client c where c.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("pkId", pkId);  
        return query.list();
    }
    
    public List<Campaign>getCampaignList(Long pkId){
        String queryString = "from Campaign c where c.cabinet.pkId=:pkId";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("pkId", pkId);  
        return query.list();
    }

    public List<PersonalCabinet> getCabinetList() {
        String queryString = "from PersonalCabinet order by endDate asc";
        Query query = getCurrentSession().createQuery(queryString);
        return query.list();
    }
    
}
