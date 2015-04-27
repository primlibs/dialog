/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "event_client_link")
public class EventClientLink extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_client_link_id")
    private Long eventClientLinkId;

    @JoinColumn(name = "personal_cabinet_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonalCabinet cabinet;

    @JoinColumn(name = "event_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Event event;

    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Override
    public Long getId() {
        return eventClientLinkId;
    }

}
