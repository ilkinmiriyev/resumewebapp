/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.entity.EmploymentHistory;
import com.mycompany.entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class EmploymentHistoryDaoImpl extends AbstractDAO implements EmploymentHistoryDaoInter {

    
    
    
    private EmploymentHistory getEmploymentHistory(ResultSet rs) throws Exception {

        String header=rs.getString("header");
        String jobDescription=rs.getString("job_description");
        Date beginDate=rs.getDate("begin_date");
        Date endDate=rs.getDate("end_date");
        int userId=rs.getInt("user_id");
        EmploymentHistory emp=new EmploymentHistory(null, header, beginDate, endDate, jobDescription, new User(userId));
        return emp;
    }

    
    
    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {

        List<EmploymentHistory> result = new ArrayList<>();
        try ( Connection c = connect()) {

            PreparedStatement stmt = c.prepareStatement("select *from employment_history where user_id=?");

            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                EmploymentHistory emp = getEmploymentHistory(rs);
                result.add(emp);
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
}
