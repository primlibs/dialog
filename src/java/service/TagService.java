/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.PersonalCabinetDao;
import dao.TagDao;
import entities.Client;
import entities.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
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
    private ClientDao clientDao;

    public List<Tag> getAllActiveTags(Long pkId) {
        return tagDao.getAllActiveTags(pkId);
    }

    public LinkedHashMap<Long, Tag> getAllActiveTagsMap(Long pkId) {
        LinkedHashMap<Long, Tag> res = new LinkedHashMap();
        for (Tag tag : getAllActiveTags(pkId)) {
            res.put(tag.getId(), tag);
        }
        return res;
    }

    public boolean create(String name, Long pkId) {
        List<Tag> tags = tagDao.getAllActiveTags(pkId);
        Tag supTag = null;
        boolean unique = true;
        boolean deleted = false;
        for (Tag tag : tags) {
            if (tag.getName().equalsIgnoreCase(name)) {
                unique = false;
                supTag = tag;
                if (tag.getDeleteDate() != null) {
                    deleted = true;
                }
            }
        }
        if (unique) {
            Tag tag = new Tag();
            tag.setCabinet(pkDao.find(pkId));
            tag.setName(name);
            if (validate(tag)) {
                tagDao.save(tag);
                return true;
            }
        } else {
            if (deleted && supTag != null) {
                supTag.setDeleteDate(null);
                supTag.setName(name);
                if (validate(supTag)) {
                    tagDao.update(supTag);
                    return true;
                }
            } else {
                addError("Такой тэг уже есть");
                return false;
            }
        }
        return false;
    }

    public void delete(Long tagId, boolean deleteLinks) {
        if (tagId != null) {
            Tag tag = tagDao.find(tagId);
            if (tag != null) {
                if (deleteLinks) {
                    for (Client client : tag.getClients()) {
                        Set<Tag> cltags = client.getTags();
                        cltags.remove(tag);
                        client.setTags(cltags);
                        if (validate(client)) {
                            clientDao.update(client);
                            addError(client.getNameCompany());
                        }
                    }
                }
                tag.setDeleteDate(new Date());
            } else {
                addError("Не удалось найти тэг");
            }
        } else {
            addError("Ид тэга не передан");
        }
    }

    public boolean changeName(Long tagId, String newName, Long pkId) {
        Tag tag = tagDao.find(tagId);
        if (tag != null) {
            tag.setName(newName);
            if (validate(tag)) {
                tagDao.update(tag);
                return true;
            }
        } else {
            addError("Не удалось найти тэг");
        }
        return false;
    }

    public boolean isUniqueName(String name, Long pkId) {
        for (Tag tag : getAllActiveTags(pkId)) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    public void addTagsToClient(Long clientId, Long[] tagIds, Long pkId) {
        if (pkId != null) {
            if (tagIds != null && !(tagIds.length == 1 && tagIds[0] == (long) 0)) {
                HashMap<Long, Tag> tagMap = getAllActiveTagsMap(pkId);
                Client client = clientDao.find(clientId);
                Set<Tag> tags = client.getTags();
                for (Long tagId : tagIds) {
                    Tag tag = tagMap.get(tagId);
                    if (tag != null) {
                        tags.add(tag);
                    }
                }
                client.setTags(tags);
                if (validate(client)) {
                    clientDao.update(client);
                }
            }
        } else {
            addError("Ошибка личного кабинета");
        }
    }

    /*public HashMap<Long,Tag>getClientTags(Long clientId,Long pkId){
     HashMap<Long,Tag>res = new HashMap();
     Client cl = clientDao.getClient(clientId,pkId);
     for(ClientTagLink ctl:cl.getTagLinks()){
     res.put(ctl.getTag().getId(),ctl.getTag());
     }
     return res;
     }*/
    /*public boolean deleteClientTagLink(Long ctlId){
     ClientTagLink ctl = clientTagLinkDao.find(ctlId);
     if(ctl!=null){
     clientTagLinkDao.delete(ctl);
     return true;
     }
     addError("Связь Тэга с клиентом не найдена");
     return false;
     }*/
    public void deleteClientTag(Long clientId, Long tagId, Long pkId) {
        if (pkId != null) {
            if (clientId != null) {
                Client c = clientDao.find(clientId);
                if (tagId != null) {
                    Tag t = tagDao.find(tagId);
                    Set<Tag> tags = c.getTags();
                    tags.remove(t);
                    c.setTags(tags);
                    if (validate(c)) {
                        clientDao.update(c);
                    }
                } else {
                    addError("Ид тэга не передан");
                }
            } else {
                addError("Ид клиента не передан");
            }
        } else {
            addError("Ошибка личного кабинета");
        }
    }

    public List<Tag> getNotLinkedTags(Long clientId, Long pkId) {
        List<Tag> res = new ArrayList();
        if (pkId != null) {
            if (clientId != null) {
                List<Tag> allTags = tagDao.getAllActiveTags(pkId);
                Client c = clientDao.find(clientId);
                Set<Tag> ctags = c.getTags();
                for (Tag t : allTags) {
                    if (!ctags.contains(t)) {
                        res.add(t);
                    }
                }
            } else {
                addError("Ид клиента не передан");
            }
        } else {
            addError("Ошибка личного кабинета");
        }
        return res;
    }

    public List<Tag> getDeletedTags(Long pkId) {
        return tagDao.getDeletedTags(pkId);
    }

}
