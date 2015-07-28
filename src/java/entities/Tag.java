/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import entities.parent.PrimEntity;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author bezdatiuzer
 */
@Entity
@Table(name = "tag")
public class Tag extends PrimEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;
    
    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Необходимо указать ИД кабинета")
    @Index(name="cabinetIndex")
    private PersonalCabinet cabinet;
    
    @Column(name = "name",unique=true)
    @NotBlank(message = "Необходимо указать наименование тэга")
    private String name;
    
    @ManyToMany(mappedBy = "tags")
    /*@JoinTable(
            name = "client_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))*/
    private Set<Client> clients;
    
    @Column(name = "delete_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;
    
    @Override
    public Long getId() {
        return tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    
}
