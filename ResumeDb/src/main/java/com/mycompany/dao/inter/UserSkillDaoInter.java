/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.inter;

import com.mycompany.entity.UserSkill;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UserSkillDaoInter {

    public List<UserSkill> getAllSkillByUserId(int userId);

    boolean removeUserSkill(int id);

    boolean insertUserSkill(UserSkill userSkill);

    boolean updateUserSkill(UserSkill userSkill);

}
