/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import dao.ClientDao;
import entities.Client;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;

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
    
    public List<Client> getCabinetClients(Long pkId){
        return clientDao.getCabinetClients(pkId);
    }
    
    public Client getClient(Long clientId){
        return clientDao.find(clientId);
    }
    
}
