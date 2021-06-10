/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.resume.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;
import com.mycompany.main.Context;
import util.ControllerUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Admin
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private UserDaoInter userDao=Context.instanceUserDao();
    private BCrypt.Hasher bcyrpt =BCrypt.withDefaults();
    private BCrypt.Verifyer verifyer = BCrypt.verifyer();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request,response );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
//            password=bcyrpt.hashToString(4, password.toCharArray());


            User user = userDao.findyByEmail(email);
            if (user == null) {
                throw new IllegalArgumentException("email or password incorrect!!!");
            }

            BCrypt.Result verify = verifyer.verify(password.toCharArray(), user.getPassword());

            if(!verify.verified){
                throw new IllegalArgumentException("password incorrect!!!");
            }


            request.getSession().setAttribute("loggedInUser", user);
            response.sendRedirect("users");

        }catch (Exception e){
            e.printStackTrace();
            ControllerUtil.errorPage(response, e);
        }





    }
}
