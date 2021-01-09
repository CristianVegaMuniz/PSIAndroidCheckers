package com.example.checkers;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CheckersIA {
    private CheckersMap checkersMap = null;
    private int level = 0;
    private int depth = 1;

    CheckersIA(CheckersMap map, int level, int depth) {
        this.checkersMap = map;
        this.level = level;
        this.depth = depth;
    }

    public Piece moveIA() {
        Piece piece = null;
        switch(this.level) {
            case 0:
                piece = easyIA();
                break;
            case 1:
                piece = mediumIA(this.depth);
                break;
            case 2:
                piece = hardIA(this.depth);
                break;
        }

        if (piece != null) {
            System.out.println("\nIA selected the movement: " + piece.getMovement().toString() + "\n");
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
        Movement bestMovement = null;
        // Get the valid pieces for the IA on the map passed by parameters
        LinkedList<Piece> iaPieces = checkersMap.getIaPieces();

        for (Piece iaPiece: iaPieces) {
            // Per each valid pieces we will test every movement
            int hasMoves = iaPiece.checkValidMoves(checkersMap.getMap());
            if (hasMoves > 0) {
                for (Movement movement: iaPiece.getValidMoves()) {
                    // Create copy objects of everything we will use
                    Piece testPiece = new Piece(iaPiece);
                    Movement testMovement = new Movement(movement);
                    CheckersMap testMap = new CheckersMap(checkersMap);

                    // We don't need the scores to move the piece
                    testPiece.setMovement(testMovement);
                    testMap.movePiece(testPiece);

                    // Now we tested the movement, lets set the score of it
                    movement.setScore(calculateMovementScore(movement, iaPiece, checkersMap));
                    //System.out.println("DEPTH: " + depth + " - IA movement: " + movement.toString());

                    // Now we look for the best player response, his best movement
                    Piece bestPlayerResponse = getBestPlayerResponse(testMap);

                    // If the player has a valid movement get the score and update our movement score
                    if (bestPlayerResponse != null) {
                        bestPlayerResponse.getMovement().setScore(calculateMovementScore(bestPlayerResponse.getMovement(), bestPlayerResponse, testMap));
                        //System.out.println("\tBest response movement: " + bestPlayerResponse.getMovement().toString());
                        movement.setScore(movement.getScore() - bestPlayerResponse.getMovement().getScore());
                        //System.out.println("\tMovement updated: " + movement.toString());

                        testMap.movePiece(bestPlayerResponse);
                    }

                    // Per each movement if depth > 1 we have to repeat the entire process for that map state
                    if (depth > 1) {
                        Piece depthPiece = minimax(depth-1, testMap);

                        // If on that sub-state we have some valid movement we add the score of it to our movement score
                        if (depthPiece != null) {
                            movement.setScore(movement.getScore() + depthPiece.getMovement().getScore());
                        }
                    }

                    if (bestMovement == null || bestMovement.getScore() < movement.getScore()) {
                        bestMovement = movement;
                        p = iaPiece;
                    }
                }
            }

        }

        if (p != null) {
            p.setMovement(bestMovement);
        }

        return p;
    }

    private Piece getBestPlayerResponse(CheckersMap copyMap) {
        Piece bestPlayerResponse = null;
        LinkedList<Piece> playerPieces = getValidPieces(copyMap, false);
        if (playerPieces.size() == 0) return null;

        for (Piece piece: playerPieces) {
            for (Movement movement: piece.getValidMoves()) {
                movement.setScore(calculateMovementScore(movement, piece, copyMap));
            }
            Collections.sort(piece.getValidMoves());
        }
        Collections.sort(playerPieces);

        bestPlayerResponse = playerPieces.getFirst();
        bestPlayerResponse.setMovement(bestPlayerResponse.getValidMoves().getFirst());

        return bestPlayerResponse;
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

    private int calculateMovementScore(Movement movement, Piece piece, CheckersMap map) {
        int score = 0;
        boolean eatable = willBeEatable(movement, piece, map);
        if (eatable) {
            score -= 10;
            if (piece.isKing()) {
                score -= 5;
            }
        }

        // Puntuación laterales (non poden comer)
        if (movement.getGoY() == 0 || movement.getGoY() == 7) {
            score += 4;
        } else if (movement.getGoY() == 1 || movement.getGoY() == 6) {
            score += 3;
        } else if (movement.getGoY() == 2 || movement.getGoY() == 5) {
            score += 2;
        } else if (movement.getGoY() == 3 || movement.getGoY() == 4) {
            score += 3;
        }

        // Puntuación coronar
        if (movement.getGoX() == 7) {
            // si xa e dama como o lateral
            if (piece.isKing()) {
                score += 4;
            } else {
                score += 10;
            }
        } else {
            if (!piece.isKing() && piece.getType() == 2) {
                score += movement.getGoX();
            } else if (!piece.isKing() && piece.getType() == 1) {
                score += (7 - movement.getGoX()) ;

            }
        }

        if (movement.isEatMovement()) {
            if (!eatable) {
                score += 20;
            } else {
                score += 10;
            }

            if (movement.getEatedPiece().isKing()) {
                score += 5;
            }
        }

        return score;
    }

    private boolean willBeEatable(Movement movement, Piece piece, CheckersMap map) {
        if(piece == null) return false;

        CheckersMap tmpMap = new CheckersMap(map);
        Piece tmpPiece = new Piece(piece);
        Movement tmpMovement = new Movement(movement);
        tmpPiece.setMovement(tmpMovement);
        tmpMap.movePiece(tmpPiece);

        if (piece.getType() == 2) {
            LinkedList<Piece> pieces = getValidPieces(tmpMap, false);
            for (Piece p: pieces) {
                for (Movement m: p.getValidMoves()) {
                    if (m.isEatMovement()) {
                        if (m.getEatedPiece().getId() == piece.getId()) {
                            return true;
                        }
                    }
                }
            }
        } else {
            LinkedList<Piece> pieces = getValidPieces(tmpMap, true);
            for (Piece p: pieces) {
                for (Movement m: p.getValidMoves()) {
                    if (m.isEatMovement()) {
                        if (m.getEatedPiece().getId() == piece.getId()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
