/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import service.UserService;

/**
 *
 * @author Юрий
 */
public class SendMail {

    @Autowired
    private UserService userService;

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public void mailSend(String email ) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText("Вы востонавливаите пароль от CallAssistent + ссылка на контроллер + параметр.хеш");

        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            userService.addError(ex.toString());
        }
    }
}
