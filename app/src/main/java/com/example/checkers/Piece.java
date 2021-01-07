package com.example.checkers;

import java.util.Collections;
import java.util.LinkedList;

public class Piece implements Comparable<Piece>{
    private int x;
    private int y;

    private int id = 0;
    private int type = 0;
    private boolean isKing = false;
    private Movement movement = new Movement(this);
    private boolean canEat = false;
    private LinkedList<Movement> validMoves;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Piece(Piece p) {
        this.x = p.x;
        this.y = p.y;
        this.id = p.id;
        this.type = p.type;
        this.isKing = p.isKing;
        this.movement = new Movement(p.movement);
        this.movement.setPiece(this);
        this.canEat = p.canEat;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCanEat(boolean canEat) {
        this.canEat = canEat;
    }

    public boolean canEat() {
        return canEat;
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

    public int checkValidMoves(Piece[][] map) {
        int hasMoves = 0;
        canEat = false;
        validMoves = MoveChecker.getMovements(this, map);

        if (validMoves.size() > 0) {
            hasMoves = 1;
            for (Movement movement: validMoves) {
                if (movement.isEatMovement()) {
                    setMovement(movement);
                    canEat = true;
                    hasMoves = 2;
                } else if (this.movement == null) {
                    setMovement(movement);
                }
            }
        }

        Collections.sort(validMoves);
        return hasMoves;
    }

    /* Only use when the piece has movements. The list of movements must be sorted */
    @Override
    public int compareTo(Piece p) {
        if (this.validMoves.getFirst().getScore() > p.getValidMoves().getFirst().getScore()) return -1;
        if (this.validMoves.getFirst().getScore() < p.getValidMoves().getFirst().getScore()) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "Piece on: " + x + "-" + y;
    }

}