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

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "event")
public class Event extends PrimEntity {
    
    public static int FAILED = 4;
    public static int SUCCESSFUL = 3;
    public static int POSTPONED = 2;
    public static int ASSIGNED = 1;
    public static int UNASSIGNED = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @JoinColumn(name = "campaign_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Index(name="campaignIndex")
    private Campaign campaign;

    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @Index(name="clientIndex")
    private Client client;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    //@Index(name="userIndex")
    private User user;

    @Column(name = "status")
    private Integer status;

    @Column(name = "comment")
    private String comment;
    
    @Column(name = "final_comment")
    private String finalComment;
    
    @Column(name = "success_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date successDate;
    
    @Column(name = "postponed_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date postponedDate;
    
    @JoinColumn(name = "fail_reason_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private FailReason failReason;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "event")
    private List<ModuleEventClient> moduleEventClientList;

    @Override
    public Long getId() {
        return eventId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ModuleEventClient> getModuleEventClientList() {
        return moduleEventClientList;
    }

    public void setModuleEventClientList(List<ModuleEventClient> moduleEventClientList) {
        this.moduleEventClientList = moduleEventClientList;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getFinalComment() {
        return finalComment;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public Date getPostponedDate() {
        return postponedDate;
    }

    public void setPostponedDate(Date postponedDate) {
        this.postponedDate = postponedDate;
    }

    public FailReason getFailReason() {
        return failReason;
    }

    public void setFailReason(FailReason failReason) {
        this.failReason = failReason;
    }
    
    public boolean isClosed(){
        if(status!=null&&(status==FAILED||status==SUCCESSFUL)){
            return true;
        }
        return false;
    }
    
    public String getRusStatus(){
        String res = "Не назначено";
        if(UNASSIGNED==status){
            return "Не назначено";
        }else if(ASSIGNED==status){
            return "Назначено";
        }else if(POSTPONED==status){
            return "Перенесено";
        }else if(SUCCESSFUL==status){
            return "Успех";
        }else if(FAILED==status){
            return "Провал";
        }else{
            return "Не определен";
        }
    }

}
