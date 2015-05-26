/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.parent.PrimEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "directory_drain")
public class Drain extends PrimEntity {

    @Override
    public Long getId() {
        return drainId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_drain_id")
    private Long drainId;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "strategy_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

    @Column(name = "date_delete")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateDelete;

    public Long getDrainId() {
        return drainId;
    }

    public void setDrainId(Long drainId) {
        this.drainId = drainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public void setDateDelete(Date dateDelete) {
        this.dateDelete = dateDelete;
    }
    
}
