/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import support.StringAdapter;

/**
 *
 * @author bezdatiuzer
 */
@Entity
@Table(name = "tarif")
public class Tarif extends PrimEntity {
    
    public static String NOTARIFNAME = "Не назначен";
    public static String DEFAULTMARK = "defaultMark";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarif_id")
    private Long tarifId;
    
    @Column(name = "name")
    @NotBlank(message = "Название тарифа не может быть пустым")
    private String name;
    
    @Column(name = "price")
    @NotNull(message = "Необходимо указать цену")
    private Long price;
    
    @Column(name = "day_length")
    private Long dayLength;
    
    @Column(name = "user_count")
    private Long userCount;
    
    @Column(name = "client_count")
    private Long clientCount;
    
    @Column(name = "campaign_count")
    private Long campaignCount;
    
    @Column(name = "default_mark")
    private String defaultMark;
    
    //+ограничения?права?
    
    @Override
    public Long getId() {
        return tarifId;
    }

    public Long getTarifId() {
        return tarifId;
    }

    public void setTarifId(Long tarifId) {
        this.tarifId = tarifId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDayLength() {
        return dayLength;
    }

    public void setDayLength(Long dayLength) {
        this.dayLength = dayLength;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getClientCount() {
        return clientCount;
    }

    public void setClientCount(Long clientCount) {
        this.clientCount = clientCount;
    }

    public Long getCampaignCount() {
        return campaignCount;
    }

    public void setCampaignCount(Long campaignCount) {
        this.campaignCount = campaignCount;
    }
    
    public String getData(){
        String data=this.name+" - "+this.price;
        String u = StringAdapter.getString(this.userCount);
        if(u.equals("")){
           u="не ограничено";
        }
        String camps = StringAdapter.getString(this.campaignCount);
        if(camps.equals("")){
           camps="не ограничено";
        }
        String cls = StringAdapter.getString(this.clientCount);
        if(cls.equals("")){
           cls="не ограничено";
        }
        String days = StringAdapter.getString(this.dayLength);
        if(days.equals("")){
           days="не ограничено";
        }
        return data+", дней:"+days+", польз.:"+u+", кампаний:"+camps+", клиентов:"+cls;
    }
    
    public boolean isDefault(){
        return DEFAULTMARK.equals(this.defaultMark);
    }
    
    public void setDefault(Boolean set){
        if(true==set){
            this.defaultMark=DEFAULTMARK;
        }else{
            this.defaultMark=null;
        }
    }
    
}
