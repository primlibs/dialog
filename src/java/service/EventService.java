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
import entities.CabinetUser;
import entities.Client;
import entities.Event;
import entities.EventClientLink;
import entities.PersonalCabinet;
import entities.Strategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    public List<EventClientLink> getEventClientLinkList(Long eventId) {
        List<EventClientLink> eventClientLinkList = eventClientLinkDao.getEventClientLinkListByEventId(eventId);

        return eventClientLinkList;
    }
// не использую

    public List<EventClientLink> getEventClientLinkList(Long eventId, Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        List<EventClientLink> ecl = pk.getEventClientLinkList();

        // List<EventClientLink> eventClientLinkList = 
        return null;
    }

// получить лист НЕ назначиных клиентов
    public List<EventClientLink> getUnassignedEventClientLink(Long eventId) {
        List<EventClientLink> eventClientLinkList = eventClientLinkDao.getUnassignedEventClientLink(eventId);

        return eventClientLinkList;

    }
}
