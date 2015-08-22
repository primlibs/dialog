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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import support.editors.PhoneEditor;

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
    private Long pkId;

    @Email
    @Column(name = "email")
    //@NotNull(message = "поле Email не может быть пустым")
    private String email;

    @Column(name = "company")
    @NotNull(message = "поле название компании не может быть пустым")
    private String company;

    @Column(name = "phone")
    //@NotNull(message = "Поле телефон не может быть пустым")
    private String phone;
    
    //@LazyCollection(LazyCollectionOption.TRUE)
    
    
    @JoinColumn(name = "tarif_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Тариф не указан")
    private Tarif tarif;
    
    @Column(name = "begin_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    //@NotNull(message = "Дата начала не указана")
    private Date beginDate;
    
    @Column(name = "end_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    //@NotNull(message = "Дата окончания не указана")
    private Date endDate;
    
    //@LazyCollection(LazyCollectionOption.TRUE)
    
    
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Установивший пользователь не указан")
    private User userSet;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<CabinetUser> cabinetUserList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<Strategy> strategyList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<Group> groupList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<Module> moduleList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<Campaign> campaigns;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<Client> clientList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<ModuleEventClient> moduleEventClientList;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "cabinet")
    private List<Event> events;

    public List<Strategy> getStrategyList() {
        return strategyList;
    }
    
    public List<Strategy> getActiveStrategyList() {
        List<Strategy> res = new ArrayList();
        for(Strategy str:strategyList){
            if(str.getDeleteDate()==null){
                res.add(str);
            }
        }
        return res;
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

    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
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
        PhoneEditor phe= new PhoneEditor();
        this.phone = phe.getPhone(phone);
    }

    @Override
    public Long getId() {
        return pkId;
    }

    public List<CabinetUser> getActiveCabinetUserList() {
        List<CabinetUser> activeCabinetUserList = new ArrayList();
        for (CabinetUser cabinetUser : cabinetUserList) {
            if (cabinetUser.getDeleteDate() == null) {
                activeCabinetUserList.add(cabinetUser);
            }
        }
        return activeCabinetUserList;
    }

    public List<CabinetUser> getRoleUserActiveCabinetUserList() {
        List<CabinetUser> activeCabinetUserList = getActiveCabinetUserList();
        List<CabinetUser> roleUserActiveCabinetUserList = new ArrayList();
        for (CabinetUser cabinetUser : activeCabinetUserList) {
            if (cabinetUser.getUserRole().equals("user")) {
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

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUserSet() {
        return userSet;
    }

    public void setUserSet(User userSet) {
        this.userSet = userSet;
    }
    
    

}
