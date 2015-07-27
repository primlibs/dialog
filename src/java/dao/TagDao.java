/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.parent.Dao;
import entities.Tag;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bezdatiuzer
 */
@Repository
public class TagDao extends Dao<Tag> {
    
    @Override
    public Class getSupportedClass() {
       return Tag.class;
    }
    
    public List<Tag> getAllActiveTags(Long pkId){
        String hql = "from Tag t where t.cabinet.pkId=:pkId and t.deleteDate is null order by t.name";
        Query q = getCurrentSession().createQuery(hql);
        q.setParameter("pkId", pkId);
        return q.list();
    }
    
    public List<Tag> getDeletedTags(Long pkId){
        String hql = "from Tag where cabinet.pkId=:pkId and deleteDate is not null order by name";
        Query q = getCurrentSession().createQuery(hql);
        q.setParameter("pkId", pkId);
        return q.list();
    }
}
