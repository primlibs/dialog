/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.CampaignDao;
import dao.EventCommentDao;
import dao.ModuleDao;
import dao.ModuleEventClientDao;
import dao.UserDao;
import entities.Campaign;
import entities.Event;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
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
    private ModuleDao moduleDao;
    
    @Autowired
    private UserDao userDao;
    
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
        /*for(Object[]o:failedModulesRawRes){
            BigInteger id=(BigInteger)o[0];
            BigInteger failCount = (BigInteger)o[1];
            preRes.put(id.longValue(),BigDecimal.valueOf(failCount.longValue()));
        }
        for(Object[]o:allModulesRawRes){
            BigInteger id=(BigInteger)o[0];
            BigInteger allCount = (BigInteger)o[1];
            BigDecimal all=BigDecimal.valueOf(allCount.longValue());
            BigDecimal fails = preRes.get(id.longValue());
            if(fails!=null){
                BigDecimal[] supArr= new BigDecimal[2];
                supArr[0]=BigDecimal.valueOf(id.longValue());
                if(!all.equals(BigDecimal.valueOf(0))){
                    supArr[1]=fails.divide(all, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }else{
                    supArr[1]=all;
                }
                supListForSort.add(supArr);
            }
        }*/
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
    
    
    
    /*private LinkedHashMap<Long,String>getUserMapTemplate(HashSet<Long>usedUserIds,Long pkId){
        LinkedHashMap<Long,String>res = new LinkedHashMap();
        res.put((long)0,"0(0%)");
        LinkedHashMap<Long,User>users=userService.getMakingCallsAndParticipatedUsersMap(pkId);
        for(Map.Entry<Long,User> entry:users.entrySet()){
            Long userId = entry.getKey();
            if(usedUserIds.contains(userId)){
                res.put(userId,"0(0%)");
            }
        }
        return res;
    }*/
    
    /*public ArrayList<User>getUserList(Long pkId){
        
        LinkedHashMap<Module,LinkedHashMap<Long,String>>fullRes = getDatabyModules(pkId);
        if(!fullRes.isEmpty()){
            LinkedHashMap<Long,User>availUsers=userService.getMakingCallsAndParticipatedUsersMap(pkId);
            LinkedHashMap<Long,String>userMap=new LinkedHashMap();
            for(LinkedHashMap<Long,String>map:fullRes.values()){
                userMap=map;
                break;
            }
            if(!userMap.isEmpty()){
                ArrayList users=new ArrayList();
                for(Long u:userMap.keySet()){
                    User user = availUsers.get(u);
                    if(user!=null){
                        users.add(user);
                    }
                }
                return users;
            }
        }
        
        return new ArrayList();
    }*/
    
    /*public LinkedHashMap<Module,LinkedHashMap<Long,String>>getDatabyModules(Long pkId){
        
        HashMap<Long,HashMap<Long,BigDecimal[]>>rawRes=new HashMap();
        LinkedHashMap<Module,LinkedHashMap<Long,String>>result=new LinkedHashMap();
        
        List<Object[]> failedCountListByUser = moduleEventClientDao.getCountedFailedEventsModuleDataByUser(pkId);
        List<Object[]> failedCountListOverAll = moduleEventClientDao.getCountedFailedEventsModuleDataByAll(pkId);
        
        List<Object[]> allCountListByUser = moduleEventClientDao.getCountedAllEventsIncludingModulesDataByUser(pkId);
        List<Object[]> allCountListOverAll = moduleEventClientDao.getCountedFailedEventsModuleDataByAll(pkId);
        
        HashSet<Long>usedUserIds=new HashSet();
        
        for(Object[] o:failedCountListByUser){
            BigInteger moduleId = (BigInteger)o[0];
            BigInteger userId=(BigInteger)o[1];
            usedUserIds.add(userId.longValue());
            BigInteger failed = (BigInteger)o[2];
            BigDecimal f = BigDecimal.valueOf(failed.longValue());
            HashMap<Long,BigDecimal[]>rawUserMap=rawRes.get(moduleId.longValue());
            if(rawUserMap==null){
                rawUserMap=new HashMap();
            }
            BigDecimal[] bdm = new BigDecimal[2];
            bdm[0]=BigDecimal.valueOf(failed.longValue());
            rawUserMap.put(userId.longValue(),bdm);
            rawRes.put(moduleId.longValue(), rawUserMap);
        }
        
        for(Object[] o:allCountListByUser){
            BigInteger moduleId = (BigInteger)o[0];
            BigInteger userId=(BigInteger)o[1];
            BigInteger allEvs = (BigInteger)o[2];
            HashMap<Long,BigDecimal[]>rawUserMap=rawRes.get(moduleId.longValue());
            if(rawUserMap!=null){
                BigDecimal[] bdm=rawUserMap.get(userId.longValue());
                if(bdm!=null){
                    BigDecimal a = BigDecimal.valueOf(allEvs.longValue());
                    if(!a.equals(BigDecimal.valueOf(0))&&bdm[0]!=null){
                        bdm[1]=bdm[0].divide(a, 2, RoundingMode.HALF_UP);
                    }
                    rawUserMap.put(userId.longValue(),bdm);
                    rawRes.put(moduleId.longValue(), rawUserMap);
                }
            }
        }
        
        HashMap<Long,BigDecimal[]>rawSumResMap=new HashMap();
        //лист для сортировки модулей по % отношению сливов к общему числу эвентов с этим модулем, 0-moduleId,1 - число сливов,2 - % отношение
        List<BigDecimal[]>listForSort=new ArrayList();
        
        for(Object[]o:failedCountListOverAll){
            BigInteger intId = (BigInteger)o[0];
            Long moduleId = intId.longValue();
            BigInteger failed = (BigInteger)o[1];
            BigDecimal failCount = BigDecimal.valueOf(failed.longValue());
            BigDecimal[] entry=new BigDecimal[2];
            //entry[0]=BigDecimal.valueOf(moduleId);
            entry[0]=failCount;
            rawSumResMap.put(moduleId, entry);
        }
        for(Object[]o:allCountListOverAll){
            BigInteger intId = (BigInteger)o[0];
            Long moduleId = intId.longValue();
            BigInteger all = (BigInteger)o[1];
            BigDecimal allCount = BigDecimal.valueOf(all.longValue());
            BigDecimal[] entry = rawSumResMap.get(moduleId);
            if(entry!=null){
                if(!allCount.equals(BigDecimal.valueOf(0))){
                    entry[1]=entry[0].divide(allCount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }else if(entry[0].equals(BigDecimal.valueOf(0))){
                    entry[1]=BigDecimal.valueOf(0);
                }else{   
                    entry[1]=null;
                    addError("Возникла непредвиденная ошибка при подсчете данных, попытка разделить на 0. Общее количество контактов по модулю ид:"+moduleId+" равно 0.");
                }
                listForSort.add(entry);
            }
            //rawSumResMap.put(moduleId,entry);
        }
        
        LinkedHashMap<Long,String> userMapTemplate = getUserMapTemplate(usedUserIds,pkId);
        
        Collections.sort(listForSort,new specByPercentComparator());
        HashMap<Long,Module>modules=moduleService.getAllModulesMap(pkId);
        
        for(BigDecimal[] bdm:listForSort){
            Long moduleId=bdm[0].longValue();
            Module m = modules.get(moduleId);
            LinkedHashMap<Long,String>userResMap=new LinkedHashMap(userMapTemplate);
            userResMap.put((long)0,StringAdapter.getString(bdm[0])+"("+bdm[1]+"%)");
            
            HashMap<Long,BigDecimal[]>supMap=rawRes.get(moduleId);
            if(supMap!=null){
                for(Map.Entry<Long,BigDecimal[]>entry:supMap.entrySet()){
                    userResMap.put(entry.getKey(), StringAdapter.getString(entry.getValue()[0])+"("+StringAdapter.getString(entry.getValue()[1])+"%)");
                }
            }
            result.put(m,userResMap);
        }
        
        return result;
    }*/
    
    //возвращает map ключ - ид модуля, значение - map, где ключ - ид юзера, значение - его результат.
    /*public LinkedHashMap<Long,LinkedHashMap<Long,String>> getDataForModuleReport(Long pkId){
        
        LinkedHashMap<Long,LinkedHashMap<Long,String>>res=new LinkedHashMap();
        
        List<Object[]> failedCountList = moduleEventClientDao.getCountedFailedEventsModuleDataByUser(pkId);
        List<Object[]> failedCountListForAll = moduleEventClientDao.getCountedFailedEventsModuleDataByAll(pkId);
        
        List<Object[]> allCountList = moduleEventClientDao.getCountedAllEventsIncludingModulesDataByUser(pkId);
        List<Object[]> allCountListForAll = moduleEventClientDao.getCountedFailedEventsModuleDataByAll(pkId);
        
        HashMap<Long,BigDecimal[]>supMap=new HashMap();
        //лист для сортировки модулей по % отношению сливов к общему числу эвентов с этим модулем, 0-moduleId,1 - число сливов,2 - % отношение
        List<BigDecimal[]>listForSort=new ArrayList();
        
        for(Object[]o:failedCountListForAll){
            BigInteger intId = (BigInteger)o[0];
            Long moduleId = intId.longValue();
            BigInteger failed = (BigInteger)o[1];
            BigDecimal failCount = BigDecimal.valueOf(failed.longValue());
            BigDecimal[] entry=new BigDecimal[3];
            entry[0]=BigDecimal.valueOf(moduleId);
            entry[1]=failCount;
            supMap.put(moduleId, entry);
        }
        for(Object[]o:allCountListForAll){
            BigInteger intId = (BigInteger)o[0];
            Long moduleId = intId.longValue();
            BigInteger all = (BigInteger)o[1];
            BigDecimal allCount = BigDecimal.valueOf(all.longValue());
            BigDecimal[] entry = supMap.get(moduleId);
            if(entry!=null){
                if(!allCount.equals(BigDecimal.valueOf(0))){
                    entry[2]=entry[1].divide(allCount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }else if(!entry[0].equals(BigDecimal.valueOf(0))){
                    entry[2]=BigDecimal.valueOf(0);
                }else{   
                    entry[2]=null;
                    addError("Возникла непредвиденная ошибка при подсчете данных, попытка разделить на 0. Общее количество контактов по модулю ид:"+moduleId+" равно 0.");
                }
                listForSort.add(entry);
            }
        }
        
        Collections.sort(listForSort,new specByPercentComparator());
        
        for(BigDecimal[] bdm:listForSort){
            Long moduleId=bdm[0].longValue();
            LinkedHashMap<Long,String>usersMap=new LinkedHashMap();
            usersMap.put((long)0,StringAdapter.getString(bdm[1])+"("+bdm[2]+"%)");
            res.put(moduleId, usersMap);
        }
        
        HashMap<Long,HashMap<Long,BigDecimal[]>>supModuleIdSumFailedMap=new HashMap();
        
        HashSet<Long>usedUserIdSet = new HashSet();
        
        for(Object[] o:failedCountList){
            BigInteger moduleId = (BigInteger)o[0];
            BigInteger userId=(BigInteger)o[1];
            usedUserIdSet.add(userId.longValue());
            BigInteger failed = (BigInteger)o[2];
            BigDecimal f = BigDecimal.valueOf(failed.longValue());
            HashMap<Long,BigDecimal[]>supUserIdMap=supModuleIdSumFailedMap.get(moduleId.longValue());
            if(supUserIdMap==null){
                supUserIdMap=new HashMap();
            }
            BigDecimal[] bdm = new BigDecimal[2];
            bdm[0]=BigDecimal.valueOf(failed.longValue());
            supUserIdMap.put(userId.longValue(),bdm);
            supModuleIdSumFailedMap.put(moduleId.longValue(), supUserIdMap);
        }
        for(Object[] o:allCountList){
            BigInteger moduleId = (BigInteger)o[0];
            BigInteger userId=(BigInteger)o[1];
            BigInteger all = (BigInteger)o[2];
            HashMap<Long,BigDecimal[]>supUserIdMap=supModuleIdSumFailedMap.get(moduleId.longValue());
            if(supUserIdMap!=null){
                BigDecimal[] bdm=supUserIdMap.get(userId.longValue());
                if(bdm!=null){
                    BigDecimal a = BigDecimal.valueOf(all.longValue());
                    bdm[1]=bdm[0].divide(a, 2, RoundingMode.HALF_UP);
                    supUserIdMap.put(userId.longValue(),bdm);
                    supModuleIdSumFailedMap.put(moduleId.longValue(), supUserIdMap);
                }
            }
        }
        
        LinkedHashMap<Long,User>users=userService.getMakingCallsAndParticipatedUsersMap(pkId);
        LinkedHashMap<Long,String>userMapTemplate=new LinkedHashMap();
        for(Map.Entry<Long,User> entry:users.entrySet()){
            Long userId = entry.getKey();
            if(usedUserIdSet.contains(userId)){
                userMapTemplate.put(userId,"0(0%)");
            }
        }
        
        for(Map.Entry<Long,HashMap<Long,BigDecimal[]>> moduleMapEntry:supModuleIdSumFailedMap.entrySet()){
            Long moduleId=moduleMapEntry.getKey();
            HashMap<Long,BigDecimal[]>userSupValuesMap=moduleMapEntry.getValue();
            LinkedHashMap<Long,String>userResMap=new LinkedHashMap(userMapTemplate);
            for(Map.Entry<Long,BigDecimal[]>userSupValueEntry:userSupValuesMap.entrySet()){
                userResMap.put(userSupValueEntry.getKey(),StringAdapter.getString(userSupValueEntry.getValue()[0])+
                        "("+StringAdapter.getString(userSupValueEntry.getValue()[1])+"%)");
            }
            res.put(moduleId, userResMap);
        }
        return res;
    }
    
    public LinkedHashMap<Module,LinkedHashMap<User,String>> getFullDataForModuleReport(Long pkId){
        LinkedHashMap<Module,LinkedHashMap<User,String>>res=new LinkedHashMap();
        LinkedHashMap<Long,User>users=userService.getMakingCallsAndParticipatedUsersMap(pkId);
        HashMap<Long,Module>modules=moduleService.getAllModulesMap(pkId);
        
        for(Map.Entry<Long,LinkedHashMap<Long,String>>mentry:getDataForModuleReport(pkId).entrySet()){
            Module m=modules.get(mentry.getKey());
            LinkedHashMap<User,String>uresMap=new LinkedHashMap();
            for(Map.Entry<Long,String>uentry:mentry.getValue().entrySet()){
                User u = users.get(uentry.getKey());
                uresMap.put(u,uentry.getValue());
            }
            res.put(m, uresMap);
        }
        return res;
    }
    
    public ArrayList<User>getUserList(Long pkId){
        LinkedHashMap<Module,LinkedHashMap<User,String>>fullRes = getFullDataForModuleReport(pkId);
        if(!fullRes.isEmpty()){
            LinkedHashMap<User,String>userMap=new LinkedHashMap();
            for(LinkedHashMap<User,String>map:fullRes.values()){
                userMap=map;
                break;
            }
            if(!userMap.isEmpty()){
                ArrayList users=new ArrayList();
                for(User u:userMap.keySet()){
                    users.add(u);
                }
                return users;
            }
        }
        
        return new ArrayList();
    }*/
    
    
    
    /*public HashMap<Long,User> getUserMap(Long pkId){
        List<User> users = userDao.getUsers(pkId);
        
    }*/
    
    /*private LinkedHashMap<Module,LinkedHashMap<User,String[]>> getModuleMap(Long pkId){
        List<Module> allModules = moduleDao.getAllModules(pkId);
        LinkedHashMap<Module,LinkedHashMap<User,String[]>> res = new LinkedHashMap();
        
        for(Module m:allModules){
            LinkedHashMap<User,String[]> map = getMakingCallsAndParticipatedUserMap(pkId);
            res.put(m, map);
        }
        return res;
    }
    
    private LinkedHashMap<User,String[]> getMakingCallsAndParticipatedUserMap(Long pkId){
        List<User> users = userService.getMakingCallsAndParticipatedUsers(pkId);
        Collections.sort(users,new SurnameComparator());
        LinkedHashMap<User,String[]> res = new LinkedHashMap();
        for(User u:users){
            String[] arr = new String[3];
            arr[0]="0";
            arr[1]="0";
            arr[2]="0";
            res.put(u, arr);
        }
        return res;
    }
    
    private class SurnameComparator implements Comparator<User> {
        @Override
        public int compare(User a, User b) {
            return a.getSurname().compareToIgnoreCase(b.getSurname());
        }
    }*/
    
    
    
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
    public LinkedHashMap<String,HashMap<String,String>> getDataForWorkReport(Long pkId,Long campaignId,Date dateFrom,Date dateTo){
        LinkedHashMap<String,HashMap<String,String>>res = new LinkedHashMap();
        if(campaignId!=null){
            Campaign campaign = campaignDao.find(campaignId);
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
    }
    
}
