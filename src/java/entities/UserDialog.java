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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "user_dialog")
public class UserDialog extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_dialog_id")
    private Long userDialogId;

    @JoinColumn(name = "dialog_id")
    @ManyToOne
    private Dialog dialog;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private Clients client;

    public Long getUserDialogId() {
        return userDialogId;
    }

    public void setUserDialogId(Long userDialogId) {
        this.userDialogId = userDialogId;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    @Override
    public Long getId() {
        return userDialogId;
    }

    

}
