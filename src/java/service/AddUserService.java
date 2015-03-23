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
public class AddUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CabinetUserDao cabinetUserDao;

    @Autowired
    private PersonalCabinetDao cabinetDao;

    public void save(
           
            String email,
            String phone,
            String password,
            String name,
            String surname,
            String patronymic    )
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        userDao.save(user);

        
        CabinetUser link = new CabinetUser();
      
        link.setUser(user);
        cabinetUserDao.save(link);

    }
}
