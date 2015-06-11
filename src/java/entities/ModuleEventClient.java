/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Index;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "module_event_client")
public class ModuleEventClient extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_event_client_id")
    private Long moduleEventClientId;

    @JoinColumn(name = "event_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Index(name="eventIndex")
    private Event event;

    @Column(name = "insert_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date insertDate;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @JoinColumn(name = "strategy_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Strategy strategy;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;

    @JoinColumn(name = "module_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Index(name="moduleIndex")
    private Module module;

    @Column(name = "sign")
    private String sign;

    @Override
    public Long getId() {
        return moduleEventClientId;
    }

    public Long getModuleEventClientId() {
        return moduleEventClientId;
    }

    public void setModuleEventClientId(Long moduleEventClientId) {
        this.moduleEventClientId = moduleEventClientId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getSing() {
        return sign;
    }

    public void setSing(String sing) {
        this.sign = sign;
    }

}
