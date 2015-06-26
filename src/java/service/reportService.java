/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.ModuleDao;
import dao.ModuleEventClientDao;
import dao.UserDao;
import entities.Module;
import entities.User;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
import support.StringAdapter;

/**
 *
 * @author bezdatiuzer
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class reportService extends PrimService {
    
    @Autowired
    private ModuleEventClientDao moduleEventClientDao;
    
    @Autowired
    private ModuleDao moduleDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ModuleService moduleService;
    
    
    
    public LinkedHashMap<Module,LinkedHashMap<User,String[]>> getDataForModuleReport(Long pkId){
        
        LinkedHashMap<Long,User>users=userService.getMakingCallsAndParticipatedUsersMap(pkId);
        HashMap<Long,Module>modules=moduleService.getAllModulesMap(pkId);
        List<Object[]> failedCountList = moduleEventClientDao.getCountedFailedEventsModuleData(pkId);
        List<Object[]> allCountList = moduleEventClientDao.getCountedAllEventsIncludingModulesData(pkId);
        LinkedHashMap<Module,LinkedHashMap<User,String[]>> res = new LinkedHashMap();
        
        for(Object[] o:failedCountList){
            Long moduleId = (Long)o[0];
            Long userId = (Long)o[1];
            String countRes = StringAdapter.getString(o[2]);
            
            Module m = modules.get(moduleId);
            
            
            LinkedHashMap<User,String[]> userDataMap = new LinkedHashMap();
            /*for(Map.Entry<Long,User> entry:users.entrySet()){
                userDataMap.put(entry.getValue(),);
            }
            
            supMap.put(u,countRes);
            res.put(m, supMap);*/
        }
        return res;
    }
    
    /*public HashMap<Long,User> getUserMap(Long pkId){
        List<User> users = userDao.getUsers(pkId);
        
    }*/
    
    /*private LinkedHashMap<Module,LinkedHashMap<User,String[]>> getModuleMap(Long pkId){
        List<Module> allModules = moduleDao.getAllModules(pkId);
        LinkedHashMap<Module,LinkedHashMap<User,String[]>> res = new LinkedHashMap();
        
        for(Module m:allModules){
            LinkedHashMap<User,String[]> map = getMakingCallsAndParticipatedUserMap(pkId);
            res.put(m, map);
        }
        return res;
    }
    
    private LinkedHashMap<User,String[]> getMakingCallsAndParticipatedUserMap(Long pkId){
        List<User> users = userService.getMakingCallsAndParticipatedUsers(pkId);
        Collections.sort(users,new SurnameComparator());
        LinkedHashMap<User,String[]> res = new LinkedHashMap();
        for(User u:users){
            String[] arr = new String[3];
            arr[0]="0";
            arr[1]="0";
            arr[2]="0";
            res.put(u, arr);
        }
        return res;
    }
    
    private class SurnameComparator implements Comparator<User> {
        @Override
        public int compare(User a, User b) {
            return a.getSurname().compareToIgnoreCase(b.getSurname());
        }
    }*/
    
    
    
}
