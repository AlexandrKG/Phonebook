package com.springboot.dao;


import com.springboot.domain.Phones;
import com.springboot.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Repository
@Transactional
@Qualifier("hibernateUserDao")

public class HibernateUserDao implements CustomerDAO {

    private static final String HQL_ALL_USERS;
    private static final String HQL_USERS_PASSWD;
    private static final String HQL_USER_PHONES;
    private static final String HQL_DEL_PHONES;

    static {
        HQL_ALL_USERS = "SELECT id, login, passwd, fio  FROM User";
        HQL_USERS_PASSWD = "SELECT id, login, passwd, fio  FROM User "
                + "WHERE login = :userLogin AND passwd = :userPasswd";
        HQL_USER_PHONES = "SELECT id, name, surname, middlename, phonemob, phonehome, address, email " +
                "FROM Phones WHERE usersId = :pUID AND name LIKE :pName AND surname LIKE :pSname " +
                "AND phonemob LIKE :pMob";
        HQL_DEL_PHONES = "DELETE Phones WHERE Id = :phId";
    }

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

//===============================================================================

    private <T>T getBeanById(Class<T> cl,int id) {
        T bean = null;
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            bean = cl.newInstance();
            bean = (T) session.get(cl, id);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bean;
    }


    private <T> void addBean(T obj) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(obj);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private <T> void delBean(T obj) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.delete(obj);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
//===============================================================================

    @Override
    public User getUserById(long id) {

        User user = null;
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            user = new User();
            user = (User) session.get(User.class, id);
//            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> results = null;
        Transaction tx = null;
        Session session = sessionFactory.openSession();
//        Session session = getSession();
        try {
        tx = session.beginTransaction();
        results = getUsersList(session.createQuery(HQL_ALL_USERS));

        System.out.println(results.get(0).getFio());

        tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }

    private List<User> getUsersList(Query query) {
        ArrayList<User> users = new ArrayList<User>();
        User user;
        Iterator iterator = query.list().iterator();
        while (iterator.hasNext()) {
            Object[] obj = (Object[]) iterator.next();
            user = new User();
            user.setId((Long) obj[0]);
            user.setLogin((String) obj[1]);
            user.setPasswd((String) obj[2]);
            user.setFio((String) obj[3]);
            users.add(user);
        }
        return users;
    }

    @Override
    public void addUser(User user) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(user);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private List<User> getUserPasswd(Query query,String userLogin,String userPasswd) {
        query.setParameter("userLogin", userLogin);
        query.setParameter("userPasswd", userPasswd);
        ArrayList<User> users = new ArrayList<User>();
        User user;
        Iterator iterator = query.list().iterator();
        while (iterator.hasNext()) {
            Object[] obj = (Object[]) iterator.next();
            user = new User();
            user.setId((Long) obj[0]);
            user.setLogin((String) obj[1]);
            user.setPasswd((String) obj[2]);
            user.setFio((String) obj[3]);
            users.add(user);
        }
        return users;
    }

    @Override
    public long userExist(String name, String passwd) {
        long result = 0;
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            List<User> userlist = getUserPasswd(session.createQuery(HQL_USERS_PASSWD),name,passwd);
            if(userlist != null && userlist.size() > 0) {
                result = userlist.get(0).getId();
            }
//            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public User getUserPhones(long id) {
        User result = null;

        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            result = (User)session.load(User.class, id);
//            System.out.println("User name = " + result.getFio());

            for(Phones ph : result.getPhonelist()) {
                ph.getName();
                ph.getSurname();
                ph.getMiddlename();
                ph.getPhonemob();
                ph.getPhonehome();
                ph.getAddress();
                ph.getEmail();
//                System.out.println("Phone mob = " + ph.getPhonehome());
            }
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void addContact(Phones contact) {
        addBean(contact);
    }

    private List<Phones> getPhones(Query query,long uid,String name,String surname,String mobile) {
        System.out.println("DAO: getPhones");
        query.setParameter("pUID", uid);
        query.setParameter("pName", name);
        query.setParameter("pSname", surname);
        query.setParameter("pMob", mobile);
        ArrayList<Phones> phonesList = new ArrayList<>();
        Phones phone;
        Iterator iterator = query.list().iterator();
        while (iterator.hasNext()) {
            Object[] obj = (Object[]) iterator.next();
            phone = new Phones();
            phone.setId((Long) obj[0]);
            phone.setName((String) obj[1]);
            phone.setSurname((String) obj[2]);
            phone.setMiddlename((String) obj[3]);
            phone.setPhonemob((String) obj[4]);
            phone.setPhonehome((String) obj[5]);
            phone.setAddress((String) obj[6]);
            phone.setEmail((String) obj[7]);
            phonesList.add(phone);
            System.out.println("Contact Name = " + phone.getName());
        }
        return phonesList;
    }

    @Override
    public List<Phones> getUserContacts(String uid, String name, String surname, String mobile) {
        List<Phones> result = null;
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            System.out.println("DAO: getUserContacts");
            tx = session.beginTransaction();
            result = getPhones(session.createQuery(HQL_USER_PHONES), Long.valueOf(uid), name, surname, mobile);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    private void delPhones(Query query,long phoneId) {
        System.out.println("DAO: delPhones id = " + phoneId);
        query.setParameter("phId", phoneId);
        query.executeUpdate();
    }
        @Override
    public void delContacts(List<Long> delList) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try {
            System.out.println("DAO: delContacts");
            tx = session.beginTransaction();
            for(long phoneId: delList) {
                delPhones(session.createQuery(HQL_DEL_PHONES), phoneId);
            }
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


}
