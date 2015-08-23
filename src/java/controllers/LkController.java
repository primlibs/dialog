/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.parent.WebController;
import entities.CabinetUser;
import entities.PersonalCabinet;
import entities.Tarif;
import entities.User;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.AdminService;
import service.CabinetUserService;
import service.TarifService;
import support.DateAdapter;
import support.StringAdapter;

/**
 *
 * @author Юрий
 */
@Controller
@RequestMapping("/Lk")
public class LkController extends WebController{

    public static final String CABINET_ID_SESSION_NAME = "CabinetId";

    @Autowired
    private CabinetUserService service;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private TarifService tarifService;

    @Transactional
    @RequestMapping(value = {"/lk"})
    public String showLkPage(Map<String, Object> model, HttpServletRequest request) throws Exception {

        User user = authManager.getCurrentUser();
        dataByUserAndCompany(request, model);
        getRole(request, model);

        List<CabinetUser> list = service.getByUser(user);
        if (list.size() == 1) {
            CabinetUser cu = list.get(0);
            request.getSession().setAttribute(CABINET_ID_SESSION_NAME, cu.getCabinet().getPkId());
            request.getSession().setAttribute("role", service.getUserRole(user.getId(), cu.getCabinet().getPkId()));
            return "redirect:/";
        } else {
            model.put("list", list);
            return "lk";
        }
    }

    @RequestMapping(value = {"/selectLk"})
    public String selectPersonalCabinetId(HttpServletRequest request,  Map<String, Object> model,@RequestParam(value = "personalCabinetId") Long pkId) throws UnsupportedEncodingException {
        User user = authManager.getCurrentUser();
      
        String role = service.getUserRole(user.getId(), pkId);
        
        if(role!=null){
            request.getSession().setAttribute(CABINET_ID_SESSION_NAME, pkId);
            request.getSession().setAttribute("role", role);
        }else{
            model.put("errors","Пользователь не найден в этом личном кабинете");
            
        }
        return "redirect:/";
    }

    public void dataByUserAndCompany(HttpServletRequest request, Map<String, Object> model) throws Exception {
        
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        String superadmin = "";
        
        if(service.isSuperRole()){
            superadmin=User.SUPERADMIN;
        }
        
        String company = service.getNameCompany(cabinetId);
        String user = service.getNameUser();
        model.put("nameUser", user);
        model.put("nameCompany", company);
        model.put("superadmin", superadmin);
    }

    public void getRole(HttpServletRequest request, Map<String, Object> model) {
        Object role = request.getSession().getAttribute("role");
        model.put("role", role);

    }
    
    @RequestMapping("/cabinets")
    public String showPkList(Map<String, Object> model, HttpServletRequest request) throws Exception {
        dataByUserAndCompany(request, model);
        Object supermark = model.get("superadmin");
        List<String>errors=(List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        if(User.SUPERADMIN.equals(supermark)){
            model.put("pkList", adminService.getPkList());
            errors.addAll(adminService.getErrors());
            model.put("errors", errors);
            return "cabinets";
        }else{
            return "redirect:/";
        }
    }
    
    @RequestMapping("/administrating")
    public String administrating(Map<String, Object> model,
            @RequestParam(value = "pkId")Long pkId,
            RedirectAttributes ras,
            HttpServletRequest request) throws Exception {
        dataByUserAndCompany(request, model);
        Object supermark = model.get("superadmin");
        List<String>errors=(List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        if(User.SUPERADMIN.equals(supermark)){
            PersonalCabinet pk = adminService.getPk(pkId);
            errors.addAll(adminService.getErrors());
            if(pk!=null){
                String tarifInfo=tarifService.getTarifInfo(pkId);
                //String tarifInfo="Нет назначенного тарифа";
                /*Tarif t = pk.getTarif();
                if(t!=null){
                    String date = "не установлена";
                    if(pk.getEndDate()!=null){
                        date = DateAdapter.formatByDate(pk.getEndDate(), DateAdapter.SMALL_FORMAT);
                    }
                    String u = StringAdapter.getString(t.getUserCount());
                    if(u.equals("")){
                       u="не ограничено";
                    }
                    String camps = StringAdapter.getString(t.getCampaignCount());
                    if(camps.equals("")){
                       camps="не ограничено";
                    }
                    String cls = StringAdapter.getString(t.getClientCount());
                    if(cls.equals("")){
                       cls="не ограничено";
                    }
                    tarifInfo="Тариф: "+t.getName()+", дата окончания:"+date+", пользователей:"+u+", кампаний:"+camps+", клиентов:"+cls+".";
                }*/
                model.put("tarifInfo", tarifInfo);
                model.put("pk", pk);
                model.put("tarifs",tarifService.getAllTarifs());
                return "administrating";
            }else{
                errors.add("Личный кабинет с ид "+pkId+" не найден.");
                ras.addFlashAttribute("errors",errors);
                return "redirect:/Lk/cabinets";
            }
        }else{
            return "redirect:/";
        }
    }
    
    @RequestMapping("/setTarif")
    public String setTarif(Map<String, Object> model,
            RedirectAttributes ras,
            @RequestParam(value = "tarifId")Long tarifId,
            @RequestParam(value = "pkId")Long pkId,
            HttpServletRequest request) throws Exception {
        dataByUserAndCompany(request, model);
        Object supermark = model.get("superadmin");
        List<String>errors=(List<String>)model.get("errors");
        if(errors==null){
            errors=new ArrayList();
        }
        if(User.SUPERADMIN.equals(supermark)){
            adminService.setTarif(tarifId,pkId);
            errors.addAll(adminService.getErrors());
            ras.addAttribute("pkId",pkId);
            ras.addFlashAttribute("errors", errors);
            return "redirect:/Lk/administrating";
        }else{
            return "redirect:/";
        }
    }
    
}
