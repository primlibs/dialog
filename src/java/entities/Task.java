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
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "task")
public class Task extends PrimEntity {
    
    public static Integer CLOSE_PERFORM=1;
    public static Integer CLOSE_DECLINE=2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @Column(name = "name")
    @NotBlank(message = "Передайте название задачи")
    private String name;
    
    @JoinColumn(name = "performer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Передайте исполнителя")
    @Index(name="performerIndex")
    private User performer;

    @Column(name = "close_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date closeDate;
    
    
    @Column(name = "close_type")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Integer closeType;

    @Column(name = "perform_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date performDate;

    @Override
    public Long getId() {
        return taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void getTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getPerformer() {
        return performer;
    }

    public void setPerformer(User user) {
        this.performer = performer;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public Date getPerformDate() {
        return performDate;
    }

    public void setPerformDate(Date performDate) {
        this.performDate = performDate;
    }

    
}
