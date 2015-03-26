/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CabinetUserDao;
import entities.CabinetUser;
import entities.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
public class CabinetUserService {
    
     @Autowired
    private CabinetUserDao cabinetUserDao;
     
     public List<CabinetUser> getByUser(User user) {
         return cabinetUserDao.getByUser(user);
     }
    
}
