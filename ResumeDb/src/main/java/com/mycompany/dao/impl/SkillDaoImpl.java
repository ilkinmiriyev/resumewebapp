/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.entity.Skill;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {

    
    @Override
    public List<Skill> getAll() {
        EntityManager em = em();
        List<Skill> skills = new ArrayList<>();
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM skill");
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                skills.add(new Skill(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return skills;
    }

    @Override
    public Skill getById(int id) {

        Skill skill = null;
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("SELECT * FROM skill WHERE id=?");
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                skill = new Skill(id, rs.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return skill;
    }

    @Override
    public boolean removeSkill(int id) {
        boolean b = false;
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("DELETE FROM skill where id=?");
            stmt.setInt(1, id);
            b = stmt.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean insertSkill(Skill s) {
        boolean b = false;
        try ( Connection connect = connect()) {

            

            PreparedStatement stmt = connect.prepareStatement("INSERT skill (`name`) VALUES(?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, s.getName());
            b = stmt.execute();
            ResultSet generateKeys = stmt.getGeneratedKeys();
            if (generateKeys.next()) {
                s.setId(generateKeys.getInt(1));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean updateSkill(Skill s) {
        boolean b = false;
        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("UPDATE skill SET name =? WHERE id=?");
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getId());
            b = stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    @Override
    public Skill getByName(String skill) {
        Skill s=null;

        try ( Connection connect = connect()) {
            PreparedStatement stmt = connect.prepareStatement("SELECT * from skill where name = ?");
            stmt.setString(1, skill);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                s=new Skill(id, name);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

}
