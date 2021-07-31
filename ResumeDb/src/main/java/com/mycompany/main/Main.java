/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;

import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;

/**
 * @author AdminData Access Object
 */
public class Main {

    public static void main(String[] args) throws Exception {

        UserDaoInter userDao = Context.instanceUserDao();
        User u = userDao.getById(59);
        System.out.println("Email: "+u.getEmail());
        System.out.println("country: "+u.getBirthPlace().getName());



    }
}
