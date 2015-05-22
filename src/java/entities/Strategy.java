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
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalCabinet cabinet;

    @Column(name = "name")
    @NotBlank(message = "поле имя не может быть пустым")
    private String strategyName;

    @Column(name = "delete_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "strategy", orphanRemoval = true)
    private List<Group> groupList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "strategy")
    private List<Campaign> campaigns;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "strategy")
    private List<ModuleEventClient> moduleEventClientList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "strategy")
    private List<Drain> directoryDrainList;

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

    public List<Group> getActiveGroupList() {

        List<Group> activeGroupList = new ArrayList();
        for (Group group : groupList) {
            if (group.getDeleteDate() == null) {
                activeGroupList.add(group);
            }
        }
        return activeGroupList;
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

    public List<Drain> getDirectoryDrainList() {
        return directoryDrainList;
    }

    public void setDirectoryDrainList(List<Drain> directoryDrainList) {
        this.directoryDrainList = directoryDrainList;
    }

}
