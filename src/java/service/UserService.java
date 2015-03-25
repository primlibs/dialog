/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CabinetUserDao;
import dao.PersonalCabinetDao;
import dao.UserDao;
import entities.CabinetUser;
import entities.PersonalCabinet;
import entities.User;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CabinetUserDao cabinetUserDao;
    
    @Autowired
    private PersonalCabinetDao cabinetDao;
    
    public void save(
            String company,
            String email,
            String phone,
            String password,
            String name,
            String surname,
            String patronymic,
            String emailCompany
            ){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        userDao.save(user);
        
        PersonalCabinet cabinet = new PersonalCabinet();
        cabinet.setEmail(emailCompany);
        cabinet.setPhone(phone);
        cabinet.setCompany(company);
        cabinetDao.save(cabinet);
        
        CabinetUser link  = new CabinetUser();
        link.setCabinet(cabinet);
        link.setUser(user);
        link.setUser_role("Admin");
        
        cabinetUserDao.save(link);
        
    }
    
}
