/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import support.editors.PhoneEditor;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "client")
public class Client extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Личный кабинет не указан")
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;

    @Column(name = "name_secretary")
    private String nameSecretary;

    @Column(name = "name_lpr")
    private String nameLpr;

    @Column(name = "name_company")
    private String nameCompany;

    @Column(name = "phone_secretary")
    private String phoneSecretary;

    @Column(name = "phone_lpr")
    private String phoneLpr;

    @Column(name = "address")
    private String address;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "client")
    private List<Event> events;

    /*@LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "client")
    private List<ClientTagLink> tagLinks;*/
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_tags",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private Set<Tag> tags;

    @Column(name = "unique_id")
    @NotNull(message = "УИД не может быть пустым")
    @Index(name="uniqueIdIndex")
    private String uniqueId;

    @Override
    public Long getId() {
        return clientId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public String getNameSecretary() {
        return nameSecretary;
    }

    public void setNameSecretary(String nameSecretary) {
        this.nameSecretary = nameSecretary;
    }

    public String getNameLpr() {
        return nameLpr;
    }

    public void setNameLpr(String nameLpr) {
        this.nameLpr = nameLpr;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getPhoneSecretary() {
        return phoneSecretary;
    }

    public void setPhoneSecretary(String phoneSecretary) {
        PhoneEditor phe=new PhoneEditor();
        this.phoneSecretary = phe.getPhone(phoneSecretary);
    }

    public String getPhoneLpr() {
        return phoneLpr;
    }

    public void setPhoneLpr(String phoneLpr) {
        PhoneEditor phe=new PhoneEditor();
        this.phoneLpr = phe.getPhone(phoneLpr);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Set<Tag> getTags() {
        if(tags==null){
            tags=new HashSet();
        }
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    
    public String getFormattedPhoneLpr(){
        if(!phoneLpr.equals("")){
            if(phoneLpr.length()==7){
                String newStr = phoneLpr.substring(0, 3)+"-"+phoneLpr.substring(3, 5)+"-"+phoneLpr.substring(5, 7);
                return newStr;
            }
            if(phoneLpr.length()==11){
                String newStr = phoneLpr.substring(0, 1)+"("+phoneLpr.substring(1, 4)+")"+phoneLpr.substring(4, 7)+"-"+phoneLpr.substring(7, 9)+"-"+phoneLpr.substring(9, 11);
                return newStr;
            }
        }
        return phoneLpr;
    }
    
    public String getFormattedPhoneSec(){
        if(!phoneSecretary.equals("")){
            if(phoneSecretary.length()==7){
                String newStr = phoneSecretary.substring(0, 3)+"-"+phoneSecretary.substring(3, 5)+"-"+phoneSecretary.substring(5, 7);
                return newStr;
            }
            if(phoneSecretary.length()==11){
                String newStr = phoneSecretary.substring(0, 1)+"("+phoneSecretary.substring(1, 4)+")"+phoneSecretary.substring(4, 7)+"-"+phoneSecretary.substring(7, 9)+"-"+phoneSecretary.substring(9, 11);
                return newStr;
            }
        }
        return phoneSecretary;
    }

    /*public static String getPhone(Object ob) {
        ChainValidator ch = ChainValidator.getInstance(ValidatorTypes.PHONEFILTER);
        ch.execute(ob);
        return StringAdapter.getString(ch.getData());
    }*/
}
