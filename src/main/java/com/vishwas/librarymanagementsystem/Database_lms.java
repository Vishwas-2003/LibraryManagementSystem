/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.vishwas.librarymanagementsystem;

/**
 *
 * @author Vishwas
 */
import java.sql.*;
public class Database_lms {
    Connection con;
    Statement stmt;

    Database_lms() throws Exception{
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","vishwas");
        stmt=con.createStatement();
    }

    public int register(String usernameS,String nameS,String mobileS,String emailS,String roleS,String empS,String passwordS)throws Exception{

        String sql="insert into user values(?,?,?,?,?,?,?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1, usernameS);
        ps.setString(2, nameS);
        ps.setString(3, mobileS);
        ps.setString(4, emailS);
        ps.setString(5, roleS);
        ps.setString(6, empS);
        ps.setString(7, passwordS);
        ps.setInt(8, 0);

        // ResultSet rs2=stmt.executeQuery("select mobile from user where mobile='"+mobileS+"'");
        // if(!rs2.next()){
        //     return 4;
        // }
        // rs2=stmt.executeQuery("select email from user where email='"+emailS+"'");
        // if(!rs2.next()){
        //     return 3;
        // }
        if(roleS.equals("Issuer")){
            ResultSet rs=stmt.executeQuery("select user_name from user");
            String u_name="";
            while(rs.next()){
                u_name=rs.getString(1);
                if(u_name.equals(usernameS)){
                    return 0;
                }
            }
            ps.executeUpdate();
            return 1;
        }
        else if(roleS.equals("Librarian")){
            ResultSet rs=stmt.executeQuery("select emp_id from employee where emp_id='"+empS+"'");
            String emp_id="";
            while(rs.next()){
                emp_id=rs.getString(1);
                if(emp_id.equals(empS)){
                    ResultSet rs1=stmt.executeQuery("select user_name from user");
                    String u_name1="";
                    while(rs1.next()){
                        u_name1=rs1.getString(1);
                        if(u_name1.equals(usernameS)){
                            return 0;
                        }
                    }
                    ps.executeUpdate();
                    return 1;
                }
                else{
                    return 0;
                }
            }
            return 2;
        }
        else{
            return 2;
        }
    }

    public int login(String username,String password)throws Exception{
        String loginsql="select user_name,password,role from user where user_name='"+username+"' and password='"+password+"'";
        ResultSet loginSet=stmt.executeQuery(loginsql);
        int login=0;

        while(loginSet.next()){
            if(username.equals(loginSet.getString(1)) && password.equals(loginSet.getString(2))){
                System.out.println(loginSet.getString(3));
                if(loginSet.getString(3).equalsIgnoreCase("Issuer"))
                    login=1;
                else    
                    login=2;
            }
        }
        return login;
    }
}

