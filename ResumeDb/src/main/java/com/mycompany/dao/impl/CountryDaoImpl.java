/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.entity.Country;
import com.mysql.cj.xdevapi.Statement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter {

    public Country getCountry(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String nationality = rs.getString("nationality");
        return new Country(id, name, nationality);
    }

    @Override
    public List<Country> getAll() {
        List<Country> countries = new ArrayList<>();
        try ( Connection connection = connect();) {
            Statement statement = connection.createStatement();
            statement.execute("select * from country");
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                countries.add(getCountry(rs));
            }
        } catch (Exception ex) {

        }
        return countries;
    }

    @Override
    public Country getById(int id) {
        Country country = null;
        try ( Connection connection = connect();) {
            PreparedStatement statement = connection.prepareStatement("select * from country where id=?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                country = getCountry(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return country;

    }

    @Override
    public boolean updateCountry(Country c) {
        try ( Connection connection = connect();) {
            PreparedStatement statement = connection.prepareStatement("update country set name=?, nationality=?, where id=?");
            statement.setString(1, c.getName());
            statement.setString(2, c.getNationality());
            statement.setInt(3, c.getId());
            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCountry(int id) {
        try ( Connection connection = connect();) {
            PreparedStatement statement = connection.prepareStatement("delete from country where id=?");
            statement.setInt(1, id);
            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
