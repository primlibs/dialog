/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Modules;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class ModulesDao extends Dao<Modules>{

    @Override
    public Class getSupportedClass() {
        return Modules.class;
    }
    
}
