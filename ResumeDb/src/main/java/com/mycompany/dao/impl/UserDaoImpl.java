/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.entity.Country;
import com.mycompany.entity.User;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.UserSkill;
import com.mycompany.main.Context;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    private User getUser(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String address = rs.getString("address");
        String profileDesc=rs.getString("profile_description");
        int nationalityId = rs.getInt("nationality_id");
        int birthplaceId = rs.getInt("birthplace_id");

        String nationalityStr = rs.getString("nationality");
        String birthplaceStr = rs.getString("birthplace");
        Date birthdate = rs.getDate("birthdate");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);

        UserSkillDaoInter userSkillDao = Context.instanceUserSkillDao();
        List<UserSkill> userSkill = userSkillDao.getAllSkillByUserId(id);
        
        return new User(id, name, surname, email, profileDesc, phone, address, birthdate, nationality, birthplace, userSkill);

    }

    @Override
    public List<User> getAll(String name, String surname, Integer nationalityId) {
        List<User> result = new ArrayList<>();
        try (Connection c = connect()) {


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
                User u = getUser(rs);
                result.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {

        try ( Connection c = connect()) {

//            Statement stmt = c.createStatement();
            // PreparedStatement interface-i sql injeksinin qarsisini alir
            PreparedStatement stmt = c.prepareStatement("UPDATE USER " +
                    " SET name =? ," +
                    " surname = ? , " +
                    " phone =? , " +
                    " email =? , " +
                    " address =? , " +
                    " profile_description =? , " +
                    " birthdate =? , " +
                    " birthplace_id =? , " +
                    " nationality_id=? " +
                    " WHERE " +
                    " id =?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getAddress());
            stmt.setString(6, u.getProfileDesc());
            stmt.setDate(7, u.getBirthdate());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getNationality().getId());
            stmt.setInt(10, u.getId());

            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {

        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            return stmt.execute("delete from user where id=" + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User getById(int userId) {
        User result = null;
        try ( Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("SELECT u.*, "
                    + "n.nationality, "
                    + "c.name AS birthplace "
                    + "FROM user u LEFT JOIN country n ON u.nationality_id=n.id "
                    + "LEFT JOIN country c ON u.birthplace_id=c.id where u.id=?" );
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {

                result = getUser(rs);

            }

//            rs.close();        // ResluSet closed
//            stmt.close();      // Statement closed
//            c.close();         // Connection closed   Connection close olarsa, diger 2-side close olur.( Kill )
            // Ve Connection inter-i AutoCloseable oldugu ucun try with resource ede bilirik.
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addUser(User u) {

        try ( Connection c = connect()) {

//            Statement stmt = c.createStatement();
            // PreparedStatement interface-i sql injeksinin qarsisini alir
            PreparedStatement stmt = c.prepareStatement("insert into user(name, surname, phone, email, profile_description) values (?, ?, ?, ?, ?);");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getPhone());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getProfileDesc());

            return stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

}
