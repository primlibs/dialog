/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.CampaignDao;
import dao.EventCommentDao;
import dao.EventDao;
import dao.FailReasonDao;
import dao.ModuleEventClientDao;
import entities.CabinetUser;
import entities.Campaign;
import entities.Event;
import entities.FailReason;
import entities.Module;
import entities.User;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import support.StringAdapter;

/**
 *
 * @author bezdatiuzer
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReportService extends PrimService {
    
    @Autowired
    private ModuleEventClientDao moduleEventClientDao;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private EventDao eventDao;
    
    @Autowired
    private FailReasonDao failReasonDao;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ModuleService moduleService;
    
    @Autowired
    private CampaignDao campaignDao;
    
    @Autowired
    private EventCommentDao eventCommentDao;
    
    public LinkedHashMap<Module,String> getDataByModules(Long campaignId,Long pkId){
        LinkedHashMap<Module,String> res = new LinkedHashMap();
        HashMap<Long,BigDecimal> preRes = new HashMap();
        //List<Object[]> allModulesRawRes= moduleEventClientDao.getAllCountedModules(campaignId,pkId);
        List<Object[]> failedModulesRawRes= moduleEventClientDao.getFailedCountedModules(campaignId,pkId);
        Long allFailsCount = moduleEventClientDao.getCountFailsByCampaign(campaignId, pkId);
        BigDecimal afc = BigDecimal.valueOf(allFailsCount);
        ArrayList<BigDecimal[]>supListForSort=new ArrayList();
        for(Object[]o:failedModulesRawRes){
            BigInteger id=(BigInteger)o[0];
            BigInteger failCount = (BigInteger)o[1];
            BigDecimal[] supArr= new BigDecimal[2];
            preRes.put(id.longValue(),BigDecimal.valueOf(failCount.longValue()));
            supArr[0]=BigDecimal.valueOf(id.longValue());
            if(!afc.equals(BigDecimal.valueOf(0))){
                    supArr[1]=BigDecimal.valueOf(failCount.longValue()).divide(afc, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }else{
                    supArr[1]=BigDecimal.valueOf(failCount.longValue());
                }
            supListForSort.add(supArr);
        }
        
        Collections.sort(supListForSort,new specByPercentComparator());
        
        HashMap<Long,Module>modules=moduleService.getAllModulesMap(pkId);
        
        for(BigDecimal[] bd:supListForSort){
            Long moduleId = bd[0].longValue();
            Module m = modules.get(moduleId);
            if(m!=null){
                res.put(m,preRes.get(moduleId)+"("+StringAdapter.getString(bd[1])+"%)");
            }else{
                addError("Не найден модуль с ид: "+bd[0]+"; ");
            }
        }
        
        return res;
    }
    
    public List<Event> getEventDetalisationByModuleId(Long moduleId,Long campaignId,Long pkId){
        if(moduleId!=null&&campaignId!=null&&pkId!=null){
            return moduleEventClientDao.getFailedEventsByFinishingModuleIdAndCampaignId(moduleId,campaignId,pkId);
        }
        if(moduleId==null){
            addError("Ид модуля не передан.");
        }
        if(campaignId==null){
            addError("Ид кампании не передан.");
        }
        if(pkId==null){
            addError("Ошибка личного кабинета.");
        }
        return new ArrayList();
    }
    
    public HSSFWorkbook getModuleReportDetalisationXls(Long moduleId,Long campaignId,Long pkId) throws Exception{
        List<Event> rawData = getEventDetalisationByModuleId(moduleId, campaignId, pkId);
        String listName = "детализация по модулям";
        
        HSSFWorkbook workbook = new HSSFWorkbook();

        int n = 0;
        HSSFSheet sheet = workbook.createSheet(listName);
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
        for(Event ev:rawData){
            HSSFRow rowbody = sheet.createRow((short) n);
            
            String date = "";
            if(ev.getSetStatusDate()!=null){
                date = DateAdapter.formatByDate(ev.getSetStatusDate(), DateAdapter.FULL_FORMAT);
            }
            String finalDate = " - ";
            if(ev.getSuccessDate()!=null){
                finalDate = DateAdapter.formatByDate(ev.getSuccessDate(), DateAdapter.FULL_FORMAT);
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
            rowbody.createCell(r++).setCellValue(finalDate);
            rowbody.createCell(r++).setCellValue(ev.getFinalComment());
            n++;
        }
        return workbook;
    }
    
    private class specByPercentComparator implements Comparator<BigDecimal[]> {
        @Override
        public int compare(BigDecimal[] a, BigDecimal[] b) {
            if(a!=null&&b!=null){
                return b[1].compareTo(a[1]);
            }else{
                return 0;
            }
        }
    }
    
    //Данные для отчета по работе
    /*public LinkedHashMap<String,HashMap<String,String>> getDataForWorkReport(Long pkId,Long campaignId,Date dateFrom,Date dateTo){
        LinkedHashMap<String,HashMap<String,String>>res = new LinkedHashMap();
        if(campaignId!=null){
            Campaign campaign = campaignDao.find(campaignId);
            if(dateFrom==null){
                dateFrom=campaign.getCreationDate();
            }
            if(dateTo==null){
                dateTo=campaign.getEndDate();
                if(dateTo==null){
                    dateTo=new Date();
                }
            }
            dateFrom=DateAdapter.getStartOfDate(dateFrom);
            dateTo=DateAdapter.getEndOfDate(dateTo);
            HashMap<Long,BigDecimal>failMap=new HashMap();
            HashMap<Long,BigDecimal>successMap=new HashMap();
            HashMap<Long,BigDecimal>postponeMap=new HashMap();
            List<Object[]>flist=eventCommentDao.getUserIdFailedCount(campaign, dateFrom, dateTo, pkId);
            for(Object[] o:flist){
                BigInteger userId=(BigInteger)o[0];
                BigInteger count=(BigInteger)o[1];
                failMap.put(userId.longValue(),BigDecimal.valueOf(count.longValue()));
            }
            List<Object[]>slist=eventCommentDao.getUserIdSuccessfulCount(campaign, dateFrom, dateTo, pkId);
            for(Object[] o:slist){
                BigInteger userId=(BigInteger)o[0];
                BigInteger count=(BigInteger)o[1];
                successMap.put(userId.longValue(),BigDecimal.valueOf(count.longValue()));
            }
            List<Object[]>plist=eventCommentDao.getUserIdPostponesCount(campaign, dateFrom, dateTo, pkId);
            for(Object[] o:plist){
                BigInteger userId=(BigInteger)o[0];
                BigInteger count=(BigInteger)o[1];
                postponeMap.put(userId.longValue(),BigDecimal.valueOf(count.longValue()));
            }
            LinkedHashMap<Long,User>users=userService.getMakingCallsAndParticipatedUsersMap(pkId);
            BigDecimal sumFcount=BigDecimal.valueOf(0);
            BigDecimal sumScount=BigDecimal.valueOf(0);
            BigDecimal sumPcount=BigDecimal.valueOf(0);
            for(Map.Entry<Long,User>entry:users.entrySet()){
                Long userId=entry.getKey();
                HashMap<String,String>supMap=new HashMap();
                
                BigDecimal fcount = failMap.get(userId);
                if(fcount==null){
                    fcount=BigDecimal.valueOf(0);
                }
                sumFcount=sumFcount.add(fcount);
                BigDecimal scount = successMap.get(userId);
                if(scount==null){
                    scount=BigDecimal.valueOf(0);
                }
                sumScount=sumScount.add(scount);
                BigDecimal pcount = postponeMap.get(userId);
                if(pcount==null){
                    pcount=BigDecimal.valueOf(0);
                }
                sumPcount=sumPcount.add(pcount);
                
                supMap.put("failed",StringAdapter.getString(fcount));
                supMap.put("successful",StringAdapter.getString(scount));
                supMap.put("postponed",StringAdapter.getString(pcount));
                res.put(entry.getValue().getShortName(),supMap);
            }
            HashMap<String,String>sumMap=new HashMap();
            sumMap.put("failed",StringAdapter.getString(sumFcount));
            sumMap.put("successful",StringAdapter.getString(sumScount));
            sumMap.put("postponed",StringAdapter.getString(sumPcount));
            res.put("Итого:", sumMap);
        }
        return res;
    }*/
    
    public LinkedHashMap<User,HashMap<String,String>> getDataForWorkReport(Long pkId,Long campaignId,Date dateFrom,Date dateTo){
        LinkedHashMap<User,HashMap<String,String>>res = new LinkedHashMap();
        if(campaignId!=null){
            //LinkedHashMap<Long,User>users=userService.getMakingCallsAndParticipatedUsersMap(pkId);
            /*List<CabinetUser>cusers=eventService.getSurnameSortedCUListForCampaignSpecification(campaignId,pkId);
            LinkedHashMap<Long,User>users=new LinkedHashMap();
            for(CabinetUser cuser:cusers){
                users.put(cuser.getUser().getId(), cuser.getUser());
            }*/
            LinkedHashMap<Long,User>users=eventService.getUserWithAssignsMap(campaignId, pkId);
            
            Campaign campaign = campaignDao.find(campaignId);
            if(dateFrom==null){
                dateFrom=campaign.getCreationDate();
            }
            if(dateTo==null){
                dateTo=campaign.getEndDate();
                if(dateTo==null){
                    dateTo=new Date();
                }
            }
            dateFrom=DateAdapter.getStartOfDate(dateFrom);
            dateTo=DateAdapter.getEndOfDate(dateTo);
            HashMap<Long,BigDecimal>failMap=new HashMap();
            HashMap<Long,BigDecimal>successMap=new HashMap();
            HashMap<Long,BigDecimal>postponeMap=new HashMap();
            List<Object[]>flist=eventCommentDao.getUserIdFailedCount(campaign, dateFrom, dateTo, pkId);
            for(Object[] o:flist){
                BigInteger userId=(BigInteger)o[0];
                BigInteger count=(BigInteger)o[1];
                failMap.put(userId.longValue(),BigDecimal.valueOf(count.longValue()));
            }
            List<Object[]>slist=eventCommentDao.getUserIdSuccessfulCount(campaign, dateFrom, dateTo, pkId);
            for(Object[] o:slist){
                BigInteger userId=(BigInteger)o[0];
                BigInteger count=(BigInteger)o[1];
                successMap.put(userId.longValue(),BigDecimal.valueOf(count.longValue()));
            }
            List<Object[]>plist=eventCommentDao.getUserIdPostponesCount(campaign, dateFrom, dateTo, pkId);
            for(Object[] o:plist){
                BigInteger userId=(BigInteger)o[0];
                Long luserId = userId.longValue();
                BigInteger count=(BigInteger)o[1];
                
                BigDecimal scount = successMap.get(luserId);
                BigDecimal fcount = failMap.get(luserId);
                BigDecimal pcount = BigDecimal.valueOf(count.longValue());
                postponeMap.put(luserId,pcount);
                //addError("id="+luserId+" s="+scount+" f="+fcount+" p="+pcount);
                /*if((scount==null||scount.equals(nul))&&(fcount==null||fcount.equals(nul))&&(pcount==null||pcount.equals(nul))){
                    users.remove(luserId);
                }*/
            }
            
            BigDecimal sumFcount=BigDecimal.valueOf(0);
            BigDecimal sumScount=BigDecimal.valueOf(0);
            BigDecimal sumPcount=BigDecimal.valueOf(0);
            for(Map.Entry<Long,User>entry:users.entrySet()){
                
                BigDecimal nul = BigDecimal.valueOf(0);
                Long userId=entry.getKey();
                //addError("userID="+userId);
                HashMap<String,String>supMap=new HashMap();
                
                BigDecimal fcount = failMap.get(userId);
                if(fcount==null){
                    fcount=BigDecimal.valueOf(0);
                }
                sumFcount=sumFcount.add(fcount);
                BigDecimal scount = successMap.get(userId);
                if(scount==null){
                    scount=BigDecimal.valueOf(0);
                }
                sumScount=sumScount.add(scount);
                BigDecimal pcount = postponeMap.get(userId);
                if(pcount==null){
                    pcount=BigDecimal.valueOf(0);
                }
                sumPcount=sumPcount.add(pcount);
                
                supMap.put("failed",StringAdapter.getString(fcount));
                supMap.put("successful",StringAdapter.getString(scount));
                supMap.put("postponed",StringAdapter.getString(pcount));
                supMap.put("all",StringAdapter.getString(fcount.add(scount).add(pcount)));
                //if(!(scount.equals(nul)&&fcount.equals(nul)&&pcount.equals(nul))){
                    res.put(entry.getValue(),supMap);
                //}
            }
            HashMap<String,String>sumMap=new HashMap();
            sumMap.put("failed",StringAdapter.getString(sumFcount));
            sumMap.put("successful",StringAdapter.getString(sumScount));
            sumMap.put("postponed",StringAdapter.getString(sumPcount));
            sumMap.put("all",StringAdapter.getString(sumFcount.add(sumScount).add(sumPcount)));
            res.put(null, sumMap);
        }
        return res;
    }
    
    public LinkedHashMap<FailReason,String> getDataForFailReasonReport(Long campaignId,Long pkId){
        List<Object[]>rawFRRData=failReasonDao.getDataForFailReasonReport(campaignId, pkId);
        List<BigDecimal[]>listForSort=new ArrayList();
        LinkedHashMap<FailReason,String> res = new LinkedHashMap();
        Campaign c = campaignDao.find(campaignId);
        if(c!=null){
            List<FailReason>freasons=failReasonDao.getAllFailReasons(c.getStrategy().getId(), pkId);
            HashMap<Long,FailReason>frMap=new HashMap();
            BigDecimal sum = BigDecimal.valueOf(0);
            for(FailReason fr:freasons){
                frMap.put(fr.getId(), fr);
            }
            for(Object[] o:rawFRRData){
                BigInteger biid = (BigInteger)o[0];
                if(biid!=null){
                    FailReason fr=frMap.get(biid.longValue());
                    if(fr!=null){
                        BigInteger bicount = (BigInteger)o[1];
                        BigDecimal[] supArr=new BigDecimal[2];
                        BigDecimal count = BigDecimal.valueOf(bicount.longValue());
                        sum=sum.add(count);
                        supArr[0]=BigDecimal.valueOf(biid.longValue());
                        supArr[1]=count;
                        listForSort.add(supArr);
                    }else{
                        addError("Не удалось найти причину с ИД:"+biid);
                    }
                }
            }
            Collections.sort(listForSort,new specByPercentComparator());
            if(!sum.equals(BigDecimal.valueOf(0))){
                for(BigDecimal[] arr:listForSort){
                    BigDecimal id = arr[0];
                    BigDecimal count = arr[1];
                    FailReason fr = frMap.get(id.longValue());
                    res.put(fr, count.toString()+"("+count.multiply(BigDecimal.valueOf(100).divide(sum, 2, RoundingMode.HALF_UP))+"%)");
                }
            }else{
                for(BigDecimal[] arr:listForSort){
                    BigDecimal id = arr[0];
                    FailReason fr = frMap.get(id.longValue());
                    res.put(fr, "0(0%)");
                }
            }
            res.put(null,sum+"(100%)");
        }else{
            addError("Не удалось найти кампанию с ИД:"+campaignId);
        }
        return res;
    }
    
    public List<Event> getDataForFailReasonDeatlisation(Long failReasonId,Long campaignId,Long pkId){
        if(campaignId!=null&&pkId!=null){
            return eventDao.getFailedEvents(failReasonId, campaignId, pkId);
        }
        if(campaignId==null){
            addError("Ид кампании не передан.");
        }
        if(pkId==null){
            addError("Ошибка личного кабинета.");
        }
        return new ArrayList();
    }
    
    public HSSFWorkbook getFailReasonReportDetalisationXls(Long failReasonId,Long campaignId,Long pkId) throws Exception{
        List<Event> rawData = getDataForFailReasonDeatlisation(failReasonId, campaignId, pkId);
        String listName = "детализация по причинам";
        
        HSSFWorkbook workbook = new HSSFWorkbook();

        int n = 0;
        HSSFSheet sheet = workbook.createSheet(listName);
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
        for(Event ev:rawData){
            HSSFRow rowbody = sheet.createRow((short) n);
            
            String date = "";
            if(ev.getSetStatusDate()!=null){
                date = DateAdapter.formatByDate(ev.getSetStatusDate(), DateAdapter.FULL_FORMAT);
            }
            String finalDate = " - ";
            if(ev.getSuccessDate()!=null){
                finalDate = DateAdapter.formatByDate(ev.getSuccessDate(), DateAdapter.FULL_FORMAT);
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
            rowbody.createCell(r++).setCellValue(finalDate);
            rowbody.createCell(r++).setCellValue(ev.getFinalComment());
            n++;
        }
        return workbook;
    }
    
    public List<Event> getDataForWorkDetalisation(Integer status,Date dateFrom,Date dateTo,Long userId,Long campaignId,Long pkId){
        if(campaignId!=null){
            Campaign c = campaignDao.find(campaignId);
            if(dateFrom==null){
                dateFrom=c.getCreationDate();
            }
            if(dateTo==null){
                dateTo=c.getEndDate();
                if(dateTo==null){
                    dateTo=new Date();
                }
            }
            dateFrom=DateAdapter.getStartOfDate(dateFrom);
            dateTo=DateAdapter.getEndOfDate(dateTo);
            return eventCommentDao.getEventsForWorkReport(status, dateFrom, dateTo, userId, campaignId, pkId);
        }else{
            addError("Ид кампании не передан");
            return new ArrayList();
        }
    }
    
    public HSSFWorkbook getWorkReportDetalisationXls(Integer status,Date dateFrom,Date dateTo,Long userId,Long campaignId,Long pkId) throws Exception{
        List<Event> rawData = getDataForWorkDetalisation(status,dateFrom,dateTo,userId, campaignId, pkId);
        String listName = "отчет по работе";
        if(userId!=null){
            User u = userService.getUser(userId);
            listName+=" "+u.getShortName();
        }else{
            listName+=" итог";
        }
        
        HSSFWorkbook workbook = new HSSFWorkbook();

        int n = 0;
        HSSFSheet sheet = workbook.createSheet(listName);
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
        for(Event ev:rawData){
            HSSFRow rowbody = sheet.createRow((short) n);
            
            String date = "";
            if(ev.getSetStatusDate()!=null){
                date = DateAdapter.formatByDate(ev.getSetStatusDate(), DateAdapter.FULL_FORMAT);
            }
            String finalDate = " - ";
            if(ev.getSuccessDate()!=null){
                finalDate = DateAdapter.formatByDate(ev.getSuccessDate(), DateAdapter.FULL_FORMAT);
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
            rowbody.createCell(r++).setCellValue(finalDate);
            rowbody.createCell(r++).setCellValue(ev.getFinalComment());
            n++;
        }
        return workbook;
    }
    
}
