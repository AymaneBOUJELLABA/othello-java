package com.othello.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager
{
	private static Connection cnx; 
	private String url = "jdbc:mysql://localhost:3306/othello_game?serverTimezone=UTC";
	private String user = "root";
	private String pwd = "";
	
	private ConnectionManager()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			cnx = DriverManager.getConnection(url,user,pwd);
			
		}catch(SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static Connection getCnx()
	{
		if(cnx == null)
			new ConnectionManager();
		
		return cnx;
	}
	
	public static void main( String []args)
	{
		System.out.println(ConnectionManager.getCnx());
	}

}
