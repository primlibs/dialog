/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.RightDao;
import entities.Right;
import service.parent.PrimService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import support.logic.RightStack;

/**
 *
 * @author Кот
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RightService extends PrimService {

    @Autowired
    private RightDao rightDao;


    public List<Right> getRights() {
        return rightDao.getAllAsc("changeable","actionDescription");
    }
    
    public void update(RightStack rst) throws Exception {
       List<Right> lri=getRights();
       
       for(support.logic.Right right:rst.getRights()){
           Long baseid=null;
           for(Right inBase:lri){
               if(inBase.getObject().equals(right.getObject())&&inBase.getAction().equals(right.getAction())){
                   baseid=inBase.getRightId();
                   break;
               }
           }
           if(baseid==null){
               Right ri=new Right();
               ri.setAddDate(new Date());
               ri.setAction(right.getAction());
               ri.setObject(right.getObject());
               ri.setActionDescription(right.getActionDescription());
               ri.setObjectDescription(right.getObjectDescription());
               if(validate(ri)){
                   rightDao.save(ri);
               }
           }else{
               Right ri=rightDao.find(baseid);
               ri.setActionDescription(right.getActionDescription());
               ri.setObjectDescription(right.getObjectDescription());
               if(validate(ri)){
                   rightDao.update(ri);
               }
           }
           
       }
       
       for(Right base:lri){
           Long baseDel=base.getId();
           for(support.logic.Right right:rst.getRights()){
               if(base.getObject().equals(right.getObject())&&base.getAction().equals(right.getAction())){
                   baseDel=null;
               }
           }
           if(baseDel!=null){
               rightDao.delete(rightDao.find(baseDel));
           }
       }
    }  
    
     public void change(Long rightId) throws Exception {
         Right rt = rightDao.find(rightId);
         if(rt.isChangeable==true){
             rt.setChangeable(null);
         }else{
             rt.setChangeable(1);
         }
         rightDao.update(rt);
     }
    
}
