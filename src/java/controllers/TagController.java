/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import static controllers.LkController.CABINET_ID_SESSION_NAME;
import controllers.parent.WebController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.TagService;

/**
 *
 * @author bezdatiuzer
 */
@RequestMapping("/Tag")
@Controller
public class TagController extends WebController {
    
    @Autowired
    private TagService tagService;
    
    @RequestMapping("/show")
    public String showTags(Map<String, Object> model,HttpServletRequest request) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        
        model.put("tags",tagService.getAllActiveTags(cabinetId));
        
        return "tags";
    }
    
    @RequestMapping("/create")
    public String createTag(Map<String, Object> model,HttpServletRequest request,@RequestParam(value = "name") String name,RedirectAttributes ras) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        tagService.create(name, cabinetId);
        ras.addFlashAttribute("errors", tagService.getErrors());
        ras.addFlashAttribute("name", name);
        return "redirect:/Tag/show";
    }
    
    @RequestMapping("/delete")
    public String deleteTag(Map<String, Object> model,HttpServletRequest request,@RequestParam(value = "tagId") Long tagId,@RequestParam(value = "deleteLinks",required = false) Boolean deleteLinks,RedirectAttributes ras) throws Exception {
        Long cabinetId = (Long) request.getSession().getAttribute(CABINET_ID_SESSION_NAME);
        if(deleteLinks==null){
            deleteLinks=false;
        }
        tagService.delete(tagId,deleteLinks);
        ras.addAttribute("errors", tagService.getErrors());
        
        return "redirect:/Tag/show";
    }
    
}
