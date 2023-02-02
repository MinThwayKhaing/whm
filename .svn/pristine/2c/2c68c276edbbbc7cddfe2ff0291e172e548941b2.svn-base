package com.dat.whm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeUser {
	public static void main(String[] args) {
		Connection connection = null;
		try {
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyexam?useUnicode=yes&amp;characterEncoding=UTF-8", "easyexam", "easyexam");
	        connection.setAutoCommit(false);
	        Statement statement = connection.createStatement();
	        ResultSet userRS = statement.executeQuery("SELECT FULLNAME, ID FROM users where user_role != 'ADMIN'");
	        while (userRS.next()) {
	        	String name = userRS.getString("FULLNAME").replace(".", ",");
	        	name = name.replace(", ", ",");
	        	String id = userRS.getString("ID");
	    		int index = name.indexOf(",");
	    		String temp_1 = name.substring(0, index);
	    		String temp_2 = name.substring(index + 1, name.length());
	    		name = temp_2 + " " + temp_1;
	    		System.out.println(name);
	    		Statement updateSt = connection.createStatement();
	    		updateSt.executeUpdate("UPDATE USERS SET FULLNAME = '" + name + "' WHERE ID = '" + id + "'");
	        }
	        connection.commit();
		} catch (Exception e) {
        	try {
        		connection.rollback();
				connection.close();
			} catch (SQLException e1) {
			}
            e.printStackTrace();
        } finally {
        	try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
}
