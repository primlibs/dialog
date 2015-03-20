/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.parent.Dao;
import entities.History;

/**
 *
 * @author Юрий
 */
public class HistoryDao extends Dao<History> {

    @Override
    public Class getSupportedClass() {
        return History.class;
    }

}
