/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import dao.ClientDao;
import dao.ModuleDao;
import entities.Client;
import entities.Event;
import entities.Module;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
import support.StringAdapter;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ClientService extends PrimService {
    
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ModuleDao moduleDao;
    
    public List<Client> getCabinetClients(Long pkId){
        return clientDao.getCabinetClients(pkId);
    }
    
    public Client getClient(Long clientId){
        return clientDao.find(clientId);
    }
    
    public List<Event> getFinishedEventsByClient(Long clientId){
        return clientDao.getFinishedEventsByClient(clientId);
    }
    
    public List<Event> getUnfinishedEventsByClient(Long clientId){
        return clientDao.getUnfinishedEventsByClient(clientId);
    }
    
    public List<Client> getClientsBySearchRequest(Long pkId,String uid,String adress,String nameCompany,String name,Long phone){
        return clientDao.getClientsBySearchRequest(pkId,uid, adress, nameCompany, name, phone);
    }
    
    public List<Module> getHistory(Long eventId){
        List<Module> dialog = moduleDao.getHistory(eventId);
        return dialog;
    }
    
    public boolean updateClientField(String field,Long clientId,String newVal){
        boolean performed=false;
        Client client = clientDao.find(clientId);
        if(client!=null){
            switch (field){
                case "adress":
                    client.setAddress(newVal);
                    break;
                case "nameSecretary":
                    client.setNameSecretary(newVal);
                    break;
                case "nameLpr":
                    client.setNameLpr(newVal);
                    break;
                case "phoneSecretary":
                    //++filtr++validation?
                    Long phoneSec=StringAdapter.toLong(newVal);
                    client.setPhoneSecretary(phoneSec);
                    break;
                case "phoneLpr":
                    //same
                    Long phoneLpr=StringAdapter.toLong(newVal);
                    client.setPhoneLpr(phoneLpr);
                    break;
                case "comment":
                    client.setComment(newVal);
                    break;
                default:client=null;
                    break;
            }
        }else{
            addError("Клиент с ИД:"+clientId+" не найден");
        }
        if(client!=null){
            if(validate(client)){
                clientDao.update(client);
                performed=true;
            }
        }
        return performed;
    }
    
}
