/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
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
@Table(name = "event")
public class Event extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalCabinet cabinet;

    @JoinColumn(name = "strategy_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Strategy strategy;

    @Column(name = "creation_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "end_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "status")
    private String status;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "event")
    private List<EventClientLink> eventClientList;

    @Override
    public Long getId() {
        return eventId;
    }

}
