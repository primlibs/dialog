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
import entities.Module;
import entities.ModuleEventClient;
import entities.PersonalCabinet;
import entities.Strategy;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import service.parent.PrimService;
import support.StringAdapter;

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
    private ClientService clientService;
    
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
            addError("Стратегия не найдена по id " + cabinetId);
        }
        return new ArrayList();
    }

    public LinkedHashMap<Campaign, HashMap<String,String>> getCampaignsWithCountInfos(Long cabinetId) {
        LinkedHashMap<Campaign, HashMap<String,String>> res = new LinkedHashMap();
        HashMap<Long,HashMap<String,String>> countMap = eventDao.getFinishedAndUnassignedEventCountsInCampaignsAsMap(cabinetId);
        for(Campaign c:campaignDao.getAllCampaigns(cabinetId)){
            HashMap<String,String> InfoMap = countMap.get(c.getId());
            if(InfoMap==null){
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
                addError("не найдена стратегия по id" + strategyId);
            }
        } else {
            addError("не найден личный кабинет по id" + cabinetId);
        }

    }

    public List<CabinetUser> getActiveMakingCallsUsers(Long cabinetId) {
        return cabinetUserDao.getMakingCallsCabUsers(cabinetId);
        /*
        
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<CabinetUser> listRoleUser = pk.getRoleUserActiveCabinetUserList();
        return listRoleUser;*/
    }

    public HSSFWorkbook getXls() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        int n = 0;
        HSSFSheet sheet = workbook.createSheet("Клиенты");
        HSSFRow rowhead = sheet.createRow((short) n);
        rowhead.createCell(0).setCellValue("Номер уникальный");
        rowhead.createCell(1).setCellValue("Название компании");
        rowhead.createCell(2).setCellValue("Имя секретаря");
        rowhead.createCell(3).setCellValue("Имя лица принимающего решение");
        rowhead.createCell(4).setCellValue("Телефон секретаря");
        rowhead.createCell(5).setCellValue("Телефон лица принимающего решение ");
        rowhead.createCell(6).setCellValue("Адрес");
        rowhead.createCell(7).setCellValue("Коментарии");
        n++;
        return workbook;
    }

    public void readXls(MultipartFile fileXls, Long cabinetId, Long campaignId, Boolean update) throws Exception {
        List<Client> clientsListForSave = new ArrayList();
        List<Event> eventsListForSave = new ArrayList();
        List<Client> noContactList = new ArrayList();
        Boolean newClient = false;
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<Client> pkList = pk.getClientList();
        Campaign campaign = campaignDao.find(campaignId);
        InputStream fis = fileXls.getInputStream();
        HSSFWorkbook inputWorkbook = new HSSFWorkbook(fis);
        int sheetCount = inputWorkbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            HSSFSheet hss = inputWorkbook.getSheetAt(i);
            Iterator<Row> it = hss.iterator();
            while (it.hasNext()) {
                Row rw = it.next();
                if (!(StringAdapter.getString(rw.getCell(0))).trim().equals("Номер уникальный")) {
                    Client cl = clientDao.getClientByUniqueIdInLk(StringAdapter.getString(rw.getCell(0)), cabinetId);
                    if (cl == null) {
                        cl = new Client();
                        newClient = true;
                    }
                    if (newClient == true || update == true) {
                        String uid = StringAdapter.getString(rw.getCell(0));
                        cl.setUniqueId(uid);
                        cl.setNameCompany(StringAdapter.getString(rw.getCell(1)));
                        cl.setNameSecretary(StringAdapter.getString(rw.getCell(2)));
                        cl.setNameLpr(StringAdapter.getString(rw.getCell(3)));
                        Long secretaryPhone = StringAdapter.toLong(rw.getCell(4));
                        Long lprPhone = StringAdapter.toLong(rw.getCell(5));
                        cl.setPhoneSecretary(secretaryPhone);
                        cl.setPhoneLpr(lprPhone);
                        cl.setAddress(StringAdapter.getString(rw.getCell(6)));
                        cl.setComment(StringAdapter.getString(rw.getCell(7)));
                        cl.setCabinet(pk);
                        if (validate(cl)) {
                            if(!secretaryPhone.equals((long)0)||!lprPhone.equals((long)0)){
                                clientsListForSave.add(cl);
                            }else{
                                noContactList.add(cl);
                            }
                        }
                    }

                    if (getError().isEmpty()) {
                        Event ecl = eventDao.getEvent(cl, pk, campaign);
                        if (ecl == null) {
                            Event event = new Event();
                            event.setCabinet(pk);
                            event.setClient(cl);
                            event.setEvent(campaign);
                            if (validate(event)) {
                                eventsListForSave.add(event);
                                //eventDao.save(event);
                            }

                        }
                    }
                }
            }
        }
        if(noContactList.isEmpty()){
            for(Client cl:clientsListForSave){
                clientDao.save(cl);
            }
            for(Event ev:eventsListForSave){
                eventDao.save(ev);
            }
        }else{
            String err = "Не указаны контакты клиентов с УИД: ";
            for(Client cl:noContactList){
                err+=cl.getUniqueId()+"; ";
            }
            addError(err+" для загрузки клиентов необходимо указать хотя бы один контакт.");
        }
        

    }

    public Campaign getCampaign(Long campaignId) {
        Campaign camp = campaignDao.find(campaignId);

        return camp;
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
    public List<Client> getClientList(Long campaignId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Campaign campaign = campaignDao.find(campaignId);
        List<Client> clList = clientDao.getClientsByCampaign(pk, campaign);
        return clList;

    }

// получить лист клиентов не назначеных
    public List<Client> getNotAssignedClients(Long campaignId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Campaign campaign = campaignDao.find(campaignId);
        List<Client> clList = clientDao.getNotAssignedClientsByCampaign(pk.getId(), campaign.getId());
        return clList;

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
                if (validate(event)) {
                    eventDao.save(event);
                }
            }
        }
    }

    //распределить всех клиентов по юзерам
    public LinkedHashMap<Long, Integer> eventAppointAll(Long eventId, Long cabinetId) {
        int clientNotAssigned = getNotAssignedClients(eventId, cabinetId).size();
        int user = getActiveMakingCallsUsers(cabinetId).size();
        List<CabinetUser> cabinetUserList = getActiveMakingCallsUsers(cabinetId);

        int clientOneUser = clientNotAssigned / user; //деление
        int endClientUser = clientNotAssigned % user; // остаток

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

    }

//сохранение распределения
    public void eventAppointSaveAll(Long campaignId, Long cabinetId, Long[] userIdArray, String[] clientNumArray) {
        LinkedHashMap<Long, Integer> appointMap = new LinkedHashMap();
        List<Event> events = getUnassignedEvent(campaignId, cabinetId);
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        int clientNotAssignedSize = getNotAssignedClients(campaignId, cabinetId).size();
        int clientCount = 0;
        int summClient = 0;
        if(userIdArray.length>0&&events.size()>0&&clientNumArray.length>0){
            for (int i = 0; i < userIdArray.length; i++) {
                if (clientNumArray.length >= i) {
                    int count = StringAdapter.toInteger(clientNumArray[i]);
                    clientCount += count;
                    appointMap.put(userIdArray[i], count);
                } else {
                    appointMap.put(userIdArray[i], 0);
                }
            }

            ///получить количество нераспределенных сравить с количеством подангых - если поданных больше - ошибка
            //в цикле для каждогоо элемента appointMap распределить на пользователя пока 
            //остаются заявки вывести объект который сообщит сколько на кого было распределено
            for (int i = 0; i < clientNumArray.length; i++) {
                String df = clientNumArray[i];
                Integer i2 = Integer.valueOf(df);
                summClient += i2;
            }
            Long eclId = Long.valueOf(0);
            if (summClient <= clientNotAssignedSize) {
                for (Long userId : appointMap.keySet()) {
                    Integer clientCn = appointMap.get(userId);
                    User user = userDao.getUserBelongsPk(pk, userId);
                    if (user != null) {
                        int gaga = 0;
                        for (Event ecl : events) {
                            if (gaga < clientCn && eclId < ecl.getEventId()) {
                                ecl.setUser(user);
                                if (validate(ecl)) {
                                    eventDao.save(ecl);
                                    eclId = ecl.getEventId();
                                }
                                gaga += 1;
                            }
                        }
                    } else {
                        addError("Ошибка! Пользователь не принадлежит к личному кабинету");
                    }
                }
            } else {
                addError("Количество клиентов " + summClient + " больше не назначенных: " + clientNotAssignedSize);
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
            List<Event> events = eventDao.getAssignedEvent(campaignId, cabinetId);//лист  ссылок  НАЗНАЧЕННЫХ по евенту и личному кабинету
            return events;
        }
        if (assigned == -2 && processed == -1) {
            List<Event> events = eventDao.getAssignedEventNotProcessed(campaignId, cabinetId);//лист ссылок НАЗНАЧЕННЫХ, НЕ ОБРАБОТАННЫХ по евенту и личному кабинету
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
            List<Event> events = eventDao.getEventsByUserId(campaignId, cabinetId, userId);//лист ссылок  по userId, по евенту и личному кабинету
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
        for (CabinetUser user : getActiveMakingCallsUsers(cabinetId)) {
            userMap.put(user.getId(), "0");
        }
        return userMap;
    }

    //лист Ссылкок event по campaignId НЕ ОБРАБОТАНЫХ по userId
    public Event getEvenByUserByCampaign(Long campaignId, Long cabinetId, Long userId) {
        List<Event> events = eventDao.getEventListByUserByCampaign(campaignId, cabinetId, userId);
        if(events.isEmpty()){
            return null;
        }
        java.util.Random rng = new java.util.Random();
        Event ev = events.get(rng.nextInt(events.size()));
        if (ev.getPostponedDate() != null) {
            return ev;
        }
        ev = events.get(rng.nextInt(events.size()));
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
        Event event = eventDao.find(eventId);
        return event;
    }

    public List<FailReason> getAllFailReasons(Long strategyId){
        Strategy str = strategyDao.find(strategyId);
        List<FailReason> failReasons = str.getFailReasons();
        return failReasons;
    }
    
    public boolean writeModuleInHistory(Date date,Long userId,Long cabinetId,Long moduleId,Long eventId){
        boolean performed = false;
        Event ev = eventDao.find(eventId);
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        User user = userDao.find(userId);
        Module module = moduleDao.find(moduleId);
        
        ModuleEventClient mec = new ModuleEventClient();
        
        mec.setEvent(ev);
        mec.setCabinet(pk);
        mec.setModule(module);
        mec.setGroup(module.getGroup());
        mec.setInsertDate(date);
        mec.setStrategy(ev.getCampaign().getStrategy());
        if(validate(mec)){
            moduleEventClientDao.save(mec);
        }
        return performed;
    }
    
    public void badFinish(Long eventId,Long reasonId,String comment){
        Event ev = eventDao.find(eventId);
        FailReason fr = failReasonDao.find(reasonId);
        ev.setFinalComment(comment);
        ev.setFailReason(fr);
        if(validate(ev)){
            eventDao.update(ev);
        }
    }
    
    public void goodFinish(Long eventId,Date successDate,String finalComment){
        Event ev = eventDao.find(eventId);
        ev.setFinalComment(finalComment);
        ev.setSuccessDate(successDate);
        if(validate(ev)){
            eventDao.update(ev);
        }
    }
    
    public LinkedHashMap<User,HashMap> getUsersAndSuccessfulFailedPerformancesForReport(Date dateCampaignFrom,Date dateCampaignTo,Long pkId){
        LinkedHashMap<User,HashMap>result = new LinkedHashMap();
        for(Object[] o:eventDao.getUsersAndSuccessfulFailedPerformancesForReport(dateCampaignFrom,dateCampaignTo,pkId)){
            HashMap<String,String> infoMap = new HashMap();
            infoMap.put("successful",StringAdapter.getString(o[1]));
            infoMap.put("failed",StringAdapter.getString(o[2]));
            infoMap.put("all",StringAdapter.getString(o[3]));
            result.put((User)o[0],infoMap);
        }
        return result;
    }
    
    public void postponeEvent(Long eventId,Date postponeDate,String Comment){
        Event ev = eventDao.find(eventId);
        ev.setComment(Comment);
        ev.setPostponedDate(postponeDate);
        if(validate(ev)){
            eventDao.update(ev);
        }
    }
    
    public List<Event> getPostponedEvents(Date dateFrom,Date dateTo,Long pkId){
        if(dateTo==null){
            Calendar cl = Calendar.getInstance();
            cl.set(Calendar.DATE,cl.getActualMaximum(Calendar.DATE));
            dateTo=cl.getTime();
        }
        if(dateFrom==null){
            Calendar cl = Calendar.getInstance();
            cl.set(2000, 0, 1);
            dateFrom=cl.getTime();
        }
        //addError("from "+dateFrom.toString()+" to "+dateTo.toString());
        return eventDao.getPostponedEvents(dateFrom, dateTo, pkId);
    }
    
    /*public List<Campaign> getCampaignsByUserAndCabinet(Long cabinetId, Long userId){
        return eventDao.getCampaignsByUserAndCabinet(cabinetId, userId);
    }*/
    
    public boolean assignOneEvent(Long userId,Long eventId){
        User user = userDao.find(userId);
        Event ev = eventDao.find(eventId);
        if(user!=null&&ev!=null){
            if(ev.getFinalComment()==null){
                ev.setUser(user);
                if(validate(ev)){
                    eventDao.update(ev);
                    return true;
                }
            }else{
                addError("Взаимодействие с клиентом в рамках данной кампании завершено, контакт нельзя перенести.");
                return false;
            }
        }
        return false;
    }
    
}
