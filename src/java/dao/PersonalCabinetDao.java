/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.CabinetUser;
import entities.PersonalCabinet;
import java.util.ArrayList;
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
    
    public List<Object[]> getCampaignsAndFinishedCallsInCabinet(Long cabinetId){
        String hql="select count(ev.finalComment),ev.campaign from Event ev where ev.cabinet.personalCabinetId=:cabinetId group by ev.campaign order by ev.campaign.creationDate asc";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("cabinetId", cabinetId);
        List<Object[]> res=query.list();
        return res;
    }

}
