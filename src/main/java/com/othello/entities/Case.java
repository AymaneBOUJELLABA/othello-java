/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.othello.entities;

/**
 *
 * @author macbookpro
 */
public class Case 
{
    
    private int indexX,indexY;
    private CaseValue value;
    private boolean isPossibleMove;
    
    public Case(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.value = CaseValue.EMPTY;
    }
    
    public Case(int indexX, int indexY, boolean possible) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.value = CaseValue.EMPTY;
        this.isPossibleMove = possible;
    }

    public Case(int indexX, int indexY, CaseValue value) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.value = value;
    }
    
    public Case(int indexX, int indexY, CaseValue value, boolean possible) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.value = value;
        this.isPossibleMove = possible;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public CaseValue getValue() {
        return value;
    }

    public void setValue(CaseValue value) {
        this.value = value;
    }

    public boolean isPossibleMove() {
        return isPossibleMove;
    }

    public void setIsPossibleMove(boolean isPossibleMove) {
        this.isPossibleMove = isPossibleMove;
    }
}
