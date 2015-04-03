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
import org.hibernate.validator.constraints.NotBlank;
import entities.parent.PrimEntity;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "cabinet_user")
public class CabinetUser extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cabinet_user_id")
    private Long cabinetUserId;

    @Column(name = "user_role")
    @NotNull(message = "Ем")
    private String user_role;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne
    private PersonalCabinet cabinet;

    public Long getCabinetUserId() {
        return cabinetUserId;
    }

    public void setCabinetUserId(Long cabinetUserId) {
        this.cabinetUserId = cabinetUserId;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
