/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "personal_cabinet")
public class PersonalCabinet extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_cabinet_id")
    private Long personalCabinetId;

    @Email
    @Column(name = "email")
    @NotNull(message = "поле Email не может быть пустым")
    @NotBlank(message = "поле Email не может быть пустым")
    private String email;

    @Column(name = "company")
    @NotNull(message = "поле название компании не может быть пустым")
    private String company;

    @Column(name = "phone")
    @NotNull(message = "Поле телефон не может быть пустым")
    @NotBlank(message = "Поле телефон не может быть пустым")
    private String phone;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<CabinetUser> cabinetUserList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<Strategy> strategyList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<Group> groupList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<Module> moduleList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<Campaign> eventList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<Client> clientList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<ModuleEventClient> moduleEventClientList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cabinet")
    private List<Event> eventClientLinkList;

    public List<Strategy> getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List<Strategy> strategyList) {
        this.strategyList = strategyList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    public Long getPersonalCabinetId() {
        return personalCabinetId;
    }

    public void setPersonalCabinetId(Long personalCabinetId) {
        this.personalCabinetId = personalCabinetId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Long getId() {
        return personalCabinetId;
    }

    public List<CabinetUser> getActiveCabinetUserList() {
        List<CabinetUser> activeCabinetUserList = new ArrayList<>();
        for (CabinetUser cabinetUser : cabinetUserList) {
            if (cabinetUser.getDeleteDate() == null) {
                activeCabinetUserList.add(cabinetUser);
            }
        }
        return activeCabinetUserList;
    }

    public List<CabinetUser> getRoleUserActiveCabinetUserList() {
        List<CabinetUser> activeCabinetUserList = getActiveCabinetUserList();
        List<CabinetUser> roleUserActiveCabinetUserList = new ArrayList<>();
        for (CabinetUser cabinetUser : activeCabinetUserList) {
            if (cabinetUser.getUser_role().equals("user")) {
                roleUserActiveCabinetUserList.add(cabinetUser);
            }
        }
        return roleUserActiveCabinetUserList;
    }

    public List<CabinetUser> getCabinetUserList() {
        return cabinetUserList;
    }

    public void setCabinetUserList(List<CabinetUser> cabinetUserList) {
        this.cabinetUserList = cabinetUserList;
    }

    public List<Campaign> getEventList() {
        return eventList;
    }

    public void setEventList(List<Campaign> eventList) {
        this.eventList = eventList;
    }

    public List<ModuleEventClient> getModuleEventClientList() {
        return moduleEventClientList;
    }

    public void setModuleEventClientList(List<ModuleEventClient> moduleEventClientList) {
        this.moduleEventClientList = moduleEventClientList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Event> getEventClientLinkList() {
        return eventClientLinkList;
    }

    public void setEventClientLinkList(List<Event> eventClientLinkList) {
        this.eventClientLinkList = eventClientLinkList;
    }

}
