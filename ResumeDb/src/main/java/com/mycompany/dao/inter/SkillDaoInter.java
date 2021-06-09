/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.inter;

import com.mycompany.entity.Skill;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface SkillDaoInter {

    List<Skill> getAll();

    Skill getById(int id);

    boolean removeSkill(int id);

    boolean insertSkill(Skill s);

    boolean updateSkill(Skill s);

    Skill getByName(String name);

}
