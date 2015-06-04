/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.ClientService;
import service.TagService;

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
    @Autowired
    private TagService tagService;

    @RequestMapping("/clientList")
    public String showClientList(Map<String, Object> model, HttpServletRequest request,@RequestParam(value = "uid", required = false) String uid,
            @RequestParam(value = "adress", required = false) String adress,@RequestParam(value = "nameCompany", required = false) String nameCompany,
            @RequestParam(value = "name", required = false) String name,@RequestParam(value = "phone", required = false) Long phone,
            @RequestParam(value = "tags", required = false) Long[] tags) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("clients",clientService.getClientsBySearchRequest(cabinetId,uid, adress, nameCompany, name, phone,tags));
        model.put("tags",tagService.getAllActiveTags(cabinetId));
        
        model.put("uid",uid);
        model.put("nameCompany",nameCompany);
        model.put("adress",adress);
        model.put("name",name);
        model.put("phone",phone);
        
        List<String> clientErrors = clientService.getError();
        if(model.get("errors")!=null){
            clientErrors.addAll((List<String>)model.get("errors"));
        }
        model.put("errors",clientErrors);
        return "clientList";
    }
    
    @RequestMapping("/oneClient")
    public String showOneClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,@RequestParam(value = "eventId", required = false) Long eventId, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("client",clientService.getClient(clientId));
        model.put("possibleTagsToAdd",tagService.getNotLinkedTags(clientId,cabinetId));
        model.put("unfinishedEvents",clientService.getUnfinishedEventsByClient(clientId));
        model.put("finishedEvents",clientService.getFinishedEventsByClient(clientId));
        model.put("dialog",clientService.getHistory(eventId));
        
        List<String> clientErrors = clientService.getError();
        if(model.get("errors")!=null){
            clientErrors.addAll((List<String>)model.get("errors"));
        }
        model.put("errors",clientErrors);
        return "oneClient";
    }
    
    @RequestMapping("/addTag")
    public String addTagToClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,@RequestParam(value = "tags", required = false) Long[] tagIds,@RequestParam(value = "eventId", required = false) Long eventId, HttpServletRequest request,RedirectAttributes ras) throws Exception {
        List<String> errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        if(tagIds!=null&&tagIds.length!=0){
            tagService.addTagToClient(clientId, tagIds);
        }else{
            errors.add("Нужно выбрать хотябы один тэг");
        }
        errors.addAll(tagService.getError());
        
        model.put("errors",errors);
        ras.addFlashAttribute("errors",errors);
        ras.addAttribute("eventId", eventId);
        ras.addAttribute("clientId", clientId);
        return "redirect:/Client/oneClient";
    }
    
    @RequestMapping("/deleteTag")
    public String deleteTagFromClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,@RequestParam(value = "ctlId", required = false) Long ctlId,@RequestParam(value = "eventId", required = false) Long eventId, HttpServletRequest request,RedirectAttributes ras) throws Exception {
        List<String> errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        tagService.deleteClientTagLink(ctlId);
        errors.addAll(tagService.getError());
        
        model.put("errors",errors);
        ras.addFlashAttribute("errors",errors);
        ras.addAttribute("eventId", eventId);
        ras.addAttribute("clientId", clientId);
        return "redirect:/Client/oneClient";
    }

}
