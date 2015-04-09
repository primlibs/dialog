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
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import service.parent.PrimService;
import support.AuthManager;
import support.Random;
import support.ServiceResult;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService extends PrimService {

    final public String DEFAULT_PASS = "0000";

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
    ) {

        User existingUser = userDao.getUserByLogin(email);
        PersonalCabinet existingEmailCompany = cabinetDao.getCabinetByLogin(emailCompany);

        if (existingUser != null || existingEmailCompany != null) {
            addError("Ошибка email личного | компании");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(AuthManager.md5Custom(password));
            user.setName(name);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            if (validate(user)) {
                userDao.save(user);
            }

            if (getError().isEmpty()) {
                PersonalCabinet cabinet = new PersonalCabinet();
                cabinet.setEmail(emailCompany);
                cabinet.setPhone(phone);
                cabinet.setCompany(company);
                if (validate(cabinet)) {
                    cabinetDao.save(cabinet);
                }

                if (getError().isEmpty()) {
                    CabinetUser link = new CabinetUser();
                    link.setCabinet(cabinet);
                    link.setUser(user);
                    link.setUser_role("admin");
                    if (validate(link)) {
                        cabinetUserDao.save(link);
                    }
                }
            }

        }

    }

    public void addUser(
            String email,
            String phone,
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
                if (validate(link)) {
                    cabinetUserDao.save(link);
                }

            } else {
                addError("Пользователь уже существует");
            }

        } else {

            User user = new User();
            user.setEmail(email);
            user.setPassword(AuthManager.md5Custom(DEFAULT_PASS));
            user.setName(name);
            user.setSurname(surname);
            user.setPatronymic(patronymic);
            if (validate(user)) {
                userDao.save(user);
            }

            if (getError().isEmpty()) {
                CabinetUser link = new CabinetUser();
                link.setCabinet(cabinet);
                link.setUser_role(role);
                link.setUser(user);
                if (validate(link)) {
                    cabinetUserDao.save(link);
                }
            }

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

    public ServiceResult changePassword(
            String oldPassword,
            String password,
            String confirmPassword
    ) {
        ServiceResult result = new ServiceResult();
        User user = authManager.getCurrentUser();
        String oldHash = user.getPassword();

        if (oldHash.equals(oldPassword)) {
            if (password.equals(confirmPassword)) {
                if (password.length() >= 4) {
                    user.setPassword(AuthManager.md5Custom(password));
                    userDao.save(user);

                } else {
                    result.addError("Длина пароля должна быть не менее 4 символов");
                }
            } else {
                result.addError("Значения паролей не совпадают");
            }
        } else {
            result.addError("Неправильно введен старый пароль");
        }
        return result;
    }

    public void recoveryPassword(String email) {
        User user = userDao.getUserByLogin(email);
        user.setRecoverDate(new Date());
        user.setRecoverHash(AuthManager.md5Custom(Random.getString("qwertyuiopasdfghjklzxcvbnm", 10)));
        if (validate(user)) {
                userDao.save(user);
            }
        
    }

    public void recoverPassword(){
         User user = userDao.getUserByHash(null);
         user.setPassword(DEFAULT_PASS);
    }
}
