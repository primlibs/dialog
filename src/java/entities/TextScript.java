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

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "text_scripts")
public class TextScript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_script_id")
    private Long textScriptId;

    @JoinColumn(name = "dialog_id")
    @ManyToOne
    private Dialog dialog;

    @Column(name = "text_operator")
    private String textOperator;

    @Column(name = "customer_reply")
    private String customerReply;

    @Column(name = "parent_id")
    private String parentId;

    public Long getTextScriptId() {
        return textScriptId;
    }

    public void setTextScriptId(Long textScriptId) {
        this.textScriptId = textScriptId;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

   
    

    public String getTextOperator() {
        return textOperator;
    }

    public void setTextOperator(String textOperator) {
        this.textOperator = textOperator;
    }

    public String getCustomerReply() {
        return customerReply;
    }

    public void setCustomerReply(String customerReply) {
        this.customerReply = customerReply;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
}
