/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.Right;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Кот
 */
@Repository("rightDao")
public class RightDao extends Dao<Right>{

    @Override
    public Class getSupportedClass() {
        return Right.class;
    }  
    
    
    
    
}
