/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;

import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.Country;
import com.mycompany.entity.User;

import java.util.List;

/**
 * @author AdminData Access Object
 */
public class Main {

    public static void main(String[] args) throws Exception {
//
//        EmploymentHistoryDaoInter dao = Context.instanceEmploymentHistoryDao();
//
        UserDaoInter userDao =Context.instanceUserDao();
//        User u=new User(1, "Murad", "Teymurov", "email@gmail.com", "49747", "askcnsjanc", "sdci", null, null, null, null);
//        u.setPassword("12345");
//
//        userDao.addUser(u);

        System.out.println(userDao.getById(59).getName());

    }
}
