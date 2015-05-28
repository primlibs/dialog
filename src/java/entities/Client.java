/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalCabinet cabinet;

    @Column(name = "name_secretary")
    private String nameSecretary;

    @Column(name = "name_lpr")
    private String nameLpr;

    @Column(name = "name_company")
    private String nameCompany;

    @Column(name = "phone_secretary")
    private Long phoneSecretary;

    @Column(name = "phone_lpr")
    private Long phoneLpr;

    @Column(name = "address")
    private String address;

    @Column(name = "comment")
    private String comment;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "client")
    private List<Event> events;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "client")
    private List<ClientTagLink> tagLinks;

    @Column(name = "unique_id")
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

    public Long getPhoneSecretary() {
        return phoneSecretary;
    }

    public void setPhoneSecretary(Long phoneSecretary) {
        this.phoneSecretary = phoneSecretary;
    }

    public Long getPhoneLpr() {
        return phoneLpr;
    }

    public void setPhoneLpr(Long phoneLpr) {
        this.phoneLpr = phoneLpr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public List<ClientTagLink> getTagLinks() {
        return tagLinks;
    }

    public void setTagLinks(List<ClientTagLink> tagLinks) {
        this.tagLinks = tagLinks;
    }
}
