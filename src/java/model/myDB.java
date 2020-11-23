/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadrach.udoka
 */
public class myDB {
  
//    throws SQLException,ClassNotFoundException,InstantiationException,
//            IllegalAccessException 
    
       public myDB(){
           
           try {
               getConnection();
           } catch (SQLException ex) {
               Logger.getLogger(myDB.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(myDB.class.getName()).log(Level.SEVERE, null, ex);
           } catch (InstantiationException ex) {
               Logger.getLogger(myDB.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IllegalAccessException ex) {
               Logger.getLogger(myDB.class.getName()).log(Level.SEVERE, null, ex);
           }

    }

    private static Connection getConnection()throws SQLException,ClassNotFoundException,InstantiationException,
            IllegalAccessException{
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emg","root", "");

        } catch (Exception ex) {
            ex.printStackTrace();
            conn = null;
        }
        return conn;
    }
    
    public static void closeConnection(PreparedStatement ps, Connection conn) {
         try {
            if (ps != null  ) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Boolean verifyTerminalId(String terminalId)throws SQLException,ClassNotFoundException,InstantiationException,
            IllegalAccessException{
          boolean flag = false;
          Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
           try {
            String sql = "SELECT TerminalID FROM pos_service WHERE TerminalID = ?";
            conn = myDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, terminalId);
            rs = ps.executeQuery();
            if (rs.next()) {
                flag = true;
            }
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            closeConnection(ps, conn);

        } 
           return flag;
}
    
            static String getEmail(String terminalID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String email = null;
        try {
            String sql = "SELECT Email_Address FROM pos_service WHERE TerminalID = ? ";
            conn = myDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, terminalID);
            rs = ps.executeQuery();
            while (rs.next()) {
                email = rs.getString("Email_Address").trim();
            }
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            closeConnection(ps, conn);

        }
        return email;
    }
            
        public static Boolean verifyRefId(String refId)throws SQLException,ClassNotFoundException,InstantiationException,
            IllegalAccessException{
          boolean flag = false;
          Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
           try {
            String sql = "SELECT RefferenceId FROM transaction_logs WHERE RefferenceId = ?";
            conn = myDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, refId);
            rs = ps.executeQuery();
            if (rs.next()) {
                flag = true;
            }
            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            closeConnection(ps, conn);

        } 
           return flag;
}    
        
        public static void main(String[]args){
            
        }
           
}
