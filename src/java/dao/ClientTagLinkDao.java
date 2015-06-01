/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.parent.Dao;
import entities.ClientTagLink;
import entities.Tag;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bezdatiuzer
 */
@Repository
public class ClientTagLinkDao extends Dao<ClientTagLink> {
    
    @Override
    public Class getSupportedClass() {
        return ClientTagLink.class; 
    }
    
    public List<Tag> getNotLinkedTags(Long clientId){
        String hql="select t from Tag t where t.deleteDate is null and t.tagId not in(select ctl.tag.tagId from ClientTagLink ctl where ctl.client.clientId=:clientId)";
        Query query = getCurrentSession().createQuery(hql);
        query.setParameter("clientId", clientId);
        return query.list();
    }
    
}
