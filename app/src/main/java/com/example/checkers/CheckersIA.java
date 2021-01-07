package com.example.checkers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CheckersIA {
    private CheckersMap checkersMap = null;

    CheckersIA(CheckersMap map) {
        this.checkersMap = map;
    }

    public Piece moveIA(int lvl, int depth) {
        Piece piece = null;
        switch(lvl) {
            case 0:
                piece = easyIA();
                break;
            case 1:
                piece = mediumIA();
                break;
            case 2:
                piece = hardIA(depth);
                break;
        }

        return piece;
    }

    private LinkedList<Piece> getValidPieces() {
        LinkedList<Piece> validPieces = new LinkedList<Piece>();
        LinkedList<Piece> iaPieces = checkersMap.getIaPieces();

        for (Piece piece : iaPieces) {
            int hasMoves = piece.checkValidMoves(checkersMap.getMap());

            if (hasMoves > 0) {
                validPieces.add(piece);
            }
        }

        return validPieces;
    }

    private Piece easyIA() {
        LinkedList<Piece> validPieces = getValidPieces();
        Piece p = selectRandomPiece(validPieces);

        return p;
    }

    private Piece mediumIA() {
        LinkedList<Piece> validPieces = getValidPieces();
        Piece p = null;

        for (Piece piece: validPieces) {
            if (piece.canEat()) p = piece;
        }

        if (p == null) {
            p = selectRandomPiece(validPieces);
        }

        return p;
    }

    private Piece hardIA(int depth) {
        Piece p = mediumIA();
        /* MinMax */
            // Take each valid iaPiece
            // Set the score to each movement of each valid iaPiece
            // Get the best movement and do it

        /* Idea for MinMax with depth */
            // Take each valid iaPiece
            // Set the score to each movement of each valid iaPiece
            // Try every movement (on a copy map)
            // Get the best movement of the player (best response)
            // Set the best response score
            // triedMovementScore = triedMovementScore - bestResponseScore
            // Repeat it recursively
            // Get the best movement and do it
        return p;
    }

        private Piece selectRandomPiece(LinkedList<Piece> validPieces) {
        Piece selected = null;

        if (validPieces.size() > 0) {
            Random rand = new Random();
            int upperbound = validPieces.size();
            int random = rand.nextInt(upperbound);

            selected = validPieces.get(random);
            Collections.sort(selected.getValidMoves());

            upperbound = selected.getValidMoves().size();
            random = rand.nextInt(upperbound);
            selected.setMovement(selected.getValidMoves().get(random));
        }

        return selected;
    }
}
