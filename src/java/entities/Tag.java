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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import static support.StringAdapter.NotNull;

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
    
    @Column(name = "personal_cabinet_id")
    @NotNull(message = "Необходимо указать ИД кабинета")
    private PersonalCabinet pk;
    
    @Column(name = "name")
    @NotBlank(message = "Необходимо указать наименование тэга")
    private String name;
    
    @Column(name = "delete_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date deleteDate;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "tag")
    private List<ClientTagLink> clientLinks;
    
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

    public List<ClientTagLink> getClientLinks() {
        return clientLinks;
    }

    public void setClientLinks(List<ClientTagLink> clientLinks) {
        this.clientLinks = clientLinks;
    }

    public PersonalCabinet getPk() {
        return pk;
    }

    public void setPk(PersonalCabinet pk) {
        this.pk = pk;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
    
    
    
}
