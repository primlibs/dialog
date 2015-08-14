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
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "campaign")
public class Campaign extends PrimEntity {

    static public Long ACTIVE = (long) 0;
    static public Long CLOSE = (long) 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long campaignId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Необходимо указать ИД кабинета")
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @JoinColumn(name = "strategy_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="strategyIndex")
    private Strategy strategy;

    @Column(name = "creation_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "end_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "name")
    @NotBlank(message = "Название кампании не может быть пустым")
    private String name;

    @Column(name = "status")
    @NotNull(message = "поле статус не может быть пустым")
    @Index(name="statusIndex")
    private Long status;
    
    @Column(name="show_modules_with_text")
    private Long showModulesWithText;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "campaign")
    private List<Event> events;

    @Override
    public Long getId() {
        return campaignId;
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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getShowModulesWithText() {
        return showModulesWithText;
    }

    public void setShowModulesWithText(Long ShowModulesWithText) {
        this.showModulesWithText = ShowModulesWithText;
    }
    
    public String getShowModulesWithTextCheck() {
        if(showModulesWithText!=null){
            return "checked";
        }
            return "";
    }
    
    public Boolean isClosed(){
        if(ACTIVE.equals(status)){
            return false;
        }
        if(CLOSE.equals(status)){
            return true;
        }
        return null;
    }

}
