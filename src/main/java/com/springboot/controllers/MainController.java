package com.springboot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.dao.CustomerDAO;
import com.springboot.domain.Phones;
import com.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private CustomerDAO hibernateUserDao;

    @RequestMapping("/")
    public String index()  {
        return "main";
    }


    @RequestMapping("/hello")
    public String hello(ModelAndView modelAndView)  {
        
//		System.out.println("STEP_USERS_ModelAndView-1");
        List<User> users = hibernateUserDao.getAllUsers();
//		System.out.println(Arrays.toString(users.toArray()));

         return "hello";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView userenter(@RequestParam(value="login", required=false)String log,
                            @RequestParam(value="password", required=false)String passwd)  {

        ModelAndView modelAndView;

        long iduser= hibernateUserDao.userExist(log, passwd);
        if( iduser > 0){
            User u = hibernateUserDao.getUserById(iduser);
            if(u != null) {
                modelAndView = new ModelAndView("phonelist");
                modelAndView.addObject("loguser", u);
            } else {
                modelAndView = new ModelAndView("req_error");
            }
        } else {
            modelAndView = new ModelAndView("req_error");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/getphones", method = RequestMethod.POST)
    @ResponseBody
    public Set<String> getPhones(@RequestParam(value="uid", required=false)String iduser) {

        System.out.println("getphones Conroller");
        System.out.println("User ID = " + iduser);
        hibernateUserDao.getUserPhones(Long.valueOf(iduser));
        Set<String> records = new HashSet<String>();
        records.add("Record #1");
        records.add("Record #2");
        return records;
    }

    @RequestMapping(value = "/getphonelist", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> getPhoneList(@RequestParam(value="uid", required=false)String iduser)
            throws JsonProcessingException {

        Set<String> records = new HashSet<String>();
        System.out.println("getphonelist Conroller");
        User user = hibernateUserDao.getUserPhones(Long.valueOf(iduser));
        System.out.println("User = " + user.getFio());

        ObjectMapper mapper = new ObjectMapper();
        for(Phones ph : user.getPhonelist()) {
            System.out.println("PHONE = " + ph.getId() + "|" + ph.getName() + "|" + ph.getSurname() + "|" +
                ph.getMiddlename() + "|" + ph.getPhonemob() + "|" +
                ph.getPhonehome() + "|" + ph.getAddress() + "|" + ph.getEmail());
            records.add(mapper.writeValueAsString(ph));
        }
        return records;
    }

    @RequestMapping(value = "/getfiltrphonelist", method = RequestMethod.POST)
    @ResponseBody
    public Set<String> getFiltrPhoneList(@RequestParam(value="uid", required=false)String iduser,
                                          @RequestParam(value="fname", required=false)String name,
                                    @RequestParam(value="fsurname", required=false)String surname,
                                       @RequestParam(value="fmobile", required=false)String mobile)
            throws JsonProcessingException {
        Set<String> records = new HashSet<String>();
        System.out.println("Filtrphonelist Conroller");

        if(name == null || name.equals("")) {
            name = "%";
        } else {
            name += "%";
        }
        if(surname == null || surname.equals("")) {
            surname = "%";
        } else {
            surname += "%";
        }
        if(mobile == null || mobile.equals("")) {
            mobile = "%";
        } else {
            mobile += "%";
        }

        System.out.println("IDuser = " + iduser);
        System.out.println("Name = " + name);
        System.out.println("Surname = " + surname);
        System.out.println("Mobile = " + mobile);

        List<Phones> phonesList = hibernateUserDao.getUserContacts(iduser,name,surname,mobile);

        ObjectMapper mapper = new ObjectMapper();
        for(Phones ph : phonesList) {
            System.out.println("PHONE = " + ph.getId() + "|" + ph.getName() + "|" + ph.getSurname() + "|" +
                    ph.getMiddlename() + "|" + ph.getPhonemob() + "|" +
                    ph.getPhonehome() + "|" + ph.getAddress() + "|" + ph.getEmail());
            records.add(mapper.writeValueAsString(ph));
        }
        return records;
    }

    @RequestMapping("/signup")
//    @ResponseBody
    public String signUp() {
        System.out.println("Sign Up");
        return "signup";
    }

    @RequestMapping(value = "/phonelist", method = RequestMethod.GET)
    public ModelAndView userPhones(Model model) {
        ModelAndView modelAndView;
        long iduser = (long)model.asMap().get("iduser");
        System.out.println("phonelist");
        System.out.println("iduser = "+iduser);
        User u = hibernateUserDao.getUserById(iduser);
        if(u != null) {
            modelAndView = new ModelAndView("phonelist");
            modelAndView.addObject("loguser", u);
        } else {
            modelAndView = new ModelAndView("req_error");
        }
        return modelAndView;
    }

    @RequestMapping(value="/newlogin", method=RequestMethod.POST)
    public String userEnter(@RequestParam(value="login", required=false)String log,
                                  @RequestParam(value="password", required=false)String passwd,
                            Model model,RedirectAttributes redirectAttributes)  {

        ModelAndView modelAndView;

        long iduser= hibernateUserDao.userExist(log, passwd);
        if( iduser > 0){
            redirectAttributes.addFlashAttribute("iduser", iduser);
        } else {
            return "redirect:/signup";
        }
        return "redirect:/phonelist";
    }

    @RequestMapping(value="/newphone", method=RequestMethod.POST)
    public void addPhone(@RequestParam(value="data", required=false)String str) {
        Phones phone = new Phones();
        String[] newContact = str.split(";");
        phone.setName(newContact[0]);
        phone.setSurname(newContact[1]);
        phone.setMiddlename(newContact[2]);
        phone.setPhonemob(newContact[3]);
        phone.setPhonehome(newContact[4]);
        phone.setAddress(newContact[5]);
        phone.setEmail(newContact[6]);
        phone.setUsersId(Long.valueOf(newContact[7]));

        System.out.println("Name = " + phone.getName());
        System.out.println("Surname = " + phone.getSurname());
        System.out.println("Middlename = " + phone.getMiddlename());
        System.out.println("Mobail = " + phone.getPhonemob());
        System.out.println("Phone = " + phone.getPhonehome());
        System.out.println("Address = " + phone.getAddress());
        System.out.println("Email = " + phone.getEmail());
        System.out.println("Owner = " + phone.getUsersId());

        hibernateUserDao.addContact(phone);

    }

    @RequestMapping(value="/newowner", method=RequestMethod.POST)
    public ModelAndView newUser(@RequestParam(value="reglog", required=false)String log,
                                  @RequestParam(value="regpasswd", required=false)String passwd,
                                @RequestParam(value="fio", required=false)String fullname)  {

        ModelAndView modelAndView;
        User user = new User();
        user.setLogin(log);
        user.setPasswd(passwd);
        user.setFio(fullname);

        System.out.println("Login = " + log);
        System.out.println("Passwd = " + passwd);
        System.out.println("FIO = " + fullname);

        hibernateUserDao.addUser(user);
        long iduser= hibernateUserDao.userExist(log, passwd);
        System.out.println("ID = " + iduser);
        if( iduser > 0){
            user = hibernateUserDao.getUserById(iduser);
            modelAndView = new ModelAndView("phonelist");
            modelAndView.addObject("loguser", user);
        } else {
            modelAndView = new ModelAndView("req_error");
        }
        return modelAndView;
    }

    @RequestMapping(value="/delcontacts", method=RequestMethod.POST)
    public void delPhones(@RequestParam(value="data", required=false)String str) {

        List<String> list = new ArrayList<>(Arrays.asList(str.split(";")));
        List<Long> listToDel = new ArrayList<>();
        for(String s: list){
            listToDel.add(Long.valueOf(s));
            System.out.println("ContactID = " + s);
        }

        hibernateUserDao.delContacts(listToDel);

    }
}
