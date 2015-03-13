/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "cabinet_user")
public class CabinetUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinet_user_id")
    private Long cabinetUserId; 

    @JoinColumn(name = "user_id")
    @ManyToOne
       private User user;

    @JoinColumn(name="personal_cabinet_id")
    @ManyToOne
   private PersonalCabinet cabinet;
    
}
