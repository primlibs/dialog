/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "client")
public class Client extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalCabinet cabinet;

    @Column(name = "name_secretary")
    private String nameSecretary;

    @Column(name = "name_lpr")
    private String nameLpr;

    @Column(name = "name_company")
    private String nameCompany;

    @Column(name = "phone_secretary")
    private Long phoneSecretary;

    @Column(name = "phone_lpr")
    private Long phoneLpr;

    @Column(name = "address")
    private String address;

    @Column(name = "comment")
    private String comment;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "client")
    private List<EventClientLink> eventClientList;

    @Override
    public Long getId() {
        return clientId;
    }

}
