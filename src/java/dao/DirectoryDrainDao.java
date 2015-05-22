/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Drain;
import entities.Module;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Юрий
 */
@Repository
public class DirectoryDrainDao extends Dao<Module>{

    @Override
    public Class getSupportedClass() {
        return Drain.class;
    }
    
}
