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
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "event")
public class Event extends PrimEntity {

    static public Long ACTIVE = Long.valueOf(0);
    static public Long CLOSE = Long.valueOf(1);

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

    @Column(name = "name")
    @NotBlank(message = "поле Название эвента не может быть пустым")
    private String name;

    @Column(name = "status")
    @NotNull(message = "поле статус не может быть пустым")
    private Long status;

    @Column(name = "unique_id")
    private String uniqueId;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "event")
    private List<EventClientLink> eventClientList;

    @Override
    public Long getId() {
        return eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<EventClientLink> getEventClientList() {
        return eventClientList;
    }

    public void setEventClientList(List<EventClientLink> eventClientList) {
        this.eventClientList = eventClientList;
    }

}
