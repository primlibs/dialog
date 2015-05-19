/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.EventDao;
import dao.CampaignDao;
import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import dao.UserDao;
import entities.CabinetUser;
import entities.Client;
import entities.Campaign;
import entities.Event;
import entities.PersonalCabinet;
import entities.Strategy;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private GroupDao groupDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private CampaignDao campaignDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ClientService clientService;

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

    public List<Strategy> strategytList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return pk.getStrategyList();
        } else {
            addError("Стратегия не найден по id " + cabinetId);
        }
        return new ArrayList();
    }

    public List<Campaign> getCampaignList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return pk.getEventList();
        } else {
            addError("Компания не найдена по id " + cabinetId);
        }
        return new ArrayList();
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
                        cl.setUniqueId(StringAdapter.getString(rw.getCell(0)));
                        cl.setNameCompany(StringAdapter.getString(rw.getCell(1)));
                        cl.setNameSecretary(StringAdapter.getString(rw.getCell(2)));
                        cl.setNameLpr(StringAdapter.getString(rw.getCell(3)));
                        cl.setPhoneSecretary(Long.getLong(StringAdapter.getString(rw.getCell(4))));
                        cl.setPhoneLpr(Long.getLong(StringAdapter.getString(rw.getCell(5))));
                        cl.setAddress(StringAdapter.getString(rw.getCell(6)));
                        cl.setComment(StringAdapter.getString(rw.getCell(7)));
                        cl.setCabinet(pk);
                        if (validate(cl)) {
                            clientDao.save(cl);
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
                                eventDao.save(event);
                            }

                        }
                    }
                }
            }
        }

    }

    public Campaign getCampaign(Long campaignId) {
        Campaign camp = campaignDao.find(campaignId);
        // event.getName();
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
        List<Client> clList = clientDao.getNotAssignedClientsByCampaign(pk, campaign);
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
        int user = listRoleUserActiveCabinetUser(cabinetId).size();
        List<CabinetUser> cabinetUserList = listRoleUserActiveCabinetUser(cabinetId);

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
    public void eventAppointSaveAll(Long eventId, Long cabinetId, Long[] userIdArray, String[] clientNumArray) {
        LinkedHashMap<Long, Integer> appointMap = new LinkedHashMap<Long, Integer>();
        List<Event> events = getUnassignedEvent(eventId, cabinetId);
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        int clientNotAssignedSize = getNotAssignedClients(eventId, cabinetId).size();
        int clientCount = 0;
        int summClient = 0;

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
            addError("Количество клиентов " + summClient + " больше не назначенным: " + clientNotAssignedSize);
        }
    }

    public HashMap<Long, String> userAssignedClient(Long eventId, Long cabinetId) {
        HashMap<Long, String> userAssignedClient = new HashMap<Long, String>();
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Campaign event = campaignDao.find(eventId);
        for (Object[] ecl : eventDao.getUserAssignedClient(eventId, cabinetId)) {
            userAssignedClient.put(StringAdapter.toLong(ecl[1]), StringAdapter.getString(ecl[0]));
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
            List<Event> events = eventDao.getEventsByUserId(campaignId, cabinetId, userId);//лист ссылок по евенту и личному кабинету
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
    public HashMap<Long, String> userAssignedClientNotProcessed(Long eventId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientNotProcessed = new HashMap<Long, String>();
        for (Object[] ecl : eventDao.getAssignedNotProcessedClientsByUserId(eventId, cabinetId)) {
            userAssignedClientNotProcessed.put(StringAdapter.toLong(ecl[1]), StringAdapter.getString(ecl[0]));
        }
        return userAssignedClientNotProcessed;
    }

    //клиенты назначение юзерам обработанные
    public HashMap<Long, String> userAssignedClientProcessed(Long eventId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientProcessed = new HashMap<Long, String>();
        for (Object[] ecl : eventDao.getAssignedProcessedClientsByUserId(eventId, cabinetId)) {
            userAssignedClientProcessed.put(StringAdapter.toLong(ecl[1]), StringAdapter.getString(ecl[0]));
        }
        return userAssignedClientProcessed;
    }

    //клиенты назначение юзерам обработанные Успешно
    public HashMap<Long, String> userAssignedClientProcessedSuccess(Long eventId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientProcessedSuccess = new HashMap<Long, String>();
        for (Object[] ecl : eventDao.getAssignedProcessedSuccessClientsByUserId(eventId, cabinetId)) {
            userAssignedClientProcessedSuccess.put(StringAdapter.toLong(ecl[1]), StringAdapter.getString(ecl[0]));
        }
        return userAssignedClientProcessedSuccess;
    }

    //клиенты назначение юзерам обработанные НЕ успешно
    public HashMap<Long, String> userAssignedClientProcessedFails(Long eventId, Long cabinetId) {
        HashMap<Long, String> userAssignedClientProcessedFails = new HashMap<Long, String>();
        for (Object[] ecl : eventDao.getAssignedProcessedFailedClientsByUserId(eventId, cabinetId)) {
            userAssignedClientProcessedFails.put(StringAdapter.toLong(ecl[1]), StringAdapter.getString(ecl[0]));
        }
        return userAssignedClientProcessedFails;
    }

    public List<Event> userShowPageEventClientList(Long cabinetId, Long userId) {
        List<Event> ecl = eventDao.getEeventsByCabinetAndUserId(cabinetId, userId);
        return ecl;
    }
}
