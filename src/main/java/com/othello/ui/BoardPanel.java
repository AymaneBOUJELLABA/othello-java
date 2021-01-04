package com.othello.ui;

import com.othello.classes.OthelloGame;
import com.othello.entities.Case;
import com.othello.entities.CaseValue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends javax.swing.JPanel {

    public Case[][] board;
    public CaseValue turn;
    static int SCORE_HEIGHT = 75;
    public OthelloGame gamedata;

    public BoardPanel(Case[][] board,OthelloGame gamedata){
        this.board = board;
        this.turn = CaseValue.BLACK;
        this.gamedata = gamedata;
        initComponents();
        calculatePossibleMoves();
        addMouseListener(new MouseAdapter(){
            @Override 
           public void mouseReleased(MouseEvent e) {
                int j = e.getX() / getCaseWidth();
                int i = (e.getY() - SCORE_HEIGHT) / getCaseHeight();
                playTurn(i, j);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {

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
        g2d.drawString("Tour", getWidth() - 55, 35);

        
        g2d.translate(0, SCORE_HEIGHT);
        // draw the cases
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = j * getCaseWidth();
                int y = i * getCaseHeight();
                g2d.setColor(Color.black);
                g2d.drawRect(x, y, getCaseWidth(), getCaseHeight());
                if (board[i][j].getValue() == CaseValue.EMPTY) {
                    if (board[i][j].isPossibleMove()) {
                        g2d.drawOval(x + 10, y + 10, getCaseWidth() - 20, getCaseHeight() - 20);
                    }
                    continue;
                }

                g2d.setColor(board[i][j].getValue() == CaseValue.BLACK ? Color.black : Color.white);
                g2d.fillOval(x + 10, y + 10, getCaseWidth() - 20, getCaseHeight() - 20);
            }
        }
    }

    public int getCaseWidth() {
        return getWidth() / 8;
    }

    public int getCaseHeight() {
        return (getHeight() - SCORE_HEIGHT) / 8;
    }

    public void playTurn(int i, int j) {
        if (!isSafe(i, j) || !board[i][j].isEmpty() || !board[i][j].isPossibleMove()) {
            return;
        }
        board[i][j].setValue(turn);
        gamedata.addMove(turn,i,j);
        flipPieces(i, j);
        switchTurn();        
        calculatePossibleMoves();
        repaint();
    }

    public void switchTurn() {
        turn = turn == CaseValue.BLACK ? CaseValue.WHITE : CaseValue.BLACK;
    }

    public void flipPieces(int row, int col) {
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {

                if (dRow == 0 && dCol == 0) {
                    continue;
                }
                if (checkDir(row, col, dRow, dCol)) {
                    for (int i = row + dRow, j = col + dCol; i < 8 && i >= 0 && j < 8 && j >= 0; i += dRow, j += dCol) {
                        if (board[i][j].isEmpty()) {
                            break;
                        }
                        board[i][j].setValue(turn);
                    }
                }
            }
        }
    }

    public void calculatePossibleMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty()) {
                    continue;
                }
                board[i][j].setIsPossibleMove(checkCase(i, j));
            }
        }

    }

    private boolean checkCase(int i, int j) {
        for (int k = -1; k <= 1; k++) {
            for (int m = -1; m <= 1; m++) {
                if (k == 0 && m == 0) {
                    continue;
                }
                if (checkDir(i, j, k, m)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDir(int row, int col, int dRow, int dCol) {
        boolean foundOther = false;
        for (int i = row + dRow, j = col + dCol; i < 8 && i >= 0 && j < 8 && j >= 0; i += dRow, j += dCol) {
            // We reached the borders
            if (board[i][j].isEmpty()) {
                return false;
            }
            if (!isSafe(i + dRow, j + dCol)) {
                continue;
            }

            if (board[i][j].getValue() != turn) {
                foundOther = true;
            }
            if (board[i + dRow][j + dCol].isEmpty() && board[i][j].getValue() == turn && foundOther) {
                return true;
            }
        }
        return false;
    }

    private boolean isSafe(int i, int j) {
        return i < 8 && j < 8 && i >= 0 && j >= 0;
    }
    
    private int getBoardCount(CaseValue value){
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
