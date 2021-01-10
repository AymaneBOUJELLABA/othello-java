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
    
    public othelloPosition(Case[][] CaseBoard, int i, int j,CaseValue turn)
    {
        board = Case.getTable(CaseBoard);
        
        board[(i*8)+j].setValue(CaseValue.WHITE);
        
        board = Case.getTable(this.flipPieces(turn,i, j));
    }
    
    public Case[][] flipPieces(CaseValue turn,int row, int col)
    {
    	Case[][] board = Case.get2DTable(this.board);
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j].setIsFlipped(false);
            }
        }
    	
        for (int dRow = -1; dRow <= 1; dRow++)
        {
            for (int dCol = -1; dCol <= 1; dCol++)
            {

                if (dRow == 0 && dCol == 0)
                {
                    continue;
                }
                if (checkDir(turn,board,row, col, dRow, dCol))
                {
                    int lastI = 1000, lastJ = 1000;
                    for (int i = row + dRow, j = col + dCol; i < 8 && i >= 0 && j < 8 && j >= 0; i += dRow, j += dCol) {
                        if (board[i][j].isEmpty())
                        {
                            break;
                        }
                        if(board[i][j].getValue() == turn)
                        		{
                            lastI = i;
                            lastJ = j;
                        }
                    }
                    
                    for (int i = row + dRow, j = col + dCol; i < 8 && i >= 0 && j < 8 && j >= 0; i += dRow, j += dCol) {
                        if (board[i][j].isEmpty()) {
                            break;
                        }
                        board[i][j].setValue(turn);
                        board[i][j].setIsFlipped(true);
                        if(lastI == i && lastJ == j){
                            break;
                        }
                    }
                }
            }
        }
        return board;
    }
    private boolean checkDir(CaseValue turn,Case[][] board,int row, int col, int dRow, int dCol)
    {
    	
        boolean foundOther = false;
        for (int i = row + dRow, j = col + dCol; i < 8 && i >= 0 && j < 8 && j >= 0; i += dRow, j += dCol) {
            // We reached the borders
            if (board[i][j].isEmpty()) {
                return false;
            }
            if (board[i][j].getValue() != turn) {
                foundOther = true;
            }
            if (board[i][j].getValue() == turn && foundOther) {
                return true;
            }
        }
        return false;
    }

    private boolean isSafe(int i, int j) {
        return i < 8 && j < 8 && i >= 0 && j >= 0;
    }
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer("[");
        for (int i=0; i<64; i++)
        {
            sb.append(""+board[i].getValue()+",");
            if(i%8==0)
            	sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
