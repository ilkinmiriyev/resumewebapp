/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.resume.controller;

import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.Country;
import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import com.mycompany.main.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@WebServlet(name = "UserDetailController", urlPatterns = {"/userdetail"})
public class UserDetailController extends HttpServlet {


    private UserDaoInter userDao = Context.instanceUserDao();
    private CountryDaoInter countryDao = Context.instanceCountryDao();
    private UserSkillDaoInter userSkillDao = Context.instanceUserSkillDao();
    private SkillDaoInter skillDao = Context.instanceSkillDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.valueOf(request.getParameter("id"));
        String action = request.getParameter("action");
        if (action.equals("update")) {


            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String birthdate = request.getParameter("birthdate");

            String birthplaceId = request.getParameter("birthplace");
            Country birthplace = countryDao.getById(Integer.valueOf(birthplaceId));

            String nationalityId = request.getParameter("nationality");
            Country nationality = countryDao.getById(Integer.valueOf(nationalityId));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date brthd = null;
            try {
                brthd = sdf.parse(birthdate);
            } catch (ParseException ex) {
                ex.getMessage();
            }
            long time = brthd.getTime();
            java.sql.Date sqlDate = new java.sql.Date(time);

            String profile = request.getParameter("profile");

            String[] userSkillPowers = request.getParameterValues("userSkillPower");
            String[] userSkillId = request.getParameterValues("userSkillId");
            String[] userSkillName = request.getParameterValues("userSkillName");

            User u = userDao.getById(id);

            for (int i = 0; i < userSkillId.length; i++) {
                UserSkill userSkill = new UserSkill();

                userSkill.setId(Integer.parseInt(userSkillId[i]));
                userSkill.setUser(null);
                userSkill.setSkill(null);
                userSkill.setPower(Integer.parseInt(userSkillPowers[i]));
                skillDao.updateSkill(new Skill((u.getSkills()).get(i).getSkill().getId(), userSkillName[i]));
                userSkillDao.updateUserSkill(userSkill);
            }

            u.setName(name);
            u.setSurname(surname);
            u.setAddress(address);
            u.setEmail(email);
            u.setPhone(phone);
            u.setBirthdate(sqlDate);
            u.setBirthPlace(birthplace);
            u.setProfileDesc(profile);
            u.setNationality(nationality);
            userDao.updateUser(u);
        } else if (action.equals("delete")) {
            userDao.removeUser(id);
        }


        else if (action.equals("Add")){
            UserSkill us=new UserSkill();
            String txtSkillName=request.getParameter("newSkillName");
            us.setPower(Integer.parseInt(request.getParameter("newSkillPower")));
            Skill skill=new Skill();
            if (!txtSkillName.trim().isEmpty()){
                skill.setName(txtSkillName);
                skillDao.insertSkill(skill);
                us.setSkill(skillDao.getByName(txtSkillName));

            }else{
                int newSkillId=Integer.valueOf(request.getParameter("skillList"));
                us.setSkill(skillDao.getById(newSkillId));
            }

            us.setUser(userDao.getById(id));
            userSkillDao.insertUserSkill(us);

            response.sendRedirect("userdetail?id="+id);
            return;
        }
        else if (action.equals("skillDelete")) {
            int skillId = Integer.parseInt(request.getParameter("skillId"));
            userSkillDao.removeUserSkill(skillId);
            response.sendRedirect("userdetail?id="+id);
            return;
        }
        response.sendRedirect("users");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User u = null;
            String userIdStr = request.getParameter("id");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Id is not defined");
            }

            Integer userId = Integer.parseInt(userIdStr);

            u = userDao.getById(userId);
            if (u == null) {
                throw new IllegalArgumentException("There is no user with this id");
            }
            List<UserSkill> skills = u.getSkills();
            List<Country> country = countryDao.getAll();
            List<Skill> allSkill = skillDao.getAll();

            request.setAttribute("skills", skills);
            request.setAttribute("allSkill", allSkill);
            request.setAttribute("country", country);
            request.setAttribute("user", u);


            request.getRequestDispatcher("userdetail.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error?msg="+ex.getMessage());
        }
    }

}
