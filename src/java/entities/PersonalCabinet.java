/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "personal_cabinet")
public class PersonalCabinet extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_cabinet_id")
    private Long personalCabinetId;

    @Email
    @Column(name = "email")
    @NotNull(message = "Email не может быть пустым")
    private String email;

    @Column(name = "company")
    @NotNull(message = "Название компании не может быть пустым")
    private String company;

    @Column(name = "phone")
    @NotNull(message = "Телефон не может быть пустым")
    private String phone;

    public Long getPersonalCabinetId() {
        return personalCabinetId;
    }

    public void setPersonalCabinetId(Long personalCabinetId) {
        this.personalCabinetId = personalCabinetId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Long getId() {
       return personalCabinetId;
    }

}
