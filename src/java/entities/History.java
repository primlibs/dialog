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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Юрий
 */
@Entity
@Table(name = "history")
public class History extends PrimEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historiId;

    @JoinColumn(name = "user_dialog_id")
    @ManyToOne
    private UserDialog userDialog ;

    @JoinColumn(name = "text_script_id")
    @ManyToOne
    private TextScript textScript;

    public Long getHistoriId() {
        return historiId;
    }

    public void setHistoriId(Long historiId) {
        this.historiId = historiId;
    }

    public UserDialog getUserDialog() {
        return userDialog;
    }

    public void setUserDialog(UserDialog userDialog) {
        this.userDialog = userDialog;
    }

  

    public TextScript getTextScript() {
        return textScript;
    }

    public void setTextScript(TextScript textScript) {
        this.textScript = textScript;
    }

    @Override
    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
  

    
    
   

}
