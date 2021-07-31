/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    @Override
    public List<User> getAll(String name, String surname, Integer nationalityId) {
        List<User> result = new ArrayList<>();
        try ( Connection c = connect()) {

            String sql = "select u.*, "
                    + "n.nationality, "
                    + "c.name as birthplace "
                    + "from user u "
                    + "left join country n on u.nationality_id = n.id "
                    + "left join country c on u.birthplace_id = c.id where 1=1";
            if (name != null && !name.trim().isEmpty()) {
                sql += " and u.name=? ";
            }

            if (surname != null && !surname.trim().isEmpty()) {
                sql += " and u.surname=? ";
            }

            if (nationalityId != null) {
                sql += " and u.nationality_id=? ";
            }

            PreparedStatement stmt = c.prepareStatement(sql);
            int i = 1;
            if (name != null && !name.trim().isEmpty()) {
                stmt.setString(i, name);
                i++;
            }

            if (surname != null && !surname.trim().isEmpty()) {
                stmt.setString(i, surname);
                i++;
            }

            if (nationalityId != null) {
                stmt.setInt(i, nationalityId);
            }
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
//                User u = getUser(rs);
//                result.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public User findyByEmailAndPassword(String email, String password) {
        User result = null;
        try ( Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("select * from user where email=? and password =?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
//                result = getUserSimple(rs);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public User findyByEmail(String email) {
        User result = null;
        try ( Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement("select * from user where email=?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
//                result = getUserSimple(rs);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {

        EntityManager em = em();
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public boolean removeUser(int id) {

        EntityManager em = em();
        User u = em.find(User.class, id);
        em.getTransaction().begin();
        em.remove(u);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    @Override
    public User getById(int userId) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("resumeappPU");
        EntityManager em = emf.createEntityManager();
        User u = em.find(User.class, userId);
        em.close();
        return u;
    }

    private static BCrypt.Hasher cyrpt = BCrypt.withDefaults();

    @Override
    public boolean addUser(User u) {
        
        u.setPassword(cyrpt.hashToString(4, u.getPassword().toCharArray()));
        
        EntityManager em = em();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();
        return true;

    }

    public static void main(String[] args) {
//        UserDaoImpl usd = new UserDaoImpl();
//        User u = new User(0, "test", "test", "asas@gmail.com", "skcnasknc", "27323", null, null, null, null, null);
//        u.setPassword("12345");
//        usd.addUser(u);
    }

}
