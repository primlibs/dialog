/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.EventDao;
import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import entities.CabinetUser;
import entities.Event;
import entities.PersonalCabinet;
import entities.Strategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import service.parent.PrimService;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EventService extends PrimService {

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private EventDao eventDao;

    public String numericName(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            List<Event> eventList = pk.getEventList();
            if (eventList != null) {
                return eventList.size() + 1 + "";
            } else {
                return "1";
            }
        } else {
            addError("не найден по " + cabinetId);
        }
        return "";

    }

    public List<Strategy> strategytList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return pk.getStrategyList();
        } else {
            addError("Стратегия не найден по id " + cabinetId);
        }
        return new ArrayList();
    }

    public List<Event> eventList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return pk.getEventList();
        } else {
            addError("Евент не найден по id " + cabinetId);
        }
        return new ArrayList();
    }

    public void eventAdd(String name, Long strategyId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Strategy strategy = strategyDao.find(strategyId);
        Date dt = new Date();
        if (pk != null) {
            if (strategyId != null) {
                if (name != null) {
                    Event event = new Event();
                    event.setCabinet(pk);
                    event.setName(name);
                    event.setStrategy(strategy);
                    event.setCreationDate(dt);
                    event.setStatus(Event.ACTIVE);
                    if (validate(event)) {
                        eventDao.save(event);
                    }
                } else {
                    addError("поле название эвента не может быть пустым");
                }
            } else {
                addError("не найдена стратегия по id" + strategyId);
            }
        } else {
            addError("не найден личный кабинет по id" + cabinetId);
        }

    }

    public List<CabinetUser> listRoleUserActiveCabinetUser(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<CabinetUser> listRoleUser = pk.getRoleUserActiveCabinetUserList();
        return listRoleUser;
    }

    public void getBook(File fl) throws FileNotFoundException, IOException{
        FileInputStream fis= new FileInputStream(fl);
        HSSFWorkbook  hss=new HSSFWorkbook(fis);
        
    }
       public HSSFWorkbook getXls() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
     
            int n = 0;
            HSSFSheet sheet = workbook.createSheet("Клиенты");
            HSSFRow rowhead = sheet.createRow((short) n);
            rowhead.createCell(0).setCellValue("Номер по порядку ");
            rowhead.createCell(1).setCellValue("Название компании");
            rowhead.createCell(2).setCellValue("Имя секретаря");
            rowhead.createCell(3).setCellValue("Имя лица принимающего решение");
            rowhead.createCell(4).setCellValue("Телефон секретаря");
            rowhead.createCell(5).setCellValue("Телефон лица принимающего решение ");
            rowhead.createCell(6).setCellValue("Адрес");
            rowhead.createCell(7).setCellValue("Коментарии");
            n++;
           
            HSSFSheet sheetLog = workbook.createSheet("LOG");
          //  HSSFRow rowwLog = sheetLog.createRow(1);
         //   rowwLog.createCell(0).setCellValue(eve.getResult());
          //  rowwLog.createCell(1).setCellValue(eve.getShortLog());
            return workbook;
        }
        
    }

