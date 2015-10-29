/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import entities.parent.PrimEntity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Index;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "campaign_observer")
public class CampaignObserver extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_observer_id")
    private Long campaignObserverId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name = "userIdIndex")
    private User user;

    @JoinColumn(name = "campaign_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name = "campaignIndex")
    private Campaign campaign;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Index(name = "cabinetIndex")
    private PersonalCabinet cabinet;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCampaignObserverId() {
        return campaignObserverId;
    }

    public void setCampaignObserverId(Long campaignObserverId) {
        this.campaignObserverId = campaignObserverId;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public PersonalCabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(PersonalCabinet cabinet) {
        this.cabinet = cabinet;
    }

    @Override
    public Long getId() {
        return getCampaignObserverId();
    }

}
