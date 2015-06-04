/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.ClientDao;
import dao.ClientTagLinkDao;
import dao.PersonalCabinetDao;
import dao.TagDao;
import entities.Client;
import entities.ClientTagLink;
import entities.Tag;
import java.util.ArrayList;
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
    @Autowired
    private ClientTagLinkDao clientTagLinkDao;
    @Autowired
    private ClientDao clientDao;
    
    public List<Tag> getAllActiveTags(Long pkId){
        return tagDao.getAllTags(pkId);
    }
    
    public boolean create(String name,Long pkId){
        List<Tag> tags = tagDao.getAllTags(pkId);
        Tag supTag=null;
        boolean unique = true;
        boolean deleted = false;
        for(Tag tag:tags){
            if(tag.getName().equalsIgnoreCase(name)){
                unique = false;
                supTag = tag;
                if(tag.getDeleteDate()!=null){
                    deleted = true;
                }
            }
        }
        if(unique){
            Tag tag = new Tag();
            tag.setCabinet(pkDao.find(pkId));
            tag.setName(name);
            if(validate(tag)){
                tagDao.save(tag);
                return true;
            }
        }else{
            if(deleted&&supTag!=null){
                supTag.setDeleteDate(null);
                supTag.setName(name);
                if(validate(supTag)){
                    tagDao.update(supTag);
                    return true;
                }
            }else{
                addError("Такой тэг уже есть");
                return false;
            }
        }
        return false;
    }
    
    public void delete(Long tagId,boolean deleteLinks){
        Tag tag = tagDao.find(tagId);
        if(tag!=null){
            if(deleteLinks){
                for(ClientTagLink ctl:tag.getClientLinks()){
                    clientTagLinkDao.delete(ctl);
                }
            }
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
            if(tag.getName().equalsIgnoreCase(name)){
                return false;
            }
        }
        return true;
    }
    
    public boolean addTagToClient(Long clientId,Long[] tags){
        List<ClientTagLink> listForSave = new ArrayList();
        Client client = clientDao.find(clientId);
        if(!(tags.length==1&&tags[0]==(long)0)){
            for(Long tagId:tags){
                ClientTagLink ctl = new ClientTagLink();
                Tag tag = tagDao.find(tagId);
                ctl.setClient(client);
                ctl.setTag(tag);
                if(validate(ctl)){
                    listForSave.add(ctl);
                }
            }
            for(ClientTagLink link:listForSave){
                clientTagLinkDao.save(link);
            }
            return true;
        }
        return false;
    }
    
    public boolean deleteClientTagLink(Long ctlId){
        ClientTagLink ctl = clientTagLinkDao.find(ctlId);
        if(ctl!=null){
            clientTagLinkDao.delete(ctl);
            return true;
        }
        addError("Связь Тэга с клиентом не найдена");
        return false;
    }
    
    public List<Tag> getNotLinkedTags(Long ClientId,Long pkId){
        return clientTagLinkDao.getNotLinkedTags(ClientId,pkId);
    }
    
    public List<Tag> getDeletedTags(Long pkId){
        return tagDao.getDeletedTags(pkId);
    }
    
}
