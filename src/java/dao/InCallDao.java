/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.parent.Dao;
import entities.InCall;
import entities.Tag;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bezdatiuzer
 */
@Repository
public class InCallDao extends Dao<InCall> {
    
    @Override
    public Class getSupportedClass() {
       return InCall.class;
    }
    
    
}
