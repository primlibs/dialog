/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CabinetUserDao;
import dao.PersonalCabinetDao;
import entities.CabinetUser;
import entities.PersonalCabinet;
import entities.User;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
import support.AuthManager;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CabinetUserService extends PrimService {

    @Autowired
    private CabinetUserDao cabinetUserDao;

    @Autowired
    private PersonalCabinetDao cabinetDao;

    @Autowired
    private AuthManager user;

    public List<CabinetUser> getByUser(User user) {
        return cabinetUserDao.getCabinetUserChoice(user);
    }

    public String getNameCompany(Long cabinetId) {
       
      
        if (cabinetId == null) {
            return "не определено";
        }
        PersonalCabinet cabinet= cabinetDao.find(cabinetId);
        cabinet = cabinetDao.find(cabinetId);

        return cabinet.getCompany();
    }

    public String getNameUser() throws Exception {
        if (user == null) {
            throw new Exception("user is null");
        }
        if (user.getCurrentUser() == null) {
            throw new Exception("userCurrent is null");
        }

        return user.getCurrentUser().getName() + " " + user.getCurrentUser().getSurname();
    }
    
    public Boolean isSuperRole() throws Exception {
        if (user == null) {
            throw new Exception("user is null");
        }
        if (user.getCurrentUser() == null) {
            throw new Exception("userCurrent is null");
        }
        return user.getCurrentUser().isSuperAdmin();
    }

    public String getUserRole(Long userId,Long pkId) {
        
        List<CabinetUser> culist = cabinetUserDao.getByUserAndCabinet(userId, pkId);
       
        return culist.get(0).getUserRole();
    }
    
    public CabinetUser getCabinet(Long pkId) {
        List<CabinetUser> culist = cabinetUserDao.getByCabinet(pkId);
        return culist.get(0);
    }
    
    public Boolean existInCabinet(Long cabinetUserId,Long pkId){
        if(cabinetUserDao.getCUByIdAndCabinet(cabinetUserId, pkId)!=null){
            return true;
        }
        return false;
    }
    
    public Boolean isUserInCabinet(Long userId,Long pkId){
        return cabinetUserDao.isUserInCabinet(userId,pkId);
    }
    
}
