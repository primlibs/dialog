/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import org.hibernate.criterion.Order;

/**
 *
 * @author Юрий
 */
public interface OrderManager {
     void placeOrder(Order order);
    
}
