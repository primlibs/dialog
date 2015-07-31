/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.ClientService;
import service.EventService;
import service.TagService;
import support.JsonResponse;

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
    private EventService eventService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/clientList")
    public String showClientList(Map<String, Object> model, HttpServletRequest request,@RequestParam(value = "uid", required = false) String uid,
            @RequestParam(value = "adress", required = false) String adress,@RequestParam(value = "nameCompany", required = false) String nameCompany,
            @RequestParam(value = "name", required = false) String name,@RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "tagIds", required = false) Long[] tagIds,@RequestParam(value = "tagCrossing", required = false) String tagCrossing) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        Boolean cross = false;
        if(tagCrossing!=null){
            cross=true;
        }
        model.put("clients",clientService.getClientsBySearchRequest(cabinetId,uid, adress, nameCompany, name, phone,cross,tagIds));
        model.put("tagMap",tagService.getAllActiveTagsMap(cabinetId));
        
        model.put("uid",uid);
        model.put("nameCompany",nameCompany);
        model.put("adress",adress);
        model.put("name",name);
        model.put("phone",phone);
        model.put("tagCrossing",tagCrossing);
        HashMap<Long,Long> selectedTagsMap=new HashMap();
        if(tagIds!=null){
            for(Long tagId:tagIds){
                selectedTagsMap.put(tagId,tagId);
            }
        }
        model.put("selectedTagsMap", selectedTagsMap);
        
        List<String> clientErrors = clientService.getErrors();
        if(model.get("errors")!=null){
            clientErrors.addAll((List<String>)model.get("errors"));
        }
        model.put("errors",clientErrors);
        return "clientList";
    }
    
    @RequestMapping("/getXls")
    public void getXls(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "uid", required = false) String uid,@RequestParam(value = "tagCrossing", required = false) String tagCrossing,
            @RequestParam(value = "adress", required = false) String adress,@RequestParam(value = "nameCompany", required = false) String nameCompany,
            @RequestParam(value = "name", required = false) String name,@RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "tags", required = false) Long[] tags) throws IOException, Exception {
        lk.dataByUserAndCompany(request, model);
        Boolean cross=false;
        if(tagCrossing!=null){
            cross=true;
        }
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        HSSFWorkbook workbook = clientService.getXls(cabinetId, uid, adress, nameCompany, name, phone,cross, tags);
        getXls(response, workbook);
    }
    
    public void getXls(HttpServletResponse response, HSSFWorkbook workbook) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=Clients.xls");
        workbook.write(response.getOutputStream());
    }
    
    @RequestMapping("/oneClient")
    public String showOneClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,@RequestParam(value = "eventId", required = false) Long eventId, HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("client",clientService.getClient(clientId));
        model.put("possibleTagsToAdd",tagService.getNotLinkedTags(clientId,cabinetId));
        model.put("unfinishedEvents",clientService.getUnfinishedEventsByClient(clientId));
        model.put("finishedEvents",clientService.getFinishedEventsByClient(clientId));
        //model.put("dialog",clientService.getHistory(eventId,cabinetId));
        model.put("messages", clientService.getHistory(eventId,cabinetId));
        model.put("dialogEvent",eventService.getEventById(eventId));
        
        List<String> clientErrors = clientService.getErrors();
        if(model.get("errors")!=null){
            clientErrors.addAll((List<String>)model.get("errors"));
        }
        model.put("errors",clientErrors);
        return "oneClient";
    }
    
    @RequestMapping("/addTag")
    public String addTagToClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,@RequestParam(value = "tags", required = false) Long[] tagIds,@RequestParam(value = "eventId", required = false) Long eventId, HttpServletRequest request,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        List<String> errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        if(tagIds!=null&&tagIds.length!=0){
            tagService.addTagsToClient(clientId, tagIds, cabinetId);
        }else{
            errors.add("Нужно выбрать хотябы один тэг");
        }
        errors.addAll(tagService.getErrors());
        
        model.put("errors",errors);
        ras.addFlashAttribute("errors",errors);
        ras.addFlashAttribute("message",tagService.getMessages());
        ras.addAttribute("eventId", eventId);
        ras.addAttribute("clientId", clientId);
        return "redirect:/Client/oneClient";
    }
    
    @RequestMapping("/deleteTag")
    public String deleteTagFromClient(Map<String, Object> model,@RequestParam(value = "clientId") Long clientId,
            @RequestParam(value = "tagId", required = false) Long tagId,
            @RequestParam(value = "eventId", required = false) Long eventId, 
            HttpServletRequest request,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        List<String> errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        tagService.deleteClientTag(clientId,tagId,cabinetId);
        errors.addAll(tagService.getErrors());
        
        ras.addFlashAttribute("errors",errors);
        ras.addAttribute("eventId", eventId);
        ras.addAttribute("clientId", clientId);
        return "redirect:/Client/oneClient";
    }
    
    @RequestMapping("/updateclient")
    @ResponseBody
    public JsonResponse updateClient(Map<String, Object> model,@RequestParam(value = "clientid") Long clientId,@RequestParam(value = "parametr") String parametr,
            @RequestParam(value = "newval") String newVal,HttpServletRequest request,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        JsonResponse res = JsonResponse.getInstance();
        
        clientService.updateClientField(parametr, clientId, null, newVal,cabinetId);
        if(clientService.getErrors().isEmpty()){
            res.setStatus(Boolean.TRUE);
        }else{
            String err = "";
            for(String s:clientService.getErrors()){
                err+=s+"; ";
            }
            res.setStatus(Boolean.FALSE);
            res.setMessage(err);
        }
        return res;
    }
    
    @RequestMapping("/delete")
    public String deleteClient(Map<String, Object> model,@RequestParam(value = "clientIdtoDelete") Long clientId,
            @RequestParam(value = "uid", required = false) String uid,
            @RequestParam(value = "adress", required = false) String adress,@RequestParam(value = "nameCompany", required = false) String nameCompany,
            @RequestParam(value = "name", required = false) String name,@RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "tagIds", required = false) Long[] tagIds,@RequestParam(value = "tagCrossing", required = false) String tagCrossing,
            HttpServletRequest request,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
     
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        List<String> errors = (List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        clientService.delete(clientId,cabinetId);
        errors.addAll(clientService.getErrors());
        
        HashMap<Long,Long> selectedTagsMap=new HashMap();
        if(tagIds!=null){
            for(Long tagId:tagIds){
                selectedTagsMap.put(tagId,tagId);
            }
        }
        ras.addAttribute("selectedTagsMap", selectedTagsMap);
        
        ras.addFlashAttribute("errors",errors);
        ras.addAttribute("clientId", clientId);
        ras.addAttribute("uid",uid);
        ras.addAttribute("nameCompany",nameCompany);
        ras.addAttribute("adress",adress);
        ras.addAttribute("name",name);
        ras.addAttribute("phone",phone);
        ras.addAttribute("tagCrossing",tagCrossing);
        return "redirect:/Client/clientList";
    }

}
