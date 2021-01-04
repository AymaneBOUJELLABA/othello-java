package com.othello.classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.othello.db.ConnectionManager;
import com.othello.entities.Case;
import com.othello.entities.CaseValue;

public class OthelloGame
{
	private static Connection cnx= ConnectionManager.getCnx();
	
	private int id; // --
	private String name; // --
	private String owner; // --
	private Date date; // --
	private int type; //0 pour pvp //1 pour P v AI
	
	/* IMPORTANT FIELDS ( GAME DATA FIELDS ) */ 
	private String moves; //B11,W25,B36,W58 (NEED FROM BOARD)
	private boolean isWon; // need from board ( )
	private String state; // 0,0,B|0,1,E... (NEED FROM BOARD)
	
	public void savegame()
	{
		try
		{	
			PreparedStatement stmt; 
			OthelloGame oldgame = OthelloGame.loadgame(this.name);
			String query;
			if(oldgame==null)
			{
				System.out.println("new game saved");
				query = "INSERT INTO game(name,username,date,type,moves,state,isWon) VALUES(?,?,?,?,?,?,?);";
			}else
			{
				System.out.println("old game found");
				query = "UPDATE game set name=?,username=?,date=?,type=?,moves=?,state=?,isWon=? WHERE id=?";
				stmt = cnx.prepareStatement(query);
				stmt.setInt(8, id);
			}
			stmt = cnx.prepareStatement(query);
			long millis=System.currentTimeMillis();  
			date=new java.sql.Date(millis);
			
			
			stmt.setString(1,name);
			stmt.setString(2,owner);
			stmt.setDate(3,date);
			stmt.setInt(4,type);
			stmt.setString(5,moves);
			stmt.setString(6, state);
			stmt.setBoolean(7, isWon);

			
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
			String query = "SELECT id,name,username,date,type,moves,isWon,State FROM game WHERE id=?";
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setInt(1, id);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
				return new OthelloGame(res.getInt(1),res.getString(2),res.getString(3),res.getDate(4),res.getInt(5),res.getString(6),res.getBoolean(7),res.getString(8));
				
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
			String query = "SELECT id,name,username,date,type,moves,isWon,State FROM game WHERE name=?";
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setString(1, gamename);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
				return new OthelloGame(res.getInt(1),res.getString(2),res.getString(3),res.getDate(4),res.getInt(5),res.getString(6),res.getBoolean(7),res.getString(8));
				
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addMove(CaseValue turn,int i, int j)
	{
		String move = ((turn == CaseValue.BLACK) ? "B" : "W");
		
		if(moves.isEmpty())
			moves += move+i+""+j+"";
		else
			moves += ","+move+i+""+j+"";
		
		System.out.println("new move added :" + moves);
	}
	public static ArrayList<String> getGames(String username)
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
		moves = "";
		state = "";
		isWon = false;
		
		long millis=System.currentTimeMillis();  
		date=new java.sql.Date(millis);
	}
	
	public OthelloGame(int id, String name, String owner, Date date, int type, String moves, boolean won,
			String state) 
	{
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.date = date;
		this.type = type;
		this.moves = moves;
		this.isWon = won;
		this.state = state;
	}

	public static void main(String[] args)
	{
		OthelloGame game2 = new OthelloGame();
		game2.setName("testGame");
		game2.setOwner("aymane");
		game2.setMoves("B11,W22,B33,W44,B55");
		game2.setType(0);
		game2.setWon(false);
		game2.setState("0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E|0,0,B|0,1,W|0,2,E");
	
		
		game2.savegame();
		Case[][] board = game2.getBoardState();
		
		for(int i =0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.println(board[i][j]);
			}
		}
	}
	
	public String setStatefromBoard(Case[][] board)
	{
		String state = "";
		
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
			{
				char V;
				switch(board[i][j].getValue())
				{
					case BLACK : V = 'B'; break;
					case WHITE : V = 'W'; break;
					case EMPTY : V = 'E'; break;
					
					default : V='E'; break;
				}
				state +=(i == 8 && j==8)?  (i+","+j+","+V) : (i+","+j+","+V+"|");
			}
		
		System.out.println(state);
		return this.state = state;
	}
	// B11,W25 => B11 | W25
	public ArrayList<String> getArrayMoves()
	{
		return new ArrayList<>(Arrays.asList(moves.split(",")));
	}
	//get a [][]Case array
	public Case[][] getBoardState()
	{
		Case[][] board = new Case[8][8];
		
		String[] casestates = state.split("\\|");
		
		for(String s : casestates)
		for(int i=0;i<casestates.length;i++)
		{
			String[] data = casestates[i].split(",");
			int j=Integer.parseInt(data[0]);
			int k=Integer.parseInt(data[1]);
			
			switch(data[2])
			{
				case "B" : board[j][k] = new Case(j,k,CaseValue.BLACK); break;
				case "W" : board[j][k] = new Case(j,k,CaseValue.WHITE); break;
				case "E" : board[j][k] = new Case(j,k,CaseValue.EMPTY); break;
				
				default : System.err.println("Unkown case type !!"); break;
			}
		}
		return board; 
	}
	
	public static boolean isWon(String gamename)
	{
		try
		{	
			String query = "SELECT isWon FROM game WHERE name=?";
			PreparedStatement stmt = cnx.prepareStatement(query);
			
			stmt.setString(1, gamename);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
				return res.getBoolean(1);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
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
	
	public void setWon(boolean won) {
		this.isWon = won;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() 
	{
		return "OthelloGame [id=" + id + ", name=" + name + ", owner=" + owner + ", date=" + date + ", type=" + type
				+ ", moves=" + moves + ", isWon=" + isWon + ", state=" + state + "]";
	}
	
	
}
