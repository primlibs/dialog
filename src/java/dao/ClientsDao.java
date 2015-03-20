/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Clients;

/**
 *
 * @author Юрий
 */
public class ClientsDao extends Dao<Clients>{
    
    @Override
    public Class getSupportedClass() {
        return Clients.class; 
    }
    
}
