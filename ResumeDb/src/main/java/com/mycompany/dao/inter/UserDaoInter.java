/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.inter;

import com.mycompany.entity.User;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface UserDaoInter {

    public List<User> getAll(String name, String surname, Integer nationaltyId);

    public User getById(int id);

    public boolean updateUser(User u);

    public boolean addUser(User u);

    public boolean removeUser(int id);
    
    

}
