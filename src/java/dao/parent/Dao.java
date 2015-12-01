/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.parent;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Юрий
 */
public abstract class Dao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public abstract Class getSupportedClass();

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public void save(T obj) {
        getCurrentSession().save(obj);
    }

    public void update(T obj) {
        getCurrentSession().merge(obj);
    }

    public List<T> getAll() {
        Criteria cr = getCurrentSession().createCriteria(getSupportedClass());
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return cr.list();
    }

    public void delete(T obj) {
        obj=(T) getCurrentSession().merge(obj);
        getCurrentSession().delete(obj);
    }

    public T find(Long id) {
        if (id != null) {
            T obj = (T) getCurrentSession().get(getSupportedClass(), id);
            if (obj != null) {
                getCurrentSession().evict(obj);
            }
            return obj;
        }
        return null;
    }
    
    public List<T> find(T obj) {
        Criteria cr = getCurrentSession().createCriteria(getSupportedClass());
        cr.add(Example.create(obj).excludeZeroes());
        return cr.list();
    }
    
    
    protected Criteria getCriteriaDistinctRoot(Class cl) {
      Criteria crit = getCurrentSession().createCriteria(cl);
      crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
      return crit;
  }
  
  protected Criterion nullOrFalse(String fieldName) {
      return Restrictions.or( Restrictions.isNull(fieldName), Restrictions.eq(fieldName, false) );
  }

  public List<T> getAllAsc(String... order) {
        Criteria cr = getCurrentSession().createCriteria(getSupportedClass());
        for (String ordPosition : order) {
            cr.addOrder(Order.asc(ordPosition));
        }
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return cr.list();
    }

    public List<T> getAllDesc(String... order) {
        Criteria cr = getCurrentSession().createCriteria(getSupportedClass());
        for (String ordPosition : order) {
            cr.addOrder(Order.desc(ordPosition));
        }
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return cr.list();
    }
  
}
