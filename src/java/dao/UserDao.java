/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class UserDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public void save(User user)  {
        sessionFactory.getCurrentSession().save(user);
    }
    
}
