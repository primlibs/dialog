/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDao;
import dao.EventClientLinkDao;
import dao.EventDao;
import dao.GroupDao;
import dao.ModuleDao;
import dao.PersonalCabinetDao;
import dao.StrategyDao;
import dao.UserDao;
import entities.CabinetUser;
import entities.Client;
import entities.Event;
import entities.EventClientLink;
import entities.PersonalCabinet;
import entities.Strategy;
import entities.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
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
    private EventDao eventDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private EventClientLinkDao eventClientLinkDao;

    @Autowired
    private ClientService clientService;

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

    public void readXls(MultipartFile fileXls, Long cabinetId, Long eventId, Boolean update) throws Exception {
        Boolean newClient = false;
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<Client> pkList = pk.getClientList();
        Event event = eventDao.find(eventId);
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
                        EventClientLink ecl = eventClientLinkDao.getEventClientLink(cl, pk, event);
                        if (ecl == null) {
                            EventClientLink eventLinkClient = new EventClientLink();
                            eventLinkClient.setCabinet(pk);
                            eventLinkClient.setClient(cl);
                            eventLinkClient.setEvent(event);
                            if (validate(eventLinkClient)) {
                                eventClientLinkDao.save(eventLinkClient);
                            }

                        }
                    }
                }
            }
        }

    }

    public Event getEvent(Long eventId) {
        Event event = eventDao.find(eventId);
        // event.getName();
        return event;
    }

    public List<EventClientLink> getEventClientLinkList(Long eventId, Long cabinetId) {
        List<EventClientLink> eventClientLinkList = eventClientLinkDao.getEventClientLinkListByEventId(eventId, cabinetId);
        return eventClientLinkList;
    }

// получить лист ссылок НЕ назначиных клиентов
    public List<EventClientLink> getUnassignedEventClientLink(Long eventId, Long cabinetId) {
        List<EventClientLink> eventClientLinkList = eventClientLinkDao.getUnassignedEventClientLink(eventId, cabinetId);
        return eventClientLinkList;

    }

// получить ист клиентов
    public List<Client> getClientList(Long eventId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Event event = eventDao.find(eventId);
        List<Client> clList = clientDao.getClientByEvent(pk, event);
        return clList;

    }

// получить лист клиентов не назначеных
    public List<Client> getClientListNotAssigned(Long eventId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Event event = eventDao.find(eventId);
        List<Client> clList = clientDao.getClientByEventNotAssigned(pk, event);
        return clList;

    }

    public void eventAppointSave(String[] arrayClientIdUserId, Long cabinetId, Long eventId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        Event event = eventDao.find(eventId);

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
                EventClientLink link = eventClientLinkDao.getEventClientLink(client, pk, event);
                link.setUser(user);
                if (validate(link)) {
                    eventClientLinkDao.save(link);
                }
            }
        }
    }

    //распределить всех клиентов по юзерам
    public LinkedHashMap<Long, Integer> eventAppointAll(Long eventId, Long cabinetId) {
        int clientNotAssigned = getClientListNotAssigned(eventId, cabinetId).size();
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
        List<EventClientLink> linkLs = getUnassignedEventClientLink(eventId, cabinetId);
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        int clientNotAssignedSize = getClientListNotAssigned(eventId, cabinetId).size();
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
        if (summClient > clientNotAssignedSize) {
            for (Long userId : appointMap.keySet()) {
                Integer clientCn = appointMap.get(userId);
                //   User user = userDao.find(userId);
                User user = userDao.getUserBelongsPk(pk, userId);
                if (user != null) {
                    int gaga = 0;
                    for (EventClientLink ecl : linkLs) {
                        if (gaga < clientCn) {
                            ecl.setUser(user);
                            if (validate(ecl)) {
                                eventClientLinkDao.save(ecl);
                            }
                            gaga += 1;
                        } else {
                            break;
                        }
                    }
                } else {
                    addError("Ошибка! Юзер не принадлежит к личному кабинету");
                }
            }
        } else {
            addError("Количество клиентов не соответствует существующим: " + clientNotAssignedSize);
        }
    }
}
