/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CabinetUserDao;
import dao.EventDao;
import dao.PersonalCabinetDao;
import dao.TarifDao;
import dao.UserDao;
import entities.CabinetUser;
import entities.Event;
import entities.PersonalCabinet;
import entities.Tarif;
import entities.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.parent.PrimService;
import support.AuthManager;
import support.Random;
import support.ServiceResult;
import support.editors.PhoneEditor;
import support.enums.ValidatorTypes;
import support.filterValidator.ChainValidator;

/**
 *
 * @author Юрий
 */
@Service
@Transactional
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService extends PrimService {

    final public String DEFAULT_PASS = "0000";

    @Autowired
    private UserDao userDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private TarifDao tarifDao;
    @Autowired
    private CabinetUserDao cabinetUserDao;
    @Autowired
    private AdminService adminService;
    @Autowired
    private PersonalCabinetDao personalCabinetDao;

    public User getUser(Long userId) {
        if (userId == null) {
            return null;
        } else {
            return userDao.find(userId);
        }
    }

    public void save(
            String company,
            String email,
            String phone,
            String password,
            String name,
            String surname,
            String patronymic,
            String emailCompany
    ) {

        PhoneEditor phe = new PhoneEditor();
        phone = phe.getPhone(phone);
        if ((email != null /*&& isValidMail(email)*/) && (password != null && password.length() > 3)) {
            User existingUser = userDao.getUserByLogin(email);

            if (existingUser != null) {
                addError("Пользователь с такой почтой уже зарегистрирован, выберите другую");
            }  else {
                if (name == null || name.equals("")) {
                    name = "Пользователь";
                }
                if (surname == null || surname.equals("")) {
                    surname = "Новый";
                }
                User user = new User();
                user.setEmail(email);
                user.setPassword(AuthManager.md5Custom(password));
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                if (validate(user)) {
                    if (company == null || company.equals("")) {
                        company = "Новая компания";
                    }
                    PersonalCabinet cabinet = new PersonalCabinet();
                    Tarif t = null;
                    Date endDate = null;
                    List<Tarif> ts = tarifDao.getDefaults();
                    if (!ts.isEmpty()) {
                        t = ts.get(0);
                        if (t.getDayLength() != null) {
                            Calendar c = Calendar.getInstance();
                            c.add(Calendar.DAY_OF_MONTH, t.getDayLength().intValue());
                            endDate = c.getTime();
                        }
                    }
                    cabinet.setEmail(email);
                    cabinet.setTarif(t);
                    cabinet.setEndDate(endDate);
                    cabinet.setBeginDate(new Date());
                    cabinet.setCompany(company);
                    cabinet.setPhone(phone);
                    if (validate(cabinet)) {
                        userDao.save(user);
                        personalCabinetDao.save(cabinet);
                        CabinetUser link = new CabinetUser();
                        link.setCabinet(cabinet);
                        link.setUser(user);

                        link.setUserRole("admin");
                        if (validate(link)) {
                            cabinetUserDao.save(link);
                        }
                    }
                }
            }
        } else {
            if (email == null || email.equals("")) {
                addError("Необходимо указать email");
            }
            if (password == null || password.length() < 4) {
                addError("Необходимо указать пароль длиной более 3 символов");
            }
        }
    }

    public boolean updateUserField(String field, Long cabinetUserId, String newVal, Long pkId) {
        boolean performed = false;
        Boolean cuRole = null;
        CabinetUser cu = cabinetUserDao.find(cabinetUserId);
        if (cu != null) {
            User user = cu.getUser();
            switch (field) {
                case "makingCalls":
                    if (newVal.equals("Нет")) {
                        if (eventDao.getPostponedEvents(user.getId(), pkId).isEmpty()) {
                            for (Event ev : eventDao.getActiveEvents(user.getId(), pkId)) {
                                ev.setUser(null);
                                ev.setStatus(Event.UNASSIGNED);
                                if (validate(ev)) {
                                    eventDao.update(ev);
                                }
                            }
                            cu.setMakesCalls(null);
                        } else {
                            addError("У пользователя есть отложенные контакты с клиентами.");
                        }
                    } else if (newVal.equals("Да")) {
                        cu.setMakesCalls((short) 1);
                    }
                    cuRole = true;
                    break;
                case "userRole":
                    if (newVal.equals("Пользователь")) {
                        if (isThisUserNotTheLastAdmin(cu, pkId)) {
                            cu.setUserRole("user");
                            cu.setMakesCalls((short) 1);
                        } else {
                            addError("В кабинете должен остаться хотя бы один администратор.");
                        }
                    } else if (newVal.equals("Администратор")) {
                        cu.setUserRole("admin");
                    }
                    cuRole = true;
                    break;
                case "surname":
                    user.setSurname(newVal);
                    cuRole = false;
                    break;
                case "name":
                    user.setName(newVal);
                    cuRole = false;
                    break;
                case "patronymic":
                    user.setPatronymic(newVal);
                    cuRole = false;
                    break;
                default:
                    cuRole = null;
                    break;
            }
            if (cuRole != null) {
                if (cuRole) {
                    if (validate(cu)) {
                        cabinetUserDao.update(cu);
                        performed = true;
                    }
                } else {
                    if (validate(user)) {
                        userDao.update(user);
                        performed = true;
                    }
                }
            }
            addError(field);
        } else {
            addError("Записи о пользователе в кабинете с ИД:" + cabinetUserId + " не найдено");
        }
        return performed;
    }

    public void addUser(
            String email,
            String phone,
            String name,
            String surname,
            String patronymic,
            String role,
            Object cabinetId) {
        if (adminService.mayAddUser((Long) cabinetId)) {
            User existingUser = userDao.getUserByLogin(email);
            PersonalCabinet cabinet = personalCabinetDao.find((long) cabinetId);

            if (existingUser != null) {

                boolean exist = existCabinetUser(existingUser, cabinetId);

                if (exist == false) {
                    CabinetUser link = new CabinetUser();
                    link.setCabinet(cabinet);
                    link.setUserRole(role);
                    if (role.equals("user")) {
                        link.setMakesCalls((short) 1);
                    }
                    link.setUser(existingUser);
                    if (validate(link)) {
                        cabinetUserDao.save(link);
                    }

                } else {
                    addError("Пользователь уже существует");
                }
            } else {
                User user = new User();
                user.setEmail(email);
                user.setPassword(AuthManager.md5Custom(DEFAULT_PASS));
                user.setName(name);
                user.setSurname(surname);
                user.setPatronymic(patronymic);
                if (validate(user)) {
                    userDao.save(user);
                }
                if (getErrors().isEmpty()) {
                    CabinetUser link = new CabinetUser();
                    link.setCabinet(cabinet);
                    link.setUserRole(role);
                    if (role.equals("user")) {
                        link.setMakesCalls((short) 1);
                    }
                    link.setUser(user);
                    if (validate(link)) {
                        cabinetUserDao.save(link);
                    }
                }
            }
        } else {
            addError("Вы не можете добавлять больше пользователей в связи с ограничениями тарифа");
        }
    }

    private boolean existCabinetUser(User existingUser, Object cabinetId) {
        List<CabinetUser> list = cabinetUserDao.getByUserAndCabinet(existingUser.getId(), (Long) cabinetId);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ServiceResult changePassword(
            String oldPassword,
            String password,
            String confirmPassword
    ) {
        ServiceResult result = new ServiceResult();
        User user = authManager.getCurrentUser();
        String oldHash = user.getPassword();
        String oldHashForm = AuthManager.md5Custom(oldPassword);

        if (oldHash.equals(oldHashForm)) {
            if (password.equals(confirmPassword)) {
                if (password.length() >= 4) {
                    user.setPassword(AuthManager.md5Custom(password));
                    userDao.save(user);

                } else {
                    result.addError("Длина пароля должна быть не менее 4 символов");
                }
            } else {
                result.addError("Значения паролей не совпадают");
            }
        } else {
            result.addError("Неправильно введен старый пароль");
        }
        return result;
    }

    public String recoveryPassword(String email) {
        if (email == null || email.equals("")) {
            addError("Нужно ввести адрес электронной почты");
            return null;
        }
        User user = userDao.getUserByLogin(email);
        if (user != null) {
            user.setRecoverDate(new Date());
            user.setRecoverHash(AuthManager.md5Custom(Random.getString("qwertyuiopasdfghjklzxcvbnm", 10)));
            if (validate(user)) {
                userDao.save(user);
            }
            return user.getRecoverHash();
        } else {
            addError("Пользователя не существует");
        }
        return null;
    }

    public void recoverPassword(String hash, String password, String confirmPassword) {

        User user = userDao.getUserByHash(hash);

        if (user != null) {
            if (password.equals(confirmPassword)) {

                Calendar cl = Calendar.getInstance();
                cl.add(Calendar.MINUTE, -120);
                Date now = cl.getTime();
                Date fromBd = user.getRecoverDate();

                if (now.before(fromBd) || now.equals(fromBd)) {

                    user.setPassword(AuthManager.md5Custom(password));
                    if (validate(user)) {
                        userDao.save(user);
                    }

                } else {
                    addError("время ссылки вышло");
                }
            } else {
                addError("пароли не совпадают");
            }
        } else {
            addError("пользователь не существует  ");
        }

    }

    public List<CabinetUser> cabinetUserList(Long cabinetId) {
        PersonalCabinet pk = personalCabinetDao.find(cabinetId);
        if (pk != null) {
            return personalCabinetDao.getActiveUserList(cabinetId);
        } else {
            addError("Кабинет не найден по ид " + cabinetId);
        }
        return new ArrayList();

    }

    /*public void deleteUser(Long cabinetUserId) {
     CabinetUser cabinenUser = cabinetUserDao.find(cabinetUserId);
        
     Date date = new Date();
     if (cabinenUser != null ) {
     User user = cabinenUser.getUser();
     for(Event ev:eventDao.getNotProcessedUserEvents(user.getId(), cabinenUser.getCabinet().getId())){
     ev.setUser(null);
     if(validate(ev)){
     eventDao.update(ev);
     }
     }
     cabinenUser.setDeleteDate(date);
     cabinetUserDao.update(cabinenUser);
     } else {
     addError("Юзер не найден по: " + cabinetUserId );
     }

     }*/
    public void deleteUser(Long cabinetUserIdtoDelete, Long cabinetUserIdtoAssign, Long thisUserId, Long pkId) {
        CabinetUser deletingCu = cabinetUserDao.getCUByIdAndCabinet(cabinetUserIdtoDelete, pkId);

        List<Event> eventsForUpdate = new ArrayList();

        Date date = new Date();
        if (deletingCu != null) {
            if (!deletingCu.getUser().getId().equals(thisUserId)) {
                User user = deletingCu.getUser();
                if (isThisUserNotTheLastAdmin(deletingCu, pkId)) {
                    if (!cabinetUserIdtoAssign.equals((long) 0)) {
                        CabinetUser assigningCu = cabinetUserDao.getCUByIdAndCabinet(cabinetUserIdtoAssign, pkId);
                        for (Event ev : eventDao.getActiveEvents(user.getId(), pkId)) {
                            ev.setUser(assigningCu.getUser());
                            if (validate(ev)) {
                                eventsForUpdate.add(ev);
                            }
                        }
                    } else {
                        for (Event ev : eventDao.getActiveEvents(user.getId(), pkId)) {
                            ev.setUnassignedUnPostponed();
                            if (validate(ev)) {
                                eventsForUpdate.add(ev);
                            }
                        }
                    }
                    deletingCu.setDeleteDate(date);
                    cabinetUserDao.update(deletingCu);
                    for (Event ev : eventsForUpdate) {
                        eventDao.update(ev);
                    }
                } else {
                    addError("В кабинете должен остаться хотя бы один администратор.");
                }
            } else {
                addError("Нельзя удалить самого себя");
            }
        } else {
            addError("Пользователь не найден по ИД: " + cabinetUserIdtoDelete);
        }
    }
    
     public void nullPass(Long cabinetUserId, Long userId, Long pkId) {
         CabinetUser cu = cabinetUserDao.find(cabinetUserId);
         if(cu.getCabinet().getId().equals(pkId)&&cu.getUser().getId().equals(userId)){
             User us = userDao.find(userId);
             us.setPassword(AuthManager.md5Custom(DEFAULT_PASS));
             userDao.update(us);
         }else{
             addError("Пользователь не принадлежит кабинету");
         }

    }

    private boolean isThisUserNotTheLastAdmin(CabinetUser deletingCu, Long pkId) {
        if (deletingCu.getUserRole().equals("admin")) {
            List<CabinetUser> admins = personalCabinetDao.getAdmins(pkId);
            if (!admins.isEmpty() && admins.size() > 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public LinkedHashMap<Long, User> getMakingCallsAndParticipatedUsersMap(Long pkId) {
        LinkedHashMap<Long, User> res = new LinkedHashMap();
        List<User> participatedUsers = userDao.getParticipatedUsers(pkId);
        HashSet<Long> ids = new HashSet();
        for (User u : participatedUsers) {
            ids.add(u.getId());
        }
        List<User> makingCallsUsers = userDao.getMakingCallsUsers(pkId);
        List<User> preResList = new ArrayList();
        preResList.addAll(participatedUsers);
        for (User u : makingCallsUsers) {
            if (!ids.contains(u.getId())) {
                preResList.add(u);
            }
        }
        Collections.sort(preResList, new SurnameComparator());
        for (User u : preResList) {
            res.put(u.getId(), u);
        }
        return res;
    }

    private class SurnameComparator implements Comparator<User> {

        @Override
        public int compare(User a, User b) {
            return a.getSurname().compareToIgnoreCase(b.getSurname());
        }
    }

    public void changeCabinetName(String newName, Long pkId) {
        PersonalCabinet pk = personalCabinetDao.find(pkId);
        if (newName != null && newName.length() > 0) {
            pk.setCompany(newName);
            if (validate(pk)) {
                personalCabinetDao.update(pk);
            }
        } else {
            addError("Новое название компании не передано.");
        }
    }

    public Boolean isValidMail(Object ob) {
        /*if(ob.getClass().equals(Double.class)){
         BigDecimal bd = BigDecimal.valueOf((double)ob);
         ob=bd.longValue();
         }*/
        ChainValidator ch = ChainValidator.getInstance(ValidatorTypes.MAILVALIDATOR);
        ch.execute(ob);
        if (ch.getErrors().isEmpty()) {
            return true;
        } else {
            for (String er : ch.getErrors()) {
                addError(er);
            }
            return false;
        }
    }

    public void saveContacts(String name,
            String email,
            String phone
    ) {
        if (!((email == null || email.equals("")) && (phone == null || phone.equals("")))) {
            PhoneEditor phe = new PhoneEditor();
            phone = phe.getPhone(phone);
            User u = new User();
            u.setEmail(email);
            u.setPassword("00001");
            u.setSurname(phone);
            u.setName(name);
            //u.setSurname(surname);
            if (validate(u)) {
                userDao.save(u);
            }
        } else {
            addError("Введите email или телефон");
        }
    }

    
    public static String getUserName() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public User getCurrentUser() throws Exception {
        User user=new User();
        user.setEmail(getUserName());
        List<User> userList = userDao.find(user);
        if(!userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }
}
