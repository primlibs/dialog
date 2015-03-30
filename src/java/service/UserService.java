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
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CabinetUserDao cabinetUserDao;

    @Autowired
    private PersonalCabinetDao cabinetDao;

    private String error = "";

    public void save(
            String company,
            String email,
            String phone,
            String password,
            String name,
            String surname,
            String patronymic,
            String emailCompany
    ) {

        User existingUser;
        PersonalCabinet existingEmailCompany;
        existingUser = userDao.getUserByLogin(email);
        existingEmailCompany = cabinetDao.getCabinetByLogin(emailCompany);

        if (existingUser != null | existingEmailCompany != null) {
            error = "Ошибка мыла личного | конторы";
        } else {

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

            CabinetUser link = new CabinetUser();
            link.setCabinet(cabinet);
            link.setUser(user);
            link.setUser_role("admin");

            cabinetUserDao.save(link);

        }

    }

    public void addUser(
            String email,
            String phone,
            String password,
            String name,
            String surname,
            String patronymic,
            String role,
            Object cabinetId) {

        User existingUser;
        existingUser = userDao.getUserByLogin(email);
        PersonalCabinet cabinet = cabinetDao.find((Long) cabinetId);

        if (existingUser != null) {

            boolean exist = existCabinetUser(existingUser, cabinetId);

            if (exist == false) {
                CabinetUser link = new CabinetUser();
                link.setCabinet(cabinet);
                link.setUser_role(role);
                link.setUser(existingUser);
                cabinetUserDao.save(link);
            }else {
            error="";
        }

        } else {

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            userDao.save(user);

            CabinetUser link = new CabinetUser();

            link.setCabinet(cabinet);
            link.setUser_role(role);
            link.setUser(user);
            cabinetUserDao.save(link);

        }
    }

    private boolean existCabinetUser(User existingUser, Object cabinetId) {
        PersonalCabinet cabinet = cabinetDao.find((Long) cabinetId);
        List<CabinetUser> list = cabinetUserDao.getByUserAndCabinet(existingUser, cabinet);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getError() {
        return error;
    }

}
