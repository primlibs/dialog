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
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "modules")
public class Module extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    private Long moduleId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;
    
    @JoinColumn(name = "strategy_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="strategyIndex")
    private Strategy strategy;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name="groupIndex")
    private Group group;

    @Column(name = "name")
    @NotBlank(message = "Поле Название модуля не может быть пустым")
    private String moduleName;

    
    @Column(name = "body", columnDefinition="MEDIUMTEXT")
    private String bodyText;
    
    @Column(name = "position")
    private Long position;
    
    @Column(name = "hexcolor")
    private String hexcolor;

    @Column(name = "delete_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "module")
    private List<ModuleEventClient> moduleEventClientList;

    @Override
    public Long getId() {
        return moduleId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public List<ModuleEventClient> getModuleEventClientList() {
        return moduleEventClientList;
    }

    public void setModuleEventClientList(List<ModuleEventClient> moduleEventClientList) {
        this.moduleEventClientList = moduleEventClientList;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getHexcolor() {
        return hexcolor;
    }

    public void setHexcolor(String hexcolor) {
        this.hexcolor = hexcolor;
    }

    
    
}
