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
@Table(name = "directory_drain")
public class DirectoryDrain extends PrimEntity {

    @Override
    public Long getId() {
        return directoryDrainId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_drain_id")
    private Long directoryDrainId;

    @Column(name = "name")
    private String directoryname;

    @JoinColumn(name = "strategy_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;
}
