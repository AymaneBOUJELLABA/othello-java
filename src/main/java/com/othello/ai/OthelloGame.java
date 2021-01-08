package com.othello.ai;

import com.othello.entities.Case;
import com.othello.ui.BoardPanel;

//OUR OTHELLO !!
public class OthelloGame extends GameSearch
{
	private BoardPanel gamepanel;
	
	private int [] score = { 100, -20, 10, 5, 5, 10, -20,100,
            -20, -50, -2, -2, -2, -2, -50, -20,
            10, -2, -1, -1, -1,-1, -2, 10,
            5, -2, -1, -1, -1, -1, -2, 5,
            5, -2, -1, -1, -1, -1, -2, 5,
            10, -2, -1, -1, -1,-1, -2, 10,
            -20, -50, -2, -2, -2, -2, -50, -20,
            100, -20, 10, 5, 5, 10, -20,100};
	
	@Override
	//CHECK IF THE BOARD IS FULL
	public boolean drawnPosition(Position p)
	{	
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wonPosition(Position p, boolean player)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float positionEvaluation(Position p, boolean player)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printPosition(Position p)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position[] possibleMoves(Position p, boolean player)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position makeMove(Position p, boolean player, Move move)
	{
		// TODO Auto-generated method stu
		
		return null;
	}

	@Override
	public boolean reachedMaxDepth(Position p, int depth)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Move createMove() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
