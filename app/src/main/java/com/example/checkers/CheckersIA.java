package com.example.checkers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CheckersIA {
    int level = 0;
    CheckersMap checkersMap = null;

    CheckersIA(CheckersMap map) {
        this.checkersMap = map;
    }

    public Piece moveIA(int lvl) {
        level = lvl;
        Piece piece = null;
        switch(level) {
            case 0:
                piece = easyIA();
                break;
            case 1:
                piece = mediumIA();
                break;
            case 2:
                break;
        }

        return piece;
    }

    private Piece easyIA() {
        LinkedList<Piece> movePieces = new LinkedList<Piece>();
        LinkedList<Piece> iaPieces = checkersMap.getIaPieces();

        Movement newMovement = null;
        Piece p = null;

        for (Piece piece : iaPieces) {
            int hasMoves = piece.checkValidMoves(checkersMap.getMap());

            if (hasMoves > 0) {
                movePieces.add(piece);
            }
        }

        if (movePieces.size() > 0) {
            Random rand = new Random();
            int upperbound = movePieces.size();
            int random = rand.nextInt(upperbound);

            p = movePieces.get(random);

            Collections.sort(p.getValidMoves());

            upperbound = p.getValidMoves().size();
            random = rand.nextInt(upperbound);
            newMovement = p.getValidMoves().get(random);
            p.setMovement(newMovement);
        }

        return p;
    }

    private Piece mediumIA() {
        LinkedList<Piece> movePieces = new LinkedList<Piece>();
        LinkedList<Piece> iaPieces = checkersMap.getIaPieces();

        Movement newMovement = null;
        Piece p = null;

        for (Piece piece : iaPieces) {
            int hasMoves = piece.checkValidMoves(checkersMap.getMap());

            if (hasMoves > 0) {
                movePieces.add(piece);
                if (piece.canEat()) {
                    p = piece;
                }
            }
        }

        if (movePieces.size() > 0) {
            if (p == null) {
                Random rand = new Random();
                int upperbound = movePieces.size();
                int random = rand.nextInt(upperbound);

                p = movePieces.get(random);

                Collections.sort(p.getValidMoves());

                upperbound = p.getValidMoves().size();
                random = rand.nextInt(upperbound);
                newMovement = p.getValidMoves().get(random);
                p.setMovement(newMovement);
            }
        }

        return p;
    }
}
