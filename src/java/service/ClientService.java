/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.EventDao;
import dao.ModuleDao;
import entities.Client;
import entities.Event;
import entities.EventComment;
import entities.ModuleEventClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
import support.DateAdapter;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ClientService extends PrimService {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private EventService eventService;

    public List<Client> getCabinetClients(Long pkId) {
        return clientDao.getCabinetClients(pkId);
    }

    public Client getClient(Long clientId) {
        return clientDao.find(clientId);
    }

    public List<Event> getFinishedEventsByClient(Long clientId) {
        return clientDao.getFinishedEventsByClient(clientId);
    }

    public List<Event> getUnfinishedEventsByClient(Long clientId) {
        return clientDao.getUnfinishedEventsByClient(clientId);
    }

    public HSSFWorkbook getXls(Long pkId, String uid, String adress, String nameCompany, String name, String phone,Boolean tagCrossing, Long[] tags) {
        List<Client> clientList = getClientsBySearchRequest(pkId, uid, adress, nameCompany, name, phone,tagCrossing, tags);

        HSSFWorkbook workbook = new HSSFWorkbook();
        int n = 0;
        HSSFSheet sheet = workbook.createSheet("Клиенты");
        HSSFRow rowhead = sheet.createRow((short) n);
        int r = 0;
        rowhead.createCell(r++).setCellValue("Номер уникальный");
        rowhead.createCell(r++).setCellValue("Название компании");
        rowhead.createCell(r++).setCellValue("Имя контактного лица");
        rowhead.createCell(r++).setCellValue("Телефон к.л.");
        rowhead.createCell(r++).setCellValue("Имя лица принимающего решение");
        rowhead.createCell(r++).setCellValue("Телефон л.п.р.");
        rowhead.createCell(r++).setCellValue("Адрес");
        rowhead.createCell(r++).setCellValue("Коментарии");
        n++;
        for (Client client : clientList) {
            HSSFRow row = sheet.createRow((short) n);
            r = 0;
            row.createCell(r++).setCellValue(client.getClientId());
            row.createCell(r++).setCellValue(client.getNameCompany());
            row.createCell(r++).setCellValue(client.getNameSecretary());
            row.createCell(r++).setCellValue(client.getPhoneSecretary());
            row.createCell(r++).setCellValue(client.getNameLpr());
            row.createCell(r++).setCellValue(client.getPhoneLpr());
            row.createCell(r++).setCellValue(client.getAddress());
            row.createCell(r++).setCellValue("");
            n++;
        }
        return workbook;
    }

    public List<Client> getClientsBySearchRequest(Long pkId, String uid, String adress, String nameCompany, String name, String phone,Boolean tagCrossing, Long[] tagIds) {
        if (adress != null && !adress.equals("")) {
            String[] ss = adress.trim().split(" ");
            adress = "";
            for (String s : ss) {
                adress += "%" + s + "%";
            }
        }
        if (name != null && !name.equals("")) {
            String[] ss = name.trim().split(" ");
            name = "";
            for (String s : ss) {
                name += "%" + s + "%";
            }
        }
        if (nameCompany != null && !nameCompany.equals("")) {
            String[] ss = nameCompany.trim().split(" ");
            nameCompany = "";
            for (String s : ss) {
                nameCompany += "%" + s + "%";
            }
        }
        if (phone != null && !phone.equals("")) {
            phone = phone.replaceAll("[^0-9]*", "");
            phone="%"+phone+"%";
        }
        return clientDao.getClientsBySearchRequest(pkId, uid, adress, nameCompany, name, phone,tagCrossing, tagIds);
    }

    //to do refactor
    public TreeMap<Date,String> getHistory(Long eventId,Long pkId) {
        TreeMap<Date,String>resMap = new TreeMap();
        if(eventId!=null&&pkId!=null){
            
            Event ev = eventDao.getEvent(eventId,pkId);
            for(EventComment ec:ev.getEventComments()){
                resMap.put(ec.getInsertDate(),"Системное сообщение: "+ec.getComment()+" (от "+DateAdapter.formatByDate(ec.getInsertDate(), DateAdapter.FULL_FORMAT)+")");
            }
            for(ModuleEventClient mec:ev.getModuleEventClientList()){
                Long time=mec.getInsertDate().getTime()-1;
                resMap.put(new Date(time),"Клиент: "+mec.getModule().getModuleName());
                resMap.put(mec.getInsertDate(),"Менеджер: "+mec.getModule().getBodyText());
            }
            //List<Module> dialog = moduleDao.getHistory(eventId);
        }else{
            /*if(eventId==null){
                addError("Ид звонка не получен");
            }*/
            if(pkId==null){
                addError("Ошибка личного кабинета");
            }
        }
        ArrayList<String> res = new ArrayList();
        res.addAll(resMap.values());
        return resMap;
    }

    public void updateClientField(String field, Long clientId, Long eventId, String newVal) {
        //boolean performed=false;
        Client client = clientDao.find(clientId);
        Event ev = null;
        boolean inEvent = false;
        if (eventId != null) {
            ev = eventDao.find(eventId);
        }
        if (client != null) {
            switch (field) {
                case "nameCompany":
                    client.setNameCompany(newVal);
                    break;
                case "address":
                    client.setAddress(newVal);
                    break;
                case "nameSecretary":
                    client.setNameSecretary(newVal);
                    break;
                case "nameLpr":
                    client.setNameLpr(newVal);
                    break;
                case "phoneSecretary":
                    client.setPhoneSecretary(newVal);
                    break;
                case "phoneLpr":
                    client.setPhoneLpr(newVal);
                    break;
                case "comment":
                    if(ev!=null){
                        ev.setComment(newVal);
                    }
                    inEvent = true;
                    break;
                default:
                    client = null;
                    break;
            }
        } else {
            addError("Клиент с ИД:" + clientId + " не найден");
        }
        if (inEvent) {
            if (ev != null) {
                if (validate(ev)) {
                    eventDao.update(ev);
                }
            } else {
                addError("Эвент не найден");
            }
        } else {
            if (client != null) {
                if (validate(client)) {
                    clientDao.update(client);
                }
            } else {
                addError("Клиент не найден");
            }
        }
    }
    
public void delete(Long clientId,Long pkId){
    if(clientId!=null&&pkId!=null){
        Client c = clientDao.find(clientId);
        for(Event ev:c.getEvents()){
            eventService.delete(ev);
        }
        c.setTags(new HashSet());
        clientDao.update(c);
        clientDao.delete(c);
    }else{
        if(clientId==null){
            addError("Ид клиента не был получен");
        }
        if(pkId==null){
            addError("Ошибка личного кабинета");
        }
    }
}

}
