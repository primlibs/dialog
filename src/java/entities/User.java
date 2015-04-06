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
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "users")
public class User extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Email
    @Column(name = "email")
    @NotNull(message = "поле Email не может быть пустым")
    @NotBlank (message = "поле Email не может быть пустым")
    private String email;

    @Size(min = 4, max = 16, message="Пароль от 4 до 16 символов" )
    @Column(name = "password")
    @NotNull(message = " поле Пароль не может быть пустым")
    @NotBlank (message = "поле Пароль не может быть пустым")
    private String password;

    @Column(name = "surname")
    @NotNull(message = "поле Фамилия не может быть пустым")
    @NotBlank (message = "поле Фамилия не может быть пустым")
    private String surname;

    @Column(name = "name")
    @NotNull(message = "поле Имя не может быть пустым")
    @NotBlank (message = "поле Имя не может быть пустым")
    private String name;

    @Column(name = "patronymic")
    @NotNull(message = "поле Отчество не может быть пустым")
    @NotBlank (message = " поле Отчество не может быть пустым")
    private String patronymic;

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

}
