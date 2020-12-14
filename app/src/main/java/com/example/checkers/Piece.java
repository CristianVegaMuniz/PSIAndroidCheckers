package com.example.checkers;

import java.util.LinkedList;

public class Piece {
    private int x;
    private int y;

    private int type = 0;
    private boolean isKing = false;
    private Movement movement = new Movement(this);
    private boolean isEated = false;
    private LinkedList<Movement> validMoves;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Piece(Piece p) {
        this.x = p.getX();
        this.y = p.getY();
        this.type = p.getType();
        this.isKing = p.isKing();
        this.movement = new Movement(p.getMovement());
        this.movement.setPiece(this);
        this.isEated = p.isEated();

        if (p.getValidMoves() != null) {
            this.validMoves = new LinkedList<Movement>(p.getValidMoves());
        } else {
            this.validMoves = new LinkedList<Movement>();
        }
    }

    void move(int x, int y) {
        movement = new Movement(this, x, y);
        return;
    }

    public boolean isEated() {
        return isEated;
    }

    public void setEated(boolean isEated) {
        this.isEated = isEated;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean isKing) {
        this.isKing = isKing;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
        movement.setPiece(this);
    }

    public void setValidMoves(LinkedList<Movement> validMoves) {
        this.validMoves = validMoves;
    }

    public LinkedList<Movement> getValidMoves() {
        return validMoves;
    }

    public boolean checkValidMoves(Piece[][] map) {
        boolean hasMoves = false;
        validMoves = MoveChecker.getMovements(this, map);

        if (validMoves.size() > 0) {
            hasMoves = true;
        }

        return hasMoves;
    }

}
