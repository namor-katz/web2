package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private static AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());
    private static AtomicLong maxIdAuth = new AtomicLong(0);

    /* список авторизованных пользователей */
    //default constructor
    private UserService() {
        dataBase =  Collections.synchronizedMap(new HashMap<>());
        maxId = new AtomicLong(0);
        authMap = Collections.synchronizedMap(new HashMap<>());
    }

    private static UserService instance;//для скрытия конструктора

    //замена скрытого конструктора.
    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getAllUsers() {   //V
        //получаем из мапы все значения, суем их в лист.
        Collection<User> values = dataBase.values();
        ArrayList<User> fromReturn = new ArrayList<>(values);
        return fromReturn;
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {

        if(isExistsThisUser(user)) {
            System.out.println("это аддузер. Такой пользователь уже имеется. не буду добавлять.");
            return false;
        }

        try {
            long res = maxId.getAndIncrement();
            user.setId(res);
            dataBase.put(res, user);
            System.out.println("с вами юзер сервис. йузер добавлен с мейлом! " + user.getEmail());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteAllUser() {
        dataBase.clear();
        //authMap.clear();
    }

    public boolean isExistsThisUser(User user) {
//        return dataBase.containsValue(user);
        //это не сработает, если пароль был изменён
//        String email = user.getEmail();
        if (user != null) {
            return dataBase.values().stream().anyMatch(x -> x.getEmail().equals(user.getEmail()));
            //return true;
        }
        return false;
    }

    public List<User> getAllAuth() {
        Collection<User> values = authMap.values();
        ArrayList<User> fromReturn = new ArrayList<>(values);
        return fromReturn;
        //        return  null;
    }

    public boolean authUser(User user) {
        if (isExistsThisUser(user)) {
            System.out.println("пробую авторизовать пользователя " + user.getEmail());
            long res = maxIdAuth.getAndIncrement();
            authMap.put(res, user);
//        Collection<User> values = authMap.values();
//        for(User v : values) {
//            if(user == v) {
//                return true;
            //           }
            //       }
            return true;
        } else return false;
    }
    
    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        try {
            authMap.containsValue(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static void main(String[] args) {

    }
}
