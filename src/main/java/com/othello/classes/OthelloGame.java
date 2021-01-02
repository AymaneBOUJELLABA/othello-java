package com.othello.classes;

import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;

public class OthelloGame
{
	
	Connection cnx;
	private Date date;
	private int type; //0 pour pvp //1 pour P v AI
	private String[] moves; //B11,W25,etc 
	
	
	public void save()
	{
		
	}
	
	public OthelloGame()
	{
		super();
		
	}

	public OthelloGame(Date date, int type, String[] moves)
	{
		super();
		this.date = date;
		this.type = type;
		this.moves = moves;
	}

	public static void main(String[] args)
	{
		

	}


	public String[] getMoves() {
		return moves;
	}
	public void setMoves(String[] moves) {
		this.moves = moves;
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





	@Override
	public String toString() {
		return "OthelloGame [date=" + date + ", type=" + type + ", moves=" + Arrays.toString(moves) + "]";
	}
	
	
}
