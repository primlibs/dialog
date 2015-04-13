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

/**
 *
 * @author Юрий
 */
@RequestMapping("/Strategy")
@Controller
public class StrategyController extends WebController {
    @Autowired
    private LkController lk;

    @RequestMapping("/show")
    public String showStrategyPage(Map<String, Object> model,HttpServletRequest request) throws Exception {
        lk.dataByUserAndCompany(request, model);
        return "strategy";
    }
}
