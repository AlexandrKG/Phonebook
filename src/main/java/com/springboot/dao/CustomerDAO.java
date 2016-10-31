package com.springboot.dao;


import com.springboot.domain.Phones;
import com.springboot.domain.User;

import java.util.List;

public interface CustomerDAO {

    public User getUserById(long id);
    public List<User> getAllUsers();
    public void addUser(User user);
    public long userExist(String name, String passwd);
    public User getUserPhones(long id);
    public void addContact(Phones contact);
    public List<Phones> getUserContacts(String uid,String name,String surname,String mobile);
    public void delContacts(List<Long> delList);
}
