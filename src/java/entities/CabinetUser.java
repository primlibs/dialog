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
import entities.parent.PrimEntity;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

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
    @NotNull(message = "Роль не может быть пустой")
    @NotBlank(message = "поле РОЛЬ не может быть пустым")
    private String userRole;
    
    //null - ne makes, 1 - makes
    @Column(name = "makes_calls")
    private Short makesCalls;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="userIdIndex")
    private User user;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @Column(name = "delete_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;

    public Long getCabinetUserId() {
        return cabinetUserId;
    }

    public void setCabinetUserId(Long cabinetUserId) {
        this.cabinetUserId = cabinetUserId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
        return cabinetUserId;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Short getMakesCalls() {
        return makesCalls;
    }

    public void setMakesCalls(Short makesCalls) {
        this.makesCalls = makesCalls;
    }
}
