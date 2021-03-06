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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "strategy")
public class Strategy extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strategy_id")
    private Long strategyId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @Column(name = "name")
    @NotNull(message = "Наименование сценария не указано")
    @NotBlank(message = "Наименование сценария не указано")
    private String strategyName;
    
    @Column(name = "isin")
    private String isin;

    @Column(name = "delete_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "strategy", orphanRemoval = true)
    @OrderBy("groupName")
    private List<Group> groupList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "strategy")
    private List<Campaign> campaigns;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "strategy")
    private List<ModuleEventClient> moduleEventClientList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "strategy")
    private List<FailReason> failReasons;

    @Override
    public Long getId() {
        return strategyId;

    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }


    public List<Campaign> getEventList() {
        return campaigns;
    }

    public void setEventList(List<Campaign> eventList) {
        this.campaigns = eventList;
    }

    public List<ModuleEventClient> getModuleEventClientList() {
        return moduleEventClientList;
    }

    public void setModuleEventClientList(List<ModuleEventClient> moduleEventClientList) {
        this.moduleEventClientList = moduleEventClientList;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public List<FailReason> getFailReasons() {
        return failReasons;
    }

    public void setFailReasons(List<FailReason> failReasons) {
        this.failReasons = failReasons;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

}
