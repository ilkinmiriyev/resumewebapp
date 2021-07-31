/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.inter;

import com.mycompany.entity.Country;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface CountryDaoInter {
    
    public List<Country> getAll();
    
    public Country getById(int id);
    
    boolean updateCountry(Country u);
    
    boolean removeCountry(int id);

}
