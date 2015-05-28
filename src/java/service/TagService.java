/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.PersonalCabinetDao;
import dao.TagDao;
import entities.Tag;
import java.util.Date;
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
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TagService extends PrimService {
    
    @Autowired
    private TagDao tagDao;
    @Autowired
    private PersonalCabinetDao pkDao;
    
    public List<Tag> getAllActiveTags(Long pkId){
        return tagDao.getAllTags(pkId);
    }
    
    public boolean create(String name,Long pkId){
        Tag tag = new Tag();
        tag.setPk(pkDao.find(pkId));
        if(isUniqueName(name,pkId)){
            tag.setName(name);
        }else{
            addError("Такой тэг уже есть");
        }
        if(validate(tag)){
            tagDao.save(tag);
            return true;
        }
        return false;
    }
    
    public void delete(Long tagId){
        Tag tag = tagDao.find(tagId);
        if(tag!=null){
            tag.setDeleteDate(new Date());
        }else{
            addError("Не удалось найти тэг");
        }
    }
    
    public boolean changeName(Long tagId,String newName){
        Tag tag = tagDao.find(tagId);
        if(tag!=null){
            tag.setName(newName);
            if(validate(tag)){
                tagDao.update(tag);
                return true;
            }
        }else{
            addError("Не удалось найти тэг");
        }
        return false;
    }
    
    public boolean isUniqueName(String name,Long pkId){
        for(Tag tag:getAllActiveTags(pkId)){
            if(tag.getName().equals(name)){
                return false;
            }
        }
        return true;
    }
    
}
