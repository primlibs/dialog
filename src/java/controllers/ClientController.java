/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ClientService;

/**
 *
 * @author Юрий
 */
@Controller
@RequestMapping("/Client")
public class ClientController extends WebController {

    @Autowired
    private LkController lk;
    
    @Autowired
    private ClientService clientService;

    @RequestMapping("/clientList")
    public String showClientList(Map<String, Object> model, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("clients",clientService.getCabinetClients(cabinetId));
        List<String> clientErrors = clientService.getError();
        if(model.get("errors")!=null){
            clientErrors.addAll((List<String>)model.get("errors"));
        }
        model.put("errors",clientErrors);
        return "clientList";
    }
    
    @RequestMapping("/oneClient")
    public String showOneClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("client",clientService.getClient(clientId));
        List<String> clientErrors = clientService.getError();
        if(model.get("errors")!=null){
            clientErrors.addAll((List<String>)model.get("errors"));
        }
        model.put("errors",clientErrors);
        return "clientList";
    }

}
