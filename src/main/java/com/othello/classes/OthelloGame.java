package com.othello.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.othello.db.ConnectionManager;

public class OthelloGame
{
	private static Connection cnx= ConnectionManager.getCnx();
	
	private int id;
	private String name;
	private String owner;
	private Date date;
	private int type; //0 pour pvp //1 pour P v AI
	private String moves; //B11,W25,B36,W58
	
	public void savegame()
	{
		try
		{	
			OthelloGame oldgame = OthelloGame.loadgame(this.id);
			String query;
			if(oldgame!=null)
			{
				System.out.println("new game saved");
				query = "INSERT INTO game(name,username,date,type,moves) VALUES(?,?,?,?,?);";
			}else
			{
				System.out.println("old game found");
				query = "UPDATE game set name=?,username=?,date=?,type=?,moves=? WHERE id=?";
			}
				
			long millis=System.currentTimeMillis();  
			date=new java.sql.Date(millis);
			
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setString(1,name);
			stmt.setString(2,owner);
			stmt.setDate(3,date);
			stmt.setInt(4,type);
			stmt.setString(5,moves);
			
			stmt.execute();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static OthelloGame loadgame(int id)
	{
		try
		{	
			String query = "SELECT id,name,username,date,type,moves FROM game WHERE id=?";
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setInt(1, id);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
				return new OthelloGame(res.getInt(1),res.getString(2),res.getString(3),res.getDate(4),res.getInt(5),res.getString(6));
				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static OthelloGame loadgame(String gamename)
	{
		try
		{
			String query = "SELECT id,name,username,date,type,moves FROM game WHERE name=?";
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setString(1, gamename);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
				return new OthelloGame(res.getInt(1),res.getString(2),res.getString(3),res.getDate(4),res.getInt(5),res.getString(6));
				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	public ArrayList<String> getGames(String username)
	{
		ArrayList<String> games = new ArrayList<String>();
		try
		{	
			String query = "SELECT name FROM game WHERE username=?";
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setString(1, username);
			
			ResultSet res = stmt.executeQuery();
			
			while(res.next())
				games.add(res.getString(1));
			
			return games;
				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	public OthelloGame()
	{
		super();
	}
	
	public OthelloGame(int id, String name, String owner, Date date, int type, String moves)
	{
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.date = date;
		this.type = type;
		this.moves = moves;
	}

	public static void main(String[] args)
	{
		OthelloGame game1 = OthelloGame.loadgame(3);
		
		ArrayList<String> moves = game1.getArrayMoves();
		System.out.println("Game : "+game1.getName());
		for(String m : moves)
			System.out.println(m);
		
		OthelloGame game2 = OthelloGame.loadgame("game1");
		moves = game2.getArrayMoves();
		System.out.println("Game : "+game2.getName());
		for(String m : moves)
			System.out.println(m);
		
		game2.setName("editedGame");
		game2.savegame();
	}
	// B11,W25 => B11 | W25
	public ArrayList<String> getArrayMoves()
	{
		return new ArrayList<>(Arrays.asList(moves.split(",")));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMoves() {
		return moves;
	}

	public void setMoves(String moves) {
		this.moves = moves;
	}
	
	
	

	
}
