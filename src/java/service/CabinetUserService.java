/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import controllers.LkController;
import dao.CabinetUserDao;
import dao.PersonalCabinetDao;
import entities.CabinetUser;
import entities.PersonalCabinet;
import entities.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import support.AuthManager;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
public class CabinetUserService {

    @Autowired
    private CabinetUserDao cabinetUserDao;

    @Autowired
    private PersonalCabinetDao cabinetDao;

    @Autowired
    private AuthManager user;

    public List<CabinetUser> getByUser(User user) {
        return cabinetUserDao.getByUser(user);
    }

    public String NameCompany(HttpServletRequest request) {
        PersonalCabinet cabinet;
        Object cabinetId = request.getSession().getAttribute(LkController.CABINET_ID_SESSION_NAME);
        if (cabinetId == null) {
            return "не определено";
        }
        cabinet = cabinetDao.find((Long) cabinetId);

        return cabinet.getCompany();
    }

    public String NameUser() throws Exception {
        if(user==null){
            throw new Exception("user is null");
        }
        if(user.getCurrentUser()==null){
            throw new Exception("userCurrent is null");
        }
        
        return user.getCurrentUser().getName() + " " + user.getCurrentUser().getSurname();
    }

}
