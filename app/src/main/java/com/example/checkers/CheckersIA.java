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

    public LinkedList<Piece> getValidPieces(CheckersMap map, Boolean IA) {
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
            iaPiece.checkValidMoves(checkersMap.getMap());

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
                //System.out.println("DEPTH: " + depth + " - IA movement to check: " + movement.toString());

                // Now we look for the best player response, his best movement
                Piece bestPlayerResponse = getBestPlayerResponse(testMap);

                // If the player has a valid movement get the score and update our movement score
                if (bestPlayerResponse != null) {
                    //System.out.println("\tRESPONSE DEPTH: " + depth + " - Best response movement: " + bestPlayerResponse.getMovement().toString());
                    movement.setScore(movement.getScore() - bestPlayerResponse.getMovement().getScore());
                    //System.out.println("\tMovement updated: " + movement.toString());

                    testMap.movePiece(bestPlayerResponse);

                    //System.out.println("\tPRE DEPTH: " + depth + " - IA movement before go deeper: " + movement.toString());

                    // Per each movement if depth > 1 we have to repeat the entire process for that map state
                    if (depth > 1) {
                        Piece depthPiece = minimax(depth-1, testMap);

                        // If on that sub-state we have some valid movement we add the score of it to our movement score
                        if (depthPiece != null) {
                            //System.out.println("\tDEEP DEPTH: " + depth + " - IA depth movement : " + depthPiece.getMovement().toString());
                            movement.setScore(movement.getScore() + depthPiece.getMovement().getScore());
                        } else {
                            // If we dont have any response to the best player response we lose
                            movement.setScore(movement.getScore() - 100*depth);
                        }
                    }

                } else {
                    // If the player cannot do anything and we eat all his pieces we win
                    // 100 points for that winning movement * depth
                    if (testMap.getPlayerPieces().isEmpty()) {
                        movement.setScore(movement.getScore() + 100*depth);
                    }
                }
                //System.out.println("\tFINAL DEPTH: " + depth + " - IA movement after go deeper: " + movement.toString());

                if (bestMovement == null || bestMovement.getScore() < movement.getScore()) {
                    bestMovement = movement;
                    p = iaPiece;
                }
            }
        }

        if (p != null) {
            p.setMovement(bestMovement);
        }

        return p;
    }

    private Piece getBestPlayerResponse(CheckersMap copyMap) {
        Piece bestPlayerPiece = null;
        Movement bestPlayerMovement = null;
        LinkedList<Piece> playerPieces = copyMap.getPlayerPieces();

        for (Piece piece : playerPieces) {
            piece.checkValidMoves(copyMap.getMap());
            for (Movement movement: piece.getValidMoves()) {
                movement.setScore(calculateMovementScore(movement, piece, copyMap));

                if (bestPlayerMovement == null || bestPlayerMovement.getScore() < movement.getScore()) {
                    bestPlayerMovement = movement;
                    bestPlayerPiece = piece;
                }
            }
        }

        if (bestPlayerMovement != null) {
            bestPlayerPiece.setMovement(bestPlayerMovement);
        }

        return bestPlayerPiece;
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
            // Si la pieza no esta segura -10 puntos
            score -= 10;
            if (piece.isKing()) {
                score -= 10;
            }
        } else {
            // Si la pieza esta segura +5 puntos
            score += 5;

            // 5 puntos extra dependiendo de la posicion en el tablero
            if (movement.getGoY() == 0 || movement.getGoY() == 7 || movement.getGoX() == 0 || movement.getGoX() == 7) {
                score += 5;
            } else if (movement.getGoY() == 1 || movement.getGoY() == 6) {
                score += 4;
            } else if (movement.getGoY() == 2 || movement.getGoY() == 5) {
                score += 3;
            } else if (movement.getGoY() == 3 || movement.getGoY() == 4) {
                score += 2;
            }

            // Puntuacion de coronar
            if ((movement.getGoX() == 7 && piece.getType() == 2) || (movement.getGoX() == 0 && piece.getType() == 1)) {
                if (!piece.isKing()) {
                    score += 20;
                }
            } else {
                // Recompensar avanzar hacia adelante, queremos coronar cuanto antes
                if (!piece.isKing() && piece.getType() == 2) {
                    score += movement.getGoX();
                } else if (!piece.isKing() && piece.getType() == 1) {
                    score += (7 - movement.getGoX());
                }
            }
        }

        // Recompensas por comer. Se contempla comer de forma segura y no segura
        // Si se come una dama coronada mas recompensa
        if (movement.isEatMovement()) {
            if (!eatable) {
                score += 50;
                if (movement.getEatedPiece().isKing()) {
                    score += 10;
                }
            } else {
                score += 10;
                if (movement.getEatedPiece().isKing()) {
                    score += 10;
                }
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
