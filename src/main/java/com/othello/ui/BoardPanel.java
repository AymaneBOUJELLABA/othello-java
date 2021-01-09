package com.othello.ui;

import com.othello.ai.OthelloGameAI;
import com.othello.ai.Position;
import com.othello.ai.othelloPosition;
import com.othello.classes.OthelloGame;
import com.othello.entities.Case;
import com.othello.entities.CaseValue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class BoardPanel extends javax.swing.JPanel
{

    private OthelloGameAI ai;
    private othelloPosition gameState;
    private Case[][] board;
    public CaseValue turn;
    static int SCORE_HEIGHT = 75;
    
    public OthelloGame gamedata;

    public BoardPanel(Case[][] board,OthelloGame gamedata)
    {
    	this.ai = new OthelloGameAI(this);
    	
        this.board = board;
        this.turn = CaseValue.BLACK;
        this.gamedata = gamedata;
        
        this.gameState = new othelloPosition(this.board);
        
        
        initComponents();
        calculatePossibleMoves(this.board,turn);
      //if PVP
    	addMouseListener(new MouseAdapter()
        {
            @Override 
           public void mouseReleased(MouseEvent e) 
           {
                int j = e.getX() / getCaseWidth();
                int i = (e.getY() - SCORE_HEIGHT) / getCaseHeight();
                playTurn(i, j);
           }
        });

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }
    

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // draw the green bg
        g2d.setColor(new Color(32, 144, 103));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(Color.black);
        g2d.fillOval(getWidth()/2 - getCaseWidth()/2 - 35, 10, getCaseWidth() - 20, getCaseHeight() - 20);
        
        g2d.setColor(Color.white);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,  30));
        g2d.drawString("" + getBoardCount(CaseValue.BLACK), getWidth()/2 - getCaseWidth()/2 - 10, 45);
        
        g2d.fillOval(getWidth()/2 + getCaseWidth()/2 - 35, 10, getCaseWidth() - 20, getCaseHeight() - 20);
        
        g2d.setColor(Color.black);
        g2d.drawString("" + getBoardCount(CaseValue.WHITE), getWidth()/2 + getCaseWidth()/2 - 10, 45);
        
        g2d.setColor(turn == CaseValue.BLACK ? Color.black : Color.white);
        
        g2d.fillOval(getWidth() - 70, 10, getCaseWidth() - 20, getCaseHeight() - 20);
        g2d.setColor(turn == CaseValue.WHITE ? Color.black : Color.white);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,  16));
        g2d.drawString("Tour", getWidth() - getCaseWidth()/2 - 5 , getCaseHeight()/2 +  3);

        
        g2d.translate(0, SCORE_HEIGHT);
        // draw the cases
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = j * getCaseWidth();
                int y = i * getCaseHeight();
                g2d.setColor(Color.black);
                g2d.drawRect(x, y, getCaseWidth(), getCaseHeight());
                if (board[i][j].getValue() == CaseValue.EMPTY)
                {
                    if (board[i][j].isPossibleMove())
                    {
                        g2d.drawOval(x + 10, y + 10, getCaseWidth() - 20, getCaseHeight() - 20);
                    }
                    continue;
                }

                g2d.setColor(board[i][j].getValue() == CaseValue.BLACK ? Color.black : Color.white);
                g2d.fillOval(x + 10, y + 10, getCaseWidth() - 20, getCaseHeight() - 20);
                
                if(board[i][j].isIsLastMove()){
                    g2d.setColor(Color.red);
                    g2d.fillOval(x + 10 + getCaseWidth()/2 - getCaseWidth()/5 , y + 10 + getCaseHeight()/2 - getCaseHeight()/5 , getCaseWidth()/5, getCaseHeight()/5);
                }
            }
        }
    }

    public int getCaseWidth() {
        return getWidth() / 8;
    }

    public int getCaseHeight() {
        return (getHeight() - SCORE_HEIGHT) / 8;
    }
    
    public void playTurn(int i, int j)
    {
        if (!isSafe(i, j) || !board[i][j].isEmpty() || !board[i][j].isPossibleMove()) {
            return;
        }
        for (int k = 0; k < 8; k++)
        {
            for (int m = 0; m < 8; m++)
            {
                board[k][m].setIsLastMove(false);
            }
        }
        board[i][j].setValue(turn);
        board[i][j].setIsLastMove(true);
        for (int k = 0; k < 8; k++)
        {
            for (int m = 0; m < 8; m++)
            {
                board[k][m].setIsPossibleMove(false);
            }
        }
        repaint();
        Thread thread = new Thread(){
            public void run(){
                synchronized(board){
                    gamedata.addMove(turn,i,j);
                    flipPieces(i, j);
                    switchTurn();
                    repaint();
                    if(gamedata.getType() == 1 && turn==CaseValue.WHITE)
                    {
                        othelloPosition pos = new othelloPosition(Case.cloneBoard(board));
                        repaint();
                        ai.playturn(pos, false);
                        switchTurn();
                    }
                    calculatePossibleMoves(board,turn);
                    repaint();
                }
            }
        };
        if(checkGameState()){
            return;
        }
        thread.start();
        
    }

    public void switchTurn() {
        turn = turn == CaseValue.BLACK ? CaseValue.WHITE : CaseValue.BLACK;
    }

    public void flipPieces(int row, int col) {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j].setIsFlipped(false);
            }
        }
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {

                if (dRow == 0 && dCol == 0) {
                    continue;
                }
                if (checkDir(row, col, dRow, dCol,turn)) {
                    int lastI = 1000, lastJ = 1000;
                    for (int i = row + dRow, j = col + dCol; i < 8 && i >= 0 && j < 8 && j >= 0; i += dRow, j += dCol) {
                        if (board[i][j].isEmpty()) {
                            break;
                        }
                        if(board[i][j].getValue() == turn){
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
    }
    public othelloPosition getBoardState()
    {
    	gameState.board = Case.getTable(board);
    	
    	return this.gameState;
    }
    
    public boolean checkGameState(){
        int count = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(board[i][j].isEmpty())
                    count++;
            }
        }
        
        if(count == 0){
            gameFinished();
            return true;
        }
        
        return false;
    }
    
    public Position[] calculatePossibleMoves(Case[][] board,CaseValue turn)
    {	
    	int count = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (!board[i][j].isEmpty())
                {
                    continue;
                }
                
                boolean possible = checkCase(i, j,turn);
                board[i][j].setIsPossibleMove(possible);
                
                if(possible)
                    count++;
            }
        }
        
        if(count == 0 && (this.gamedata.getType() == 0 ||this.gamedata.getType() == 1 && turn == CaseValue.BLACK)){
            switchTurn();
            return null;
        }
        
        othelloPosition[] positions = new othelloPosition[count];
        int k=0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
            	if(board[i][j].isPossibleMove())
            	{
                    if(k<positions.length)
                    {
                            positions[k] = new othelloPosition(Case.cloneBoard(board),i,j,turn);
                            k++;
                    }
            	}
            	
            }
        }
        return positions;
    }

    private boolean checkCase(int i, int j,CaseValue turn)
    {
        for (int k = -1; k <= 1; k++) {
            for (int m = -1; m <= 1; m++) {
                if (k == 0 && m == 0) {
                    continue;
                }
                if (checkDir(i, j, k, m,turn) && adjacentToEnemy(i,j, turn)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDir(int row, int col, int dRow, int dCol,CaseValue turn) {
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
    
    public int getBoardCount(CaseValue value)
    {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j].getValue() == value) count++;
            }
        }
        return count;
    }
    public Case[][] getBoard()
    {
    	return this.board;
    }
    public void setBoard(Position p)
    {
        othelloPosition pos = (othelloPosition) p;

        Case[][] newBoard = Case.get2DTable(pos.board);
        
        int lastI = 100, lastJ = 100;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j].setIsLastMove(false);
                if(newBoard[i][j].getValue() != board[i][j].getValue() && !newBoard[i][j].isIsFlipped()){
                    System.out.println("AI will play at " + i + ", " + j);
                    this.gamedata.addMove(CaseValue.WHITE, i, j);
                    lastI = i;
                    lastJ = j;
                }
            }
        }
    	
    	this.board = Case.get2DTable(pos.board);
    	if(lastI < 8 && lastJ < 8)
            this.board[lastI][lastJ].setIsLastMove(true);
    	gamedata.setStatefromBoard(board);
    	gameState.board = Case.getTable(board);
    	repaint();
        
        
        if(checkGameState()){
            return;
        }
    }
    
    public void gameFinished(){
        int whiteCount = getBoardCount(CaseValue.WHITE), blackCount = getBoardCount(CaseValue.BLACK);
        
        String message;
        if(whiteCount == blackCount){
            message = "Le jeu était un match nul!";
        }else{
            message =  "Le " + (blackCount > whiteCount ? "noir" : "blanc") + " était victorieux!";
        }
        
        if(this.gamedata.getType() == 1 && blackCount > whiteCount)
            message += "Bien joue!";
        
        JOptionPane.showMessageDialog(this, message);
    }
    
    private boolean adjacentToEnemy(int i, int j, CaseValue turn){
        for (int k = -1; k <= 1; k++) {
            for (int m = -1; m <= 1; m++) {
                /*
                -1 -1
                -1 0
                -1 1
                
                0 -1
                0 1
                
                1 -1
                1 0 
                1 1
                */
                if(m == 0 && k == 0 || !isSafe(i + k, j + m)) continue;
                
                if(board[i + k][j + m].getValue() != turn && board[i + k][j + m].getValue() != CaseValue.EMPTY){
                    return true;
                }
            }
        }
        
        return false;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
