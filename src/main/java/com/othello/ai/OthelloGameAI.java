package com.othello.ai;

import com.othello.entities.Case;
import com.othello.entities.CaseValue;
import com.othello.ui.BoardPanel;

//OUR OTHELLO !!
public class OthelloGameAI extends GameSearch
{	
	BoardPanel gamepanel;
	private int [] score = { 100, -20, 10, 5, 5, 10, -20,100,
				            -20, -50, -2, -2, -2, -2, -50, -20,
				            10, -2, -1, -1, -1,-1, -2, 10,
				            5, -2, -1, -1, -1, -1, -2, 5,
				            5, -2, -1, -1, -1, -1, -2, 5,
				            10, -2, -1, -1, -1,-1, -2, 10,
				            -20, -50, -2, -2, -2, -2, -50, -20,
				            100, -20, 10, 5, 5, 10, -20,100};
	
	public OthelloGameAI(BoardPanel gamePanel)
	{
		this.gamepanel = gamePanel;
	}
	
	//CHECK IF THE BOARD IS FULL
	@Override
	public boolean drawnPosition(Position p)
	{	
		othelloPosition pos = (othelloPosition) p;
		
		for(int i=0;i<64;i++)
			if(pos.board[i].getValue()==CaseValue.EMPTY)
				return false;

		return true;
	}

	@Override
	public boolean wonPosition(Position p, boolean player)
	{
		boolean iswon = false;
		
		othelloPosition pos = (othelloPosition) p;
		
		int player_Score = 0, ai_Score = 0, BlankNum = 0;
		for(int i=0;i<64;i++)
		{
			switch(pos.board[i].getValue())
			{
				case BLACK: player_Score++; break;
				case WHITE: ai_Score++; break;
				case EMPTY: BlankNum++; break;
				default : BlankNum++; break;
			}
		}
		
		if(player)
		{
			if(ai_Score == 0)
				iswon = true;
			else if(BlankNum == 0 && player_Score > ai_Score)
				iswon = true;
		}
		else
		{
			if(player_Score == 0)
				iswon = true;
			else if(BlankNum == 0 && player_Score < ai_Score)
				iswon = true;
		}
		return iswon;
	}

	@Override
	public float positionEvaluation(Position p, boolean player)
	{
		int count = 0;
		float evaluation = 0;
		othelloPosition pos = (othelloPosition) p; 
		
		for(int i=0; i<64 ;i++)
		{
			if(player && pos.board[i].getValue() == CaseValue.BLACK)
				count ++;
			else if(!player && pos.board[i].getValue() == CaseValue.WHITE)
				count ++;
		}
		
		for(int i=0; i<64; i++)
        {
            if(pos.board[i].getValue()==CaseValue.BLACK)
            	evaluation+=score[i];
            else if(pos.board[i].getValue()==CaseValue.WHITE)
            	evaluation-=score[i];
        }
		
		if (wonPosition(p, player)) 
        {
            return evaluation + (1.0f / 65-count);
        }
        if (wonPosition(p, !player)) 
        {
            return -(evaluation + (1.0f / 65-count));
        }
        return evaluation;
	}

	//either print in console or draw the position to the UI
	@Override
	public void printPosition(Position p)
	{
		
	}
	
	//mouad this for you
	@Override
	public Position[] possibleMoves(Position p, boolean player)
	{
		
		return null;
	}
	//oh also this HURRY UP
	@Override
	public Position makeMove(Position p, boolean player, Move move)
	{
		
		return null;
	}

	@Override
	public boolean reachedMaxDepth(Position p, int depth)
	{
		boolean ret = false;
        if(depth>=5)
        	ret = true;
        else if (wonPosition(p, false) || wonPosition(p, true) || drawnPosition(p))
	        ret = true;
        
        return ret;
	}

	//YOU HAVE TO DO THIS TOO !!!
	@Override
	public Move createMove()
	{
		
		return null;
	}
	
}
