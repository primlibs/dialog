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
 * @author bezdatiuzer
 */
@Entity
@Table(name = "event_comment")
public class EventComment extends PrimEntity {
    
    public static int CREATE = 0;
    public static int POSTPONE = 1;
    public static int ASSIGN = 2;
    public static int UNASSIGN = 3;
    public static int FAILED = 4;
    public static int SUCCESSFUL = 5;
    public static int COMMENTED = 6;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_id")
    private Long eventCommentId;
    
    @JoinColumn(name = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="eventIndex")
    private Event event;
    
    @Column(name = "insert_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date insertDate;
    
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
    
    @Column(name = "campaign_id")
    @NotNull(message = "Ид кампании не передан")
    @Index(name="campaignIndex")
    private Long campaignId;
    
    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;
    
    @Column(name = "comment",columnDefinition="TEXT")
    private String comment;
    
    @Column(name = "type")
    private Integer type;

    @Override
    public Long getId() {
        return eventCommentId;
    }

    public Long getEventCommentId() {
        return eventCommentId;
    }

    public void setEventCommentId(Long eventCommentId) {
        this.eventCommentId = eventCommentId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
        this.campaignId = event.getCampaign().getCampaignId();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }
    
    /*public String getMessage(){
        String msg = "";
        
    }*/
    
}
