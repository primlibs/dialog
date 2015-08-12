/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CabinetUserDao;
import dao.ClientDao;
import dao.EventDao;
import dao.CampaignDao;
import dao.EventCommentDao;
import dao.FailReasonDao;
import dao.ModuleDao;
import dao.ModuleEventClientDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import dao.UserDao;
import entities.CabinetUser;
import entities.Client;
import entities.Campaign;
import entities.FailReason;
import entities.Event;
import entities.EventComment;
import entities.Module;
import entities.ModuleEventClient;
import entities.PersonalCabinet;
import entities.Strategy;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.parent.PrimService;
import support.DateAdapter;
import support.StringAdapter;
import static support.StringAdapter.getString;
import support.editors.PhoneEditor;

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
    private UserDao userDao;

    @Autowired
    private CabinetUserDao cabinetUserDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private CampaignDao campaignDao;

    @Autowired
    private FailReasonDao failReasonDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventCommentDao eventCommentDao;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ModuleEventClientDao moduleEventClientDao;

    public String numericName(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            List<Campaign> eventList = pk.getEventList();
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

    public List<Strategy> getStrategies(Long cabinetId) {
        //fail
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return pk.getStrategyList();
        } else {
            addError("Сценарий не найден по id " + cabinetId);
        }
        return new ArrayList();
    }

    public LinkedHashMap<Campaign, HashMap<String, String>> getCampaignsWithCountInfos(/*Date dateFrom,Date dateTo,Boolean closed,*/ Long pkId) {
        LinkedHashMap<Campaign, HashMap<String, String>> res = new LinkedHashMap();
        LinkedHashMap<Long, HashMap<String, String>> countMap = eventDao.getFinishedAndUnassignedEventCountsInCampaignsAsMap(/*dateFrom,dateTo,closed,*/pkId);
        for (Campaign c : campaignDao.getAllCampaigns(pkId)) {
            HashMap<String, String> InfoMap = countMap.get(c.getId());
            if (InfoMap == null) {
                InfoMap = new HashMap();
                InfoMap.put("finishedCount", "0");
                InfoMap.put("unassignedCount", "0");
            }
            res.put(c, InfoMap);
        }
        return res;
    }

    public void createCampaign(String name, Long strategyId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Strategy strategy = strategyDao.find(strategyId);
        Date dt = new Date();
        if (pk != null) {
            if (strategyId != null) {
                if (name != null) {
                    Campaign campaign = new Campaign();
                    campaign.setCabinet(pk);
                    campaign.setName(name);
                    campaign.setStrategy(strategy);
                    campaign.setCreationDate(dt);
                    campaign.setStatus(Campaign.ACTIVE);
                    if (validate(campaign)) {
                        campaignDao.save(campaign);
                    }
                } else {
                    addError("наименование кампании не может быть пустым");
                }
            } else {
                addError("не найдена сценарий по id" + strategyId);
            }
        } else {
            addError("не найден личный кабинет по id" + cabinetId);
        }
    }
    
    public void delete(Event ev){
        for(ModuleEventClient mec:ev.getModuleEventClientList()){
            moduleEventClientDao.delete(mec);
        }
        for(EventComment ec:ev.getEventComments()){
            eventCommentDao.delete(ec);
        }
        eventDao.delete(ev);
    }

    public void deleteCampaign(Long campaignId, Long cabinetId) {
        Campaign c = campaignDao.find(campaignId);
        if (c != null) {
            //if (eventDao.getAssignedEvents(campaignId, cabinetId).isEmpty()) {
                List<Event> events = c.getEvents();
                for (Event ev : events) {
                    delete(ev);
                }
                //if(c.getEvents().isEmpty()){
                campaignDao.delete(c);
            /*} else {
                addError("В кампании присутствуют назначения, её нельзя удалять.");
            }*/
        } else {
            addError("Не найдено кампании с ИД " + campaignId + "; ");
        }
    }

    public List<CabinetUser> getActiveMakingCallsUsers(Long cabinetId) {
        return cabinetUserDao.getMakingCallsCabUsers(cabinetId);
    }

    public boolean isDeleteble(Long campaignId, Long cabinetId) {
        return eventDao.getAssignedEventsCount(campaignId, cabinetId) <= 0;
    }

    public HSSFWorkbook getXls() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        int n = 0;
        HSSFSheet sheet = workbook.createSheet("Клиенты");
        HSSFRow rowhead = sheet.createRow((short) n);
        int r = 0;
        rowhead.createCell(r++).setCellValue("Номер уникальный");
        rowhead.createCell(r++).setCellValue("Клиент");
        
        rowhead.createCell(r++).setCellValue("Телефон");
        rowhead.createCell(r++).setCellValue("Коментарий");
        
        /*rowhead.createCell(r++).setCellValue("Имя контактного лица");
        rowhead.createCell(r++).setCellValue("Имя лица принимающего решение");
        rowhead.createCell(r++).setCellValue("Телефон л.п.р.");
        rowhead.createCell(r++).setCellValue("Адрес");*/
        n++;
        return workbook;
    }

    public HashMap<String, Client> getClientsMapInCampaign(Long campaignId, Long pkId) {
        HashMap<String, Client> addedInCampaignClientsMap = new HashMap();
        Campaign campaign = campaignDao.find(campaignId);
        for (Event ev : campaign.getEvents()) {
            addedInCampaignClientsMap.put(ev.getClient().getUniqueId(), ev.getClient());
        }
        return addedInCampaignClientsMap;
    }

    public HashMap<String, Client> getClientsMapInPk(Long pkId) {
        HashMap<String, Client> res = new HashMap();
        for (Client c : clientDao.getCabinetClients(pkId)) {
            res.put(c.getUniqueId(), c);
        }
        return res;
    }

    public void readXls(MultipartFile fileXls, Long[] tagIds, Long pkId, Long campaignId, Boolean update) throws Exception {
        Campaign campaign = campaignDao.find(campaignId);
        //List<String> uniqs = campaignDao.getUniqs(campaignId, pkId);
        if (!Objects.equals(campaign.getStatus(), Campaign.CLOSE)) {
            PersonalCabinet pk = personalCabinetDao.find(pkId);
            HashMap<String, String> commentMap = new HashMap();
            List<Client> clientsListForSave = new ArrayList();
            List<Client> clientsListForUpdate = new ArrayList();
            List<Event> eventsListForSave = new ArrayList();
            HashMap<String, Client> addedInPkClientsMap = getClientsMapInPk(pkId);
            HashMap<String, Client> addedInCampaignClientsMap = getClientsMapInCampaign(campaignId, pkId);
            //HashMap<String, Client> addingClientsMapForSave = new HashMap();
            List<Client> noContactList = new ArrayList();
            List<Integer> noUniqueIdList = new ArrayList();
            InputStream fis = fileXls.getInputStream();
            HSSFWorkbook inputWorkbook = new HSSFWorkbook(fis);
            int sheetCount = inputWorkbook.getNumberOfSheets();
            for (int i = 0; i < sheetCount; i++) {
                HSSFSheet hss = inputWorkbook.getSheetAt(i);
                int rowCount = 0;
                Iterator<Row> it = hss.iterator();
                while (it.hasNext()) {
                    rowCount++;
                    Row rw = it.next();
                    if (!(StringAdapter.getString(rw.getCell(0))).trim().equals("Номер уникальный")) {
                        String uid = StringAdapter.HSSFSellValue(rw.getCell(0));
                        String name = StringAdapter.HSSFSellValue(rw.getCell(1));
                        String secretaryPhone = HSSFPhoneValue(rw.getCell(2));
                        String comment = StringAdapter.HSSFSellValue(rw.getCell(3));
                        String contface=StringAdapter.HSSFSellValue(rw.getCell(4));
                        String lprPhone = HSSFPhoneValue(rw.getCell(5));
                        String namelpr=StringAdapter.HSSFSellValue(rw.getCell(6));
                        String adress=StringAdapter.HSSFSellValue(rw.getCell(7));
                        if (uid.equals("")&&(!name.equals("")||!secretaryPhone.equals("")||!lprPhone.equals(""))) {
                            noUniqueIdList.add(rowCount);
                        } else if(!uid.equals("")&&!name.equals("")&&(!secretaryPhone.equals("")||!lprPhone.equals(""))) {
                            //клиентов нет еще
                            if (!addedInPkClientsMap.keySet().contains(uid)) {
                                Client cl = new Client();
                                cl.setUniqueId(uid);
                                cl.setNameCompany(name);
                                cl.setNameSecretary(contface);
                                cl.setNameLpr(namelpr);
                                cl.setPhoneSecretary(secretaryPhone);
                                cl.setPhoneLpr(lprPhone);
                                cl.setAddress(adress);
                                commentMap.put(uid, comment);
                                cl.setCabinet(pk);
                                if (validate(cl)) {
                                    if ((secretaryPhone != null && !secretaryPhone.equals("")) || (lprPhone != null && !lprPhone.equals(""))) {
                                        clientsListForSave.add(cl);
                                    } else {
                                        noContactList.add(cl);
                                    }
                                }
                            } else {
                                Client cl = addedInPkClientsMap.get(uid);
                                if (update) {
                                    cl.setUniqueId(uid);
                                    cl.setNameCompany(name);
                                    cl.setNameSecretary(contface);
                                    cl.setNameLpr(namelpr);
                                    cl.setPhoneSecretary(secretaryPhone);
                                    cl.setPhoneLpr(lprPhone);
                                    cl.setAddress(adress);
                                    commentMap.put(uid, comment);
                                    cl.setCabinet(pk);
                                    if (validate(cl)) {
                                        if ((secretaryPhone != null && !secretaryPhone.equals("")) || (lprPhone != null && !lprPhone.equals(""))) {
                                            clientsListForUpdate.add(cl);
                                        } else {
                                            noContactList.add(cl);
                                        }
                                    }
                                }
                                //клиенты есть, но не в кампании
                                if (!addedInCampaignClientsMap.keySet().contains(uid)) {
                                    Event event = new Event();
                                    event.setCabinet(pk);
                                    event.setClient(cl);
                                    event.setUniqueId(uid);
                                    event.setCampaign(campaign);
                                    event.setComment(StringAdapter.getString(commentMap.get(cl.getUniqueId())));
                                    event.setStatus(Event.UNASSIGNED);
                                    if (validate(event)) {
                                        eventsListForSave.add(event);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (noContactList.isEmpty() && noUniqueIdList.isEmpty()) {
                for (Client cl : clientsListForSave) {
                    clientDao.save(cl);
                    if (tagIds != null && tagIds.length > 0) {
                        tagService.addTagsToClient(cl.getId(), tagIds, pkId);
                    }
                    Event event = new Event();
                    event.setCabinet(pk);
                    event.setClient(cl);
                    event.setUniqueId(cl.getUniqueId());
                    event.setCampaign(campaign);
                    event.setComment(StringAdapter.getString(commentMap.get(cl.getUniqueId())));
                    event.setStatus(Event.UNASSIGNED);
                    if (validate(event)) {
                        eventsListForSave.add(event);
                    }
                }
                for (Client cl : clientsListForUpdate) {
                    clientDao.update(cl);
                    if (tagIds != null && tagIds.length > 0) {
                        tagService.addTagsToClient(cl.getId(), tagIds, pkId);
                    }
                }
                for (Event ev : eventsListForSave) {
                    eventDao.save(ev);
                    addEventComment("Звонок добавлен", EventComment.CREATE, ev, pkId);
                    addEventComment("Комментарий: "+ev.getComment(), EventComment.COMMENTED, ev, pkId);
                }
            } else {
                if (!noContactList.isEmpty()) {
                    String err = "Не указаны контакты клиентов с УИД: ";
                    for (Client cl : noContactList) {
                        err += cl.getUniqueId() + "; ";
                    }
                    addError(err + " для загрузки клиентов необходимо указать хотя бы один контакт.");
                }
                if (!noUniqueIdList.isEmpty()) {
                    String err = "Не указан УИД клиентов в следующих строках: ";
                    for (Integer rc : noUniqueIdList) {
                        err += rc + "; ";
                    }
                    addError(err);
                }
            }

        } else {
            addError("Нельзя добавить клиентов в закрытую кампанию.");
        }
    }

    public String HSSFPhoneValue(Cell cl) {
        if (cl == null) {
            return "";
        }
        PhoneEditor phe = new PhoneEditor();
        int cellType = cl.getCellType();
        String res = "";
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                res = phe.getPhone(cl.getStringCellValue());
                addError(phe.error);
                break;
            case Cell.CELL_TYPE_NUMERIC:
                res = phe.getPhone(getString(BigDecimal.valueOf(cl.getNumericCellValue()).longValue()));
                addError(phe.error);
                break;
            case Cell.CELL_TYPE_FORMULA:
                res = phe.getPhone(getString(BigDecimal.valueOf(cl.getNumericCellValue()).longValue()));
                addError(phe.error);
                break;
            default:
                break;
        }
        return res;
    }

    public Campaign getCampaign(Long campaignId) {
        if (campaignId == null) {
            return null;
        }
        return campaignDao.find(campaignId);
    }

    public List<Event> getEventList(Long campaignId, Long cabinetId) {
        List<Event> events = eventDao.getEventListByCampaignId(campaignId, cabinetId);
        return events;
    }

// получить лист ссылок НЕ назначиных клиентов
    public List<Event> getUnassignedEvent(Long campaignId, Long cabinetId) {
        List<Event> events = eventDao.getUnassignedEvent(campaignId, cabinetId);
        return events;
    }

// получить ист клиентов
    public List<Client> getClientList(Long campaignId, Long pkId) {
        List<Client> clList = clientDao.getClientsByCampaign(campaignId, pkId);
        return clList;

    }

// получить лист клиентов не назначеных
    public List<Client> getNotAssignedClients(Long campaignId, Long cabinetId) {
        return clientDao.getNotAssignedClientsByCampaign(cabinetId, campaignId);
    }

    public void eventAppointSave(String[] arrayClientIdUserId, Long cabinetId, Long campaignId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Campaign campaign = campaignDao.find(campaignId);

        for (int i = 0; i < arrayClientIdUserId.length; i++) {
            //   String clientIdUserId = arrayClientIdUserId[i];
            //  String[] dfg = clientIdUserId.split("_");
            String str = arrayClientIdUserId[i];
            if (str != null && !str.isEmpty()) {
                String[] clientIdUserId = str.split("_");
                Long clientId = Long.valueOf(clientIdUserId[0]);
                Long userId = Long.valueOf(clientIdUserId[1]);
                Client client = clientDao.find(clientId);
                User user = userDao.find(userId);
                Event event = eventDao.getEvent(client, pk, campaign);
                event.setUser(user);
                event.setStatus(Event.ASSIGNED);
                if (validate(event)) {
                    eventDao.save(event);
                    addEventComment("Звонок назначен на " + user.getShortName()+"("+user.getEmail()+")", EventComment.ASSIGN, event, cabinetId);
                }
            }
        }
    }

    //распределить всех клиентов по юзерам
    public LinkedHashMap<Long, Integer> eventAppointAll(Long campaignId, Long cabinetId) {
        int clientNotAssigned = getNotAssignedClients(campaignId, cabinetId).size();
        int users = getActiveMakingCallsUsers(cabinetId).size();
        List<CabinetUser> cabinetUserList = getActiveMakingCallsUsers(cabinetId);
        if (users != 0) {
            int clientOneUser = clientNotAssigned / users; //деление
            int endClientUser = clientNotAssigned % users; // остаток

            LinkedHashMap<Long, Integer> residueMap = new LinkedHashMap<>();

            for (CabinetUser cabinetUser : cabinetUserList) {
                Long userId = cabinetUser.getUser().getUserId();
                int eventPoint = clientOneUser;
                if (endClientUser > 0) {
                    eventPoint += 1;
                    endClientUser--;
                }
                residueMap.put(userId, eventPoint);
            }
            return residueMap;
        } else {
            addError("Нет доступных пользователей");
            return null;
        }
    }

//сохранение распределения
    public void eventAppointSaveAll(Long campaignId, Long cabinetId, Long[] userIdArray, String[] clientNumArray) {
        if (userIdArray != null && clientNumArray != null) {
            LinkedHashMap<Long, Integer> userIdCountAssignedMap = new LinkedHashMap();
            List<Event> events = getUnassignedEvent(campaignId, cabinetId);
            List<Event> eventsForUpdate = new ArrayList();
            PersonalCabinet pk = personalCabinetDao.find(cabinetId);
            //int clientCount = 0;
            int summClient = 0;
            if (userIdArray.length > 0 && events.size() > 0 && clientNumArray.length > 0) {
                for (int i = 0; i < userIdArray.length; i++) {
                    if (clientNumArray.length >= i) {
                        int count = StringAdapter.toInteger(clientNumArray[i]);
                        summClient += count;
                        userIdCountAssignedMap.put(userIdArray[i], count);
                    } else {
                        userIdCountAssignedMap.put(userIdArray[i], 0);
                    }
                }

                ///получить количество нераспределенных, сравить с количеством поданных - если поданных больше - ошибка
                //в цикле для каждогоо элемента appointMap распределить на пользователя пока 
                //остаются заявки вывести объект который сообщит сколько на кого было распределено
                /*for (int i = 0; i < clientNumArray.length; i++) {
                 String df = clientNumArray[i];
                 if(df.equals("")||!df.matches("[0-9]*")){
                 df="0";
                 }
                 Integer i2 = Integer.valueOf(df);
                 summClient += i2;
                 }*/
                //Long eclId = (long)0;
                int sindx = 0;
                if (summClient <= events.size()) {
                    for (Long userId : userIdCountAssignedMap.keySet()) {
                        Integer eventsCountToAssign = userIdCountAssignedMap.get(userId);
                        User user = userDao.getUserBelongsPk(pk, userId);
                        if (user != null) {
                            //int supCount = 0;
                            for (int supCount = 0; supCount < eventsCountToAssign; supCount++) {
                                Event ev = events.get(sindx);
                                if (ev != null && supCount < eventsCountToAssign) {
                                    ev.setUser(user);
                                    ev.setStatus(Event.ASSIGNED);
                                    if (validate(ev)) {
                                        eventsForUpdate.add(ev);
                                        //supCount++;
                                        sindx++;
                                    }
                                }
                            }

                            /*for (Event ev : events) {
                             if (supCount < eventsCountToAssign && eclId < ev.getEventId()) {
                             ev.setUser(user);
                             ev.setStatus(Event.ASSIGNED);
                             if (validate(ev)) {
                             eventDao.save(ev);
                             eclId = ev.getEventId();
                             }
                             supCount++;
                             }
                             }*/
                        } else {
                            addError("Ошибка! Пользователь id:" + userId + " не принадлежит к личному кабинету!");
                        }
                    }
                    for (Event ev : eventsForUpdate) {
                        eventDao.update(ev);
                        User u = ev.getUser();
                        addEventComment("Звонок назначен на " + u.getShortName() + "("+u.getEmail()+")", EventComment.ASSIGN, ev, cabinetId);
                    }
                    /*String str = "";
                     for(Map.Entry<Long,Integer> entry:userIdCountAssignedMap.entrySet()){
                     str+=entry.getKey()+"-"+entry.getValue()+"; ";
                     }
                     addError(str);*/
                } else {
                    addError("Количество клиентов " + summClient + " больше количества не назначенных: " + events.size());
                }
            }
        }
    }

    public HashMap<Long, String> userAssignedClient(Long campaignId, Long cabinetId) {
        HashMap<Long, String> userAssignedClient = getUserCountMap(cabinetId);
        for (Object[] ecl : eventDao.getUserAssignedClient(campaignId, cabinetId)) {
            userAssignedClient.put(StringAdapter.toLong(ecl[1]), getStringNumber(ecl[0]));
        }
        return userAssignedClient;
    }

    public List<Event> getEventFilter(Long campaignId, Long cabinetId, Integer assigned, Integer processed) {
        if (assigned == null) {
            assigned = 0;
        }
        if (processed == null) {
            processed = 0;
        }

        int i = assigned;
        Long userId = (long) (i);

        if (assigned == 0 && processed == 0) {
            List<Event> events = eventDao.getEventListByCampaignId(campaignId, cabinetId);//лист ссылок по евенту и личному кабинету
            return events;
        }
        if (assigned == -1) {
            List<Event> events = eventDao.getUnassignedEvent(campaignId, cabinetId);//лист  ссылок Не НАЗНАЧЕННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == -2 && processed == 0) {
            List<Event> events = eventDao.getAssignedEvents(campaignId, cabinetId);//лист  ссылок  НАЗНАЧЕННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == -2 && processed == -1) {
            List<Event> events = eventDao.getAssignedNotClosedEvents(campaignId, cabinetId);//лист ссылок НАЗНАЧЕННЫХ, НЕ ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == -2 && processed == -2) {
            List<Event> events = eventDao.getAssignedEventSuccess(campaignId, cabinetId);//лист ссылок НАЗНАЧЕННЫХ, УСПЕШНО ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == -2 && processed == -3) {
            List<Event> events = eventDao.getAssignedEventNotSuccess(campaignId, cabinetId);//лист ссылок НАЗНАЧЕННЫХ, НЕ УСПЕШНО ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == -2 && processed == -4) {
            List<Event> events = eventDao.getAssignedEventProcessed(campaignId, cabinetId);//лист ссылок НАЗНАЧЕННЫХ, ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == 0 && processed == -1) {
            List<Event> events = eventDao.getEventListNotProcessed(campaignId, cabinetId);//лист ссылок НЕ ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == 0 && processed == -2) {
            List<Event> events = eventDao.getEventLisSuccess(campaignId, cabinetId);//лист ссылок УСПЕШНО ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == 0 && processed == -3) {
            List<Event> events = eventDao.getEventLisNotSuccess(campaignId, cabinetId);//лист ссылок НЕ УСПЕШНО ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == 0 && processed == -4) {
            List<Event> events = eventDao.getEventListProcessed(campaignId, cabinetId);//лист ссылок ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned > 0 && processed == 0) {
            List<Event> events = eventDao.getEventsByUserId(campaignId,userId,cabinetId);//лист ссылок  по userId, по евенту и личному кабинету
            return events;
        }
        if (assigned > 0 && processed == -1) {
            List<Event> events = eventDao.getNotProcessedEventsByUserIdAndCampaignId(campaignId, cabinetId, userId);//лист ссылок по userId, НЕ ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned > 0 && processed == -2) {
            List<Event> events = eventDao.getSuccessEventsByUserId(campaignId, cabinetId, userId);//лист ссылок по userId, УСПЕШНО ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned > 0 && processed == -3) {
            List<Event> events = eventDao.getFailedEventsByUserId(campaignId, cabinetId, userId);//лист ссылок по userId, НЕ УСПЕШНО ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned > 0 && processed == -4) {
            List<Event> events = eventDao.getProcessedEventsByUserId(campaignId, cabinetId, userId);//лист ссылок по userId, ОБРАБОТАННЫХ по евенту и личному кабинету
            return events;
        }

        return null;

    }
    
    

    //клиенты назначение юзерам не обработанные
    public HashMap<Long, String> userAssignedClientNotProcessed(Long campaignId, Long cabinetId) {
        //List<CabinetUser> cusers = getSurnameSortedCUListForCampaignSpecification(campaignId,cabinetId);
        HashMap<Long, String> userAssignedClientNotProcessed = getUserCountMap(cabinetId);
        for (Object[] ecl : eventDao.getAssignedNotProcessedClientsByUserId(campaignId, cabinetId)) {
            userAssignedClientNotProcessed.put(StringAdapter.toLong(ecl[1]), getStringNumber(ecl[0]));
        }
        return userAssignedClientNotProcessed;
    }

    //клиенты назначение юзерам обработанные
    public HashMap<Long, String> userAssignedClientProcessed(Long campaignId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientProcessed = getUserCountMap(cabinetId);
        for (Object[] ecl : eventDao.getAssignedProcessedClientsByUserId(campaignId, cabinetId)) {
            userAssignedClientProcessed.put(StringAdapter.toLong(ecl[1]), getStringNumber(ecl[0]));
        }
        return userAssignedClientProcessed;
    }

    //клиенты назначение юзерам обработанные Успешно
    public HashMap<Long, String> userAssignedClientProcessedSuccess(Long campaignId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientProcessedSuccess = getUserCountMap(cabinetId);
        for (Object[] ecl : eventDao.getUserIdListWithCountedAssignedProcessedSuccessClients(campaignId, cabinetId)) {
            userAssignedClientProcessedSuccess.put(StringAdapter.toLong(ecl[1]), getStringNumber(ecl[0]));
        }
        return userAssignedClientProcessedSuccess;
    }

    //клиенты назначение юзерам обработанные НЕ успешно
    public HashMap<Long, String> userAssignedClientProcessedFails(Long campaignId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientProcessedFails = getUserCountMap(cabinetId);
        for (Object[] ecl : eventDao.getUserIdListWithCountedAssignedProcessedFailedClients(campaignId, cabinetId)) {
            userAssignedClientProcessedFails.put(StringAdapter.toLong(ecl[1]), getStringNumber(ecl[0]));
        }
        return userAssignedClientProcessedFails;
    }

    public LinkedHashMap<Campaign, String> userShowPageEventClientList(Long cabinetId, Long userId) {
        LinkedHashMap<Campaign, String> result = new LinkedHashMap();
        for (Object[] ecl : eventDao.getCampaignByCabinetAndUserId(cabinetId, userId)) {
            result.put((Campaign) ecl[0], StringAdapter.getString(ecl[1]));
        }
        return result;
    }

    private String getStringNumber(Object ob) {
        String count = "0";
        if (ob != null && !ob.equals("") && !ob.equals("null")) {
            count = StringAdapter.getString(ob);
        }
        return count;
    }

    private HashMap<Long, String> getUserCountMap(Long cabinetId) {
        HashMap<Long, String> userMap = new HashMap();
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<CabinetUser> CUsers = pk.getCabinetUserList();
        for (CabinetUser cuser : CUsers) {
            userMap.put(cuser.getUser().getId(), "0");
        }
        return userMap;
    }

    //лист Ссылкок event по campaignId НЕ ОБРАБОТАНЫХ по userId
    public Event getEventByUserAndCampaign(Long campaignId, Long cabinetId, Long userId) {
        List<Event> events = eventDao.getEventListByUserByCampaign(campaignId, cabinetId, userId);
        //List<Event> pevents = eventDao.getPostponedEvents(campaignId, cabinetId, userId);
        /*String err = "";
         int i=0;
         for(Event ev:events){
         String date = "no";
         i++;
         if(ev.getPostponedDate()!=null){
         date=ev.getPostponedDate().toString();
         }
         addError(i+"client:"+ev.getClient().getNameCompany()+"; ppd:"+date+"; user="+ev.getUser().getEmail()+"; ");
         }*/
        if (events.isEmpty()) {
            List<Event> pevents = getPostponedEvents(userId, cabinetId);
            if (pevents.isEmpty()) {
                addMessage("Список назначенных Вам клиентов пуст!");
                return null;
            } else {
                Event pev = pevents.get(0);
                addMessage("Список назначенных Вам необработанных клиентов пуст! Ближайший запланированный контакт в рамках кампании: " + pev.getClient().getNameCompany() + " - " + pev.getPostponedDate() + ". ");
                return null;
            }
        } else {
            Event pevent = events.get(0);
            if (pevent.getPostponedDate() != null) {
                return pevent;
            }
        }

        java.util.Random rng = new java.util.Random();
        Event ev = events.get(rng.nextInt(events.size()));
        return ev;
    }

    public Long getStrategyId(Long campaignId) {
        Campaign cam = campaignDao.find(campaignId);
        if (cam == null) {
            addError("Кампания с ИД=" + campaignId + " не найдена");
            return null;
        } else {
            return cam.getStrategy().getStrategyId();
        }
    }

    public Event getEventById(Long eventId) {
        if (eventId == null) {
            return null;
        }
        Event event = eventDao.find(eventId);
        return event;
    }

    public List<FailReason> getAllFailReasons(Long strategyId) {
        Strategy str = strategyDao.find(strategyId);
        List<FailReason> failReasons = str.getFailReasons();
        return failReasons;
    }

    public boolean writeModuleInHistory(Date date, Long cabinetId, Long moduleId, Long eventId) {
        boolean performed = false;
        Event ev = eventDao.find(eventId);
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Module module = moduleDao.find(moduleId);

        ModuleEventClient mec = new ModuleEventClient();

        mec.setEvent(ev);
        mec.setCabinet(pk);
        mec.setModule(module);
        mec.setGroup(module.getGroup());
        mec.setInsertDate(date);
        mec.setStrategy(ev.getCampaign().getStrategy());
        if (validate(mec)) {
            moduleEventClientDao.save(mec);
        }
        return performed;
    }

    public void badFinish(Long[] moduleIds, Long[] dates, Long pkId, Long eventId, Long reasonId, String finalComment) {
        Event ev = eventDao.find(eventId);
        if (ev != null) {
            if (!ev.isClosed()) {
                if (!finalComment.equals("")) {
                    FailReason fr = failReasonDao.find(reasonId);
                    ev.setFinalComment(finalComment);
                    ev.setFailReason(fr);
                    if (validate(ev)) {
                        writeModulesInHistory(pkId, eventId, moduleIds, dates, true);
                        ev.setStatus(Event.FAILED);
                        eventDao.update(ev);
                        addEventComment("Звонок завершен с отрицательным итогом", EventComment.FAILED, ev, pkId);
                        addEventComment("Комментарий: "+finalComment, EventComment.COMMENTED, ev, pkId);
                    }
                } else {
                    addError("Необходимо оставить комментарий.");
                }
            } else {
                addError("Информация о контакте с клиентом уже была добавлена.");
            }

        } else {
            addError("Эвент " + eventId + " не найден!");
        }
    }

    public void goodFinish(Long[] moduleIds, Long[] dates, Long pkId, Long eventId, Date successDate, String finalComment) {
        Event ev = eventDao.find(eventId);
        if (ev != null) {
            if (!ev.isClosed()) {
                if (!finalComment.equals("")) {
                    ev.setFinalComment(finalComment);
                    ev.setSuccessDate(successDate);
                    if (validate(ev)) {
                        writeModulesInHistory(pkId, eventId, moduleIds, dates, true);
                        ev.setStatus(Event.SUCCESSFUL);
                        eventDao.update(ev);
                        addEventComment("Звонок успешно завершен", EventComment.SUCCESSFUL, ev, pkId);
                        addEventComment("Комментарий: "+finalComment, EventComment.COMMENTED, ev, pkId);
                    }
                } else {
                    addError("Необходимо оставить комментарий");
                }
            } else {
                addError("Звонок завершен, информация не может быть добавлена");
            }

        } else {
            addError("Эвент " + eventId + " не найден!");
        }
    }

    public void postponeEvent(Long[] moduleIds, Long[] dates, Long pkId, Long eventId, Date postponeDate, String finalComment) {
        Event ev = eventDao.find(eventId);
        if (ev != null) {
            if (!ev.isClosed()) {
                if (!finalComment.equals("")) {
                    ev.setComment(finalComment);
                    ev.setPostponedDate(postponeDate);
                    ev.setStatus(Event.POSTPONED);
                    if (validate(ev)) {
                        writeModulesInHistory(pkId, eventId, moduleIds, dates, false);
                        eventDao.update(ev);
                        addEventComment("Звонок перенесен на " + DateAdapter.formatByDate(postponeDate, DateAdapter.FULL_FORMAT), EventComment.POSTPONE, ev, pkId);
                        addEventComment("Комментарий: "+finalComment, EventComment.COMMENTED, ev, pkId);
                    }
                } else {
                    addError("Необходимо оставить комментарий");
                }
            } else {
                addError("Информация о контакте с клиентом уже была добавлена");
            }

        } else {
            addError("Эвент " + eventId + " не найден!");
        }
    }

    public boolean writeModulesInHistory(Long pkId, Long eventId, Long[] moduleIds, Long[] dates, boolean finishing) {
        Event ev = eventDao.find(eventId);
        if (ev != null && moduleIds != null && dates != null && dates.length > 0 && moduleIds.length > 0 && dates.length == moduleIds.length) {
            List<ModuleEventClient> history = new ArrayList();
            Strategy strat = ev.getCampaign().getStrategy();
            PersonalCabinet pk = personalCabinetDao.find(pkId);
            if (!ev.isClosed() && pk != null && strat != null) {
                for (int i = 0; i < dates.length; i++) {
                    Date date = new Date(dates[i]);
                    Module mod = moduleDao.find(moduleIds[i]);
                    if (mod != null) {
                        ModuleEventClient mec = new ModuleEventClient();
                        mec.setCabinet(pk);
                        mec.setEvent(ev);
                        mec.setStrategy(strat);
                        mec.setInsertDate(date);
                        mec.setModule(mod);
                        mec.setGroup(mod.getGroup());
                        if (i == dates.length - 1 && finishing) {
                            mec.setSign("final");
                        }
                        if (validate(mec)) {
                            history.add(mec);
                        }
                        //??mec.setSign(null);
                    }
                }
            } else {
                //addError("ev:"+ev.isClosed()+"; "+pkId+"; str="+strat);
            }
            for (ModuleEventClient mec : history) {
                moduleEventClientDao.save(mec);
            }
            return true;
        }
        return false;
    }

    public void clearHistory(Long eventId, Long pkId) {
        eventDao.clearHistory(eventId, pkId);
    }

    public boolean isEmptyHistory(Long eventId, Long pkId) {
        if (eventDao.countReplicas(eventId, pkId) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public LinkedHashMap<User, HashMap> getUsersAndSuccessfulFailedPerformancesForReport(Date dateCampaignFrom, Date dateCampaignTo, Long pkId) {
        LinkedHashMap<User, HashMap> result = new LinkedHashMap();
        for (Object[] o : eventDao.getUsersAndSuccessfulFailedPerformancesForReport(dateCampaignFrom, dateCampaignTo, pkId)) {
            HashMap<String, String> infoMap = new HashMap();
            infoMap.put("successful", StringAdapter.getString(o[1]));
            infoMap.put("failed", StringAdapter.getString(o[2]));
            infoMap.put("all", StringAdapter.getString(o[3]));
            result.put((User) o[0], infoMap);
        }
        return result;
    }

    public List<Event> getPostponedEvents(Date dateFrom, Date dateTo, Long pkId) {
        if (dateTo == null) {
            Calendar cl = Calendar.getInstance();
            cl.set(Calendar.DATE, cl.getActualMaximum(Calendar.DATE));
            dateTo = cl.getTime();
        }
        if (dateFrom == null) {
            Calendar cl = Calendar.getInstance();
            cl.set(2000, 0, 1);
            dateFrom = cl.getTime();
        }
        //addError("from "+dateFrom.toString()+" to "+dateTo.toString());
        return eventDao.getPostponedEvents(dateFrom, dateTo, pkId);
    }

    /*public List<Campaign> getCampaignsByUserAndCabinet(Long cabinetId, Long userId){
     return eventDao.getCampaignsByUserAndCabinet(cabinetId, userId);
     }*/
    public void assignOneEvent(Long userId, Long eventId, Long cabinetId) {
        if (eventId != null) {
            Event ev = eventDao.find(eventId);
            if (!ev.isClosed()) {
                int type = EventComment.ASSIGN;;
                String systemComment = "";
                if (userId == null) {
                    if (Event.POSTPONED == ev.getStatus()) {
                        addError("Перенесенный звонок нельзя сделать неназначенным;");
                    } else {
                        ev.setStatus(Event.UNASSIGNED);
                        type = EventComment.UNASSIGN;
                        systemComment = "Звонк перенесен в неназначенные";
                    }
                } else {
                    if (ev.getStatus() == null || Event.UNASSIGNED == ev.getStatus()) {
                        User user = userDao.find(userId);
                        ev.setStatus(Event.ASSIGNED);
                        ev.setUser(user);
                        type = EventComment.ASSIGN;
                        systemComment = "Звонок назначен на " + user.getShortName()+"("+user.getEmail()+")";
                    }
                }
                if (getErrors().isEmpty() && validate(ev)) {
                    eventDao.update(ev);
                    addEventComment(systemComment, type, ev, cabinetId);
                }
            } else {
                addError("Взаимодействие с клиентом в рамках данной кампании завершено, его нельзя переназначить.");
            }
        } else {
            addError("Ид звонка не передан.");
        }
    }

    public void changeUserCampaignAssignation(Long campaignId, Long userFromId, Long userToId, Long pkId) {
        if (pkId != null) {
            List<Event> evs;
            if (userFromId != null) {
                evs = eventDao.getAssignedNotClosedUserEvents(campaignId, userFromId, pkId);
            } else {
                evs = eventDao.getAssignedEvents(campaignId, pkId);
            }
            User userTo = null;
            String textComment = "";
            int type;
            if (userToId != null) {
                userTo = userDao.find(userToId);
                textComment = "Звонок назначен на " + userTo.getShortName()+"("+userTo.getEmail()+")";
                type = EventComment.ASSIGN;
            } else {
                textComment = "Звонок перенесен в неназначенные";
                type = EventComment.UNASSIGN;
            }

            for (Event ev : evs) {
                ev.setUser(userTo);
                if (userTo == null) {
                    ev.setStatus(Event.UNASSIGNED);
                    ev.setPostponedDate(null);
                }
                if (validate(ev)) {
                    eventDao.update(ev);
                    addEventComment(textComment, type, ev, pkId);
                }
            }
        } else {
            addError("Ошибка личного кабинета! Обратитесь в техническую поддержку!");
        }
    }

    public Event getAvailableEventById(Long eventId) {
        Event ev = eventDao.find(eventId);
        if (ev != null && Event.FAILED != ev.getStatus() && Event.SUCCESSFUL != ev.getStatus()) {
            return ev;
        }
        return null;
    }

    public List<Event> getPostponedEvents(Long userId, Long pkId) {
        return eventDao.getPostponedEvents(userId, pkId);
    }

    /*public void setEventsUnassigned(Long pkId,Long userId){
     List<Event> assignedEvents
     }*/
    public HashSet<CabinetUser> getParticipatedUsers(Long campaignId, Long pkId) {
        List<User> users = userDao.getParticipatedUsers(campaignId, pkId);

        HashSet<CabinetUser> cus = new HashSet();
        for (User u : users) {
            List<CabinetUser> culist = cabinetUserDao.getByUserAndCabinet(u.getId(), pkId);
            if (!culist.isEmpty()) {
                CabinetUser cu = culist.get(0);
                cus.add(cu);
            }
        }
        return cus;
    }
    
    public List<User>getUserWithAssignsList(Long campaignId,Long pkId){
        return eventDao.getUserWithAssignsList(campaignId, pkId);
    }
    
    public LinkedHashMap<Long,User> getUserWithAssignsMap(Long campaignId,Long pkId){
        LinkedHashMap<Long,User>res=new LinkedHashMap();
        for(User u:eventDao.getUserWithAssignsList(campaignId, pkId)){
            res.put(u.getId(), u);
        }
        return res;
    }

    public List<CabinetUser> getCUListForCampaignSpecification(Long campaignId, Long pkId) {
        List<CabinetUser> mclist = getActiveMakingCallsUsers(pkId);
        HashSet<CabinetUser> pset = getParticipatedUsers(campaignId, pkId);
        HashSet<Long> idset = new HashSet();
        for (CabinetUser cu : mclist) {
            idset.add(cu.getId());
        }
        for (CabinetUser cu : pset) {
            if (!idset.contains(cu.getId())) {
                mclist.add(cu);
            }
        }
        return mclist;
    }

    public List<CabinetUser> getSurnameSortedCUListForCampaignSpecification(Long campaignId, Long pkId) {
        List<CabinetUser> cusers = getCUListForCampaignSpecification(campaignId, pkId);
        Collections.sort(cusers, new CUSurnameComparator());
        return cusers;
    }

    private class CUSurnameComparator implements Comparator<CabinetUser> {

        @Override
        public int compare(CabinetUser a, CabinetUser b) {
            return a.getUser().getSurname().compareToIgnoreCase(b.getUser().getSurname());
        }
    }

    public void addEventComment(String text, int type, Event ev, Long pkId) {
        PersonalCabinet pk = personalCabinetDao.find(pkId);
        EventComment ec = new EventComment();
        ec.setCabinet(pk);
        ec.setComment(text);
        ec.setEvent(ev);
        User user = authManager.getCurrentUser();
        ec.setAuthor(user);
        Date date = new Date();
        ec.setInsertDate(date);
        ec.setType(type);
        if (validate(ec)) {
            eventCommentDao.save(ec);
        }
    }

    public void setShowModulesWithText(Boolean show, Long campaignId, Long pkId) {
        if (campaignId != null) {
            Campaign c = campaignDao.find(campaignId);
            Long showLong = null;
            if (show != null && show) {
                showLong = (long) 1;
            }
            c.setShowModulesWithText(showLong);
            if (validate(c)) {
                campaignDao.update(c);
            }
        } else {
            addError("ИД кампании не передан");
        }
    }
    
    public HSSFWorkbook getEventClientXls(Long campaignId,Integer assigned,Integer processed,Long pkId) throws Exception{
        List<Event>result=getEventFilter(campaignId,pkId,assigned,processed);
        /*if(!result.isEmpty()){
            throw new Exception(result.size()+"");
        }*/
        HSSFWorkbook workbook = new HSSFWorkbook();

        int n = 0;
        HSSFSheet sheet = workbook.createSheet("Результат");
        HSSFRow rowhead = sheet.createRow((short) n);
        int r = 0;
        rowhead.createCell(r++).setCellValue("Номер уникальный");
        rowhead.createCell(r++).setCellValue("Клиент");
        rowhead.createCell(r++).setCellValue("Телефон");
        rowhead.createCell(r++).setCellValue("Комментарий");
        rowhead.createCell(r++).setCellValue("К.Л.");
        rowhead.createCell(r++).setCellValue("Телефон Л.П.Р");
        rowhead.createCell(r++).setCellValue("Л.П.Р.");
        rowhead.createCell(r++).setCellValue("Адрес");
        rowhead.createCell(r++).setCellValue("Пользователь");
        rowhead.createCell(r++).setCellValue("Дата установки статуса");
        rowhead.createCell(r++).setCellValue("Статус");
        rowhead.createCell(r++).setCellValue("Назначенная дата");
        rowhead.createCell(r++).setCellValue("Итог");
        n++;
        for(Event ev:result){
            HSSFRow rowbody = sheet.createRow((short) n);
            
            String date = "";
            if(ev.getSetStatusDate()!=null){
                date = DateAdapter.formatByDate(ev.getSetStatusDate(), DateAdapter.FULL_FORMAT);
            }
            String finaldate = "";
            if(ev.getSuccessDate()!=null){
                finaldate = DateAdapter.formatByDate(ev.getSuccessDate(), DateAdapter.FULL_FORMAT);
            }
            User u = ev.getUser();
            String uname = "Не назначено";
            if(u!=null){
                uname=u.getShortName();
            }
            r=0;
            rowbody.createCell(r++).setCellValue(ev.getClient().getUniqueId());
            rowbody.createCell(r++).setCellValue(ev.getClient().getNameCompany());
            rowbody.createCell(r++).setCellValue(ev.getClient().getFormattedPhoneSec());
            rowbody.createCell(r++).setCellValue(ev.getComment());
            rowbody.createCell(r++).setCellValue(ev.getClient().getNameSecretary());
            rowbody.createCell(r++).setCellValue(ev.getClient().getFormattedPhoneLpr());
            rowbody.createCell(r++).setCellValue(ev.getClient().getNameLpr());
            rowbody.createCell(r++).setCellValue(ev.getClient().getAddress());
            rowbody.createCell(r++).setCellValue(uname);
            rowbody.createCell(r++).setCellValue(date);
            rowbody.createCell(r++).setCellValue(ev.getRusStatus());
            rowbody.createCell(r++).setCellValue(finaldate);
            rowbody.createCell(r++).setCellValue(ev.getFinalComment());
            n++;
        }
        return workbook;
    }
    /*public LinkedHashMap<Long,HashMap<String,String>>GetCampaignResultReportData(List<Long> campaignIds,Long PkId){
     List<Object> daoRes = eventDao.getUserAndAssignedAndSuccAndFailedByaDateAndCampaign(campaignIds, PkId);
     for(Object o:daoRes){
            
     }
     }*/
    
    

}
