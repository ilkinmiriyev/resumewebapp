/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserSkillDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {

        int userSkillId = rs.getInt("userSkill_id");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");
        return new UserSkill(userSkillId, new User(userId), new Skill(skillId, skillName), power);

    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {

        List<UserSkill> result = new ArrayList<>();
        try ( Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("SELECT " +
"                   	u.*, " +
"                  	n.nationality, " +
"                 	c.NAME AS birthplace, " +
"                  	us.id AS userSkill_id, " +
"                  	us.skill_id, " +
"                  	us.power, " +
"			s.name AS skill_name" +
"                       FROM " +
"                    	USER u " +
"                 	LEFT JOIN country n ON u.nationality_id = n.id " +
"                   	LEFT JOIN country c ON u.birthplace_id = c.id " +
"                  	LEFT JOIN user_skill us ON	u.id=us.user_id " +
"										LEFT JOIN skill s on us.skill_id=s.id " +
"                  WHERE " +
"                  	u.id =?");

            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
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
    public boolean removeUserSkill(int id) {
        boolean b = false;
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("DELETE FROM user_skill WHERE id=?");
            stmt.setInt(1, id);
            b = stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean insertUserSkill(UserSkill userSkill) {

        boolean b = false;
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("INSERT INTO user_skill VALUES(NULL,?, ?, ?)");
            stmt.setInt(1, userSkill.getUser().getId());
            stmt.setInt(2, userSkill.getSkill().getId());
            stmt.setInt(3, userSkill.getPower());
            b = stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean updateUserSkill(UserSkill userSkill) {
        
        boolean b = false;
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("UPDATE user_skill SET power=? WHERE id=?");
            stmt.setInt(1, userSkill.getPower());
            stmt.setInt(2, userSkill.getId());
            
            b = stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    
    
    

}
