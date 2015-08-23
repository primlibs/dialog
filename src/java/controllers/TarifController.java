/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.parent.WebController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.TarifService;

/**
 *
 * @author bezdatiuzer
 */
@Controller
@RequestMapping("/Tarif")
public class TarifController extends WebController{
    
    @Autowired
    private LkController lk;
    
    @Autowired
    private TarifService tarifService;
    
    @RequestMapping(value = {"/list"})
    public String selectPersonalCabinetId(HttpServletRequest request,  Map<String, Object> model) throws Exception {
        lk.dataByUserAndCompany(request, model);
        if(isSuperAdmin()){
            model.put("tarifs", tarifService.getAllTarifs());
            return "tarif";
        }else{
            return "redirect:/";
        }
    }
    
    @RequestMapping("/create")
    public String createTarif(Map<String, Object> model,HttpServletRequest request,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Long price,
            @RequestParam(value = "days") Long days,
            @RequestParam(value = "users") Long users,
            @RequestParam(value = "campaigns") Long campaigns,
            @RequestParam(value = "clients") Long clients,
            RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        if(isSuperAdmin()){
            tarifService.create(name, price, days, users, campaigns, clients);
            if(!tarifService.getErrors().isEmpty()){
                ras.addFlashAttribute("errors", tarifService.getErrors());
                ras.addFlashAttribute("name", name);
                ras.addFlashAttribute("price", price);
                ras.addFlashAttribute("days", days);
                ras.addFlashAttribute("users", users);
                ras.addFlashAttribute("campaigns", campaigns);
                ras.addFlashAttribute("clients", clients);
            }
            return "redirect:/Tarif/list";
        }else{
            return "redirect:/";
        }
    }
    
    @RequestMapping("/delete")
    public String deleteTag(Map<String, Object> model,HttpServletRequest request,@RequestParam(value = "tarifIdToDelete") Long tarifId,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        if(isSuperAdmin()){
            tarifService.delete(tarifId);
            return "redirect:/Tarif/list";
        }else{
            return "redirect:/";
        }
    }
    
    @RequestMapping("/setDefault")
    public String setDefault(Map<String, Object> model,HttpServletRequest request,@RequestParam(value = "tarifId") Long tarifId,RedirectAttributes ras) throws Exception {
        lk.dataByUserAndCompany(request, model);
        if(isSuperAdmin()){
            tarifService.setDefault(tarifId);
            ras.addFlashAttribute(ERRORS_LIST_NAME, tarifService.getErrors());
            return "redirect:/Tarif/list";
        }else{
            return "redirect:/";
        }
    }
    
}
