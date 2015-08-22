/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PersonalCabinetDao;
import entities.PersonalCabinet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;

/**
 *
 * @author bezdatiuzer
 */
@Service
@Transactional
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdminService extends PrimService {
    
    @Autowired
    PersonalCabinetDao pkDao;
    
    public List<PersonalCabinet> getPkList() {
        return pkDao.getAll();
    }
    
}
