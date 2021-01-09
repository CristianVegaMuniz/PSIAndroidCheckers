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
                piece = mediumIA(depth);
                break;
            case 2:
                piece = hardIA(depth);
                break;
        }

        return piece;
    }

    private LinkedList<Piece> getValidPieces(CheckersMap map, Boolean IA) {
        LinkedList<Piece> validPieces = new LinkedList<Piece>();
        if (IA) {
            LinkedList<Piece> iaPieces = map.getIaPieces();

            for (Piece piece : iaPieces) {
                int hasMoves = piece.checkValidMoves(map.getMap());

                if (hasMoves > 0) {
                    validPieces.add(piece);
                }
            }
        } else {
            LinkedList<Piece> playerPieces = map.getPlayerPieces();

            for (Piece piece : playerPieces) {
                int hasMoves = piece.checkValidMoves(map.getMap());

                if (hasMoves > 0) {
                    validPieces.add(piece);
                }
            }
        }

        return validPieces;
    }

    private Piece easyIA() {
        /*
        LinkedList<Piece> validPieces = getValidPieces(checkersMap, true);
        Piece p = selectRandomPiece(validPieces);
        */
        LinkedList<Piece> validPieces = getValidPieces(checkersMap, true);
        Piece p = null;

        for (Piece piece: validPieces) {
            if (piece.canEat()) p = piece;
        }

        if (p == null) {
            p = selectRandomPiece(validPieces);
        }

        return p;
    }

    private Piece mediumIA(int depth) {
        /*
        LinkedList<Piece> validPieces = getValidPieces(checkersMap, true);
        Piece p = null;

        for (Piece piece: validPieces) {
            if (piece.canEat()) p = piece;
        }

        if (p == null) {
            p = selectRandomPiece(validPieces);
        }
        */

        Piece p = minimax(depth, checkersMap);

        return p;
    }

    private Piece hardIA(int depth) {
        Piece p = minimax(depth, checkersMap);

        return p;
    }

    private  Piece minimax(int depth, CheckersMap checkersMap) {
        Piece p = null;
        // Get the valid pieces for the IA on the map passed by parameters
        LinkedList<Piece> validPieces = getValidPieces(checkersMap, true);
        if (validPieces.size() == 0) return null;

        for (Piece iaPiece: validPieces) {
            // Per each valid pieces we will test every movement
            for (Movement movement: iaPiece.getValidMoves()) {
                // Create copy objects of everything we will use
                Piece testPiece = new Piece(iaPiece);
                Movement testMovement = new Movement(movement);
                CheckersMap testMap = new CheckersMap(checkersMap);

                // We don't need the scores to move the piece
                testPiece.setMovement(testMovement);
                testMap.movePiece(testPiece);

                // Now we tested the movement, lets set the score of it
                movement.setScore(calculateMovementScore(true, movement, iaPiece));

                // Now we look for the best player response, his best movement
                Movement bestPlayerResponse = getBestPlayerMovement(testMap);

                // If the player has a valid movement get the score and update our movement score
                if (bestPlayerResponse != null) {
                    bestPlayerResponse.setScore(calculateMovementScore(false, bestPlayerResponse, bestPlayerResponse.getPiece()));
                    movement.setScore(movement.getScore() + bestPlayerResponse.getScore());
                }

                // Per each movement if depth > 1 we have to repeat the entire process for that map state
                if (depth > 1) {
                    Piece depthPiece = minimax(depth-1, testMap);

                    // If on that sub-state we have some valid movement we add the score of it to our movement score
                    if (depthPiece != null) {
                        movement.setScore(movement.getScore() + depthPiece.getMovement().getScore());
                    }
                }
            }
            Collections.sort(iaPiece.getValidMoves());
        }
        Collections.sort(validPieces);
        p = validPieces.getFirst();
        p.setMovement(validPieces.getFirst().getValidMoves().getFirst());

        return p;
    }

    private Movement getBestPlayerMovement(CheckersMap copyMap) {
        Movement bestPlayerMovement = null;
        LinkedList<Piece> playerPieces = getValidPieces(copyMap, false);
        if (playerPieces.size() == 0) return null;

        for (Piece piece: playerPieces) {
            for (Movement movement: piece.getValidMoves()) {
                movement.setScore(calculateMovementScore(false, movement, piece));
            }
            Collections.sort(piece.getValidMoves());
        }
        Collections.sort(playerPieces);

        bestPlayerMovement = playerPieces.getFirst().getValidMoves().getFirst();
        playerPieces.getFirst().setMovement(bestPlayerMovement);

        return bestPlayerMovement;
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

    private int calculateMovementScore(Boolean max, Movement movement, Piece piece) {
        int score = 0;

        if (max) {
            // Puntuación laterales (non poden comer)
            if (movement.getGoY() == 0 || movement.getGoY() == 7) {
                score += 4;
            }
            if (movement.getGoY() == 1 || movement.getGoY() == 6) {
                score += 3;
            }
            if (movement.getGoY() == 2 || movement.getGoY() == 5) {
                score += 2;
            }
            if (movement.getGoY() == 3 || movement.getGoY() == 4) {
                score += 1;
            }
            // Puntuación coronar
            if (movement.getGoX() == 7) {
                // si xa e dama como o lateral
                if (piece.isKing()) {
                    score += 4;
                } else {
                    score += 6;
                }
            }
            if (movement.isEatMovement()) {
                score += 10;
            }
            return score;

        } else {
            // Puntuación laterales (non poden comer)
            if (movement.getGoY() == 0 || movement.getGoY() == 7) {
                score -= 4;
            }
            if (movement.getGoY() == 1 || movement.getGoY() == 6) {
                score -= 3;
            }
            if (movement.getGoY() == 2 || movement.getGoY() == 5) {
                score -= 2;
            }
            if (movement.getGoY() == 3 || movement.getGoY() == 4) {
                score -= 1;
            }
            // Puntuación coronar
            if (movement.getGoX() == 7) {
                // si xa e dama como o lateral
                if (piece.isKing()) {
                    score -= 4;
                } else {
                    score -= 6;
                }
            }
            if (movement.isEatMovement()) {
                score -= 10;
            }
            return score;
        }
    }
}
