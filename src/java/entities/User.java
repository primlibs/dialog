/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "users")
public class User extends PrimEntity {
    
    public static String SUPERADMIN = "superadmin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Email
    @Column(name = "email")
    @NotNull(message = "Необходимо указать Email")
    @Index(name="emailIndex")
    private String email;

    @Size(min = 4, message = "Пароль от 4 символов")
    @Column(name = "password")
    @NotNull(message = "Необходимо указать Пароль")
    private String password;

    @Column(name = "surname")
    @NotNull(message = "Необходимо указать Фамилию")
    private String surname;

    @Column(name = "name")
    @NotNull(message = "Необходимо указать Имя")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "recoverDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date recoverDate;

    @Column(name = "recoverHash")
    private String recoverHash;
    
    @Column(name = "supermark")
    private String supermark;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "user")
    private List<CabinetUser> cabinetUser;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "user")
    private List<Event> events;

    public List<CabinetUser> getCabinetUser() {
        return cabinetUser;
    }

    public void setCabinetUser(List<CabinetUser> cabinetUser) {
        this.cabinetUser = cabinetUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public Long getId() {
        return userId;
    }

    public Date getRecoverDate() {
        return recoverDate;
    }

    public void setRecoverDate(Date recoverDate) {
        this.recoverDate = recoverDate;
    }

    public String getRecoverHash() {
        return recoverHash;
    }

    public void setRecoverHash(String recoverHash) {
        this.recoverHash = recoverHash;
    }

    public List<Event> getEventClientList() {
        return events;
    }

    public void setEventClientList(List<Event> eventClientList) {
        this.events = eventClientList;
    }
    
    public String getShortName(){
        String res = this.surname+" " + this.name.substring(0,1) + ".";
        if(this.patronymic!=null&&!this.patronymic.equals("")){
            res+=this.patronymic.substring(0, 1) + ".";
        }
        return res;
    }

    public boolean isSuperAdmin() {
        return SUPERADMIN.equals(this.supermark);
    }

    public void setSuperAdmin(Boolean set) {
        if(true==set){
            this.supermark = SUPERADMIN;
        }else{
            this.supermark = null;
        }
    }
    
    

}
