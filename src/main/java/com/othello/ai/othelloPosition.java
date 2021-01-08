package com.othello.ai;

import com.othello.entities.Case;
import com.othello.entities.CaseValue;

public class othelloPosition extends Position
{
    public Case[] board = new Case[64];
    
    public othelloPosition()
    {
    	Case[][] defaultBoard = new Case[8][8];
        for (int i = 0; i < 8; i++) 
        {
            for (int j = 0; j < 8; j++) 
            {
                defaultBoard[i][j] = new Case(i,j);
            }
        }
        // Adding the initial pieces
        defaultBoard[3][3].setValue(CaseValue.WHITE);
        defaultBoard[3][4].setValue(CaseValue.BLACK);
        defaultBoard[4][4].setValue(CaseValue.WHITE);
        defaultBoard[4][3].setValue(CaseValue.BLACK);
        
        
        board = Case.getTable(defaultBoard);
    }
    
    public othelloPosition(Case[][] CaseBoard)
    {
        board = Case.getTable(CaseBoard);
    }
    
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer("[");
        for (int i=0; i<64; i++) {
            sb.append(""+board[i]+",");
        }
        sb.append("]");
        return sb.toString();
    }
}
