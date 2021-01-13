package com.example.checkers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CheckersIA {
    private CheckersMap checkersMap = null;
    private int level = 0;
    private int depth = 1;
    public static boolean IA = true;
    public static boolean PLAYER = false;

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
            case 3:
                piece = customIA(this.depth);
                break;
        }

        return piece;
    }

    private Piece easyIA() {
        LinkedList<Piece> validPieces = getValidPieces(checkersMap, true);

        for (Piece piece: validPieces) {
            if (piece.canEat()) {
                return piece;
            }
        }

        return selectRandomPiece(validPieces);
    }

    private Piece mediumIA(int depth) {
        long init = System.currentTimeMillis();
        Piece selected = minimax(depth, checkersMap);
        init = System.currentTimeMillis()-init;
        System.out.println("[IA] Finished minmax with depth: " + depth + ". Time: " + (init) + "ms");

        return selected;
    }

    private Piece hardIA(int depth) {
        long init = System.currentTimeMillis();
        Piece selected = minimax(depth, checkersMap);
        init = System.currentTimeMillis()-init;
        System.out.println("[IA] Finished minmax with depth: " + depth + ". Time: " + (init) + "ms");

        return selected;
    }

    private Piece customIA(int depth) {
        long init = System.currentTimeMillis();
        Piece selected = minimax(depth, checkersMap);
        init = System.currentTimeMillis()-init;
        System.out.println("[IA] Finished minmax with depth: " + depth + ". Time: " + (init) + "ms");

        return selected;
    }

    private  Piece minimax(int depth, CheckersMap checkersMap) {
        LinkedList<Piece> pieces;
        Piece bestPiece = null;

        if (IA) {
            pieces = checkersMap.getIaPieces();
        } else {
            pieces = checkersMap.getPlayerPieces();
        }

        for (Piece piece: pieces) {
            // Per each valid pieces we will test every movement
            int hasMoves = piece.checkValidMoves(checkersMap.getMap());

            for (Movement movement: piece.getValidMoves()) {
                if (hasMoves == 2 && !movement.isEatMovement()) continue;
                // Create copy object of the CheckersMap. Not need copy of pieces or movements
                CheckersMap testMap = new CheckersMap(checkersMap);

                piece.setMovement(movement);
                testMap.movePiece(piece);
                movement.setScore(calculateMovementScore(piece, testMap));

                // Now we look for the best player response, his best movement
                Piece bestResponse = getBestPlayerResponse(testMap);

                // If the player has a valid movement get the score and update our movement score
                if (bestResponse != null) {
                    movement.setScore(movement.getScore() - bestResponse.getMovement().getScore());
                    testMap.movePiece(bestResponse);

                    // Per each movement if depth > 1 we have to repeat the entire process for that map state
                    if (depth > 1) {
                        Piece depthPiece = minimax(depth-1, testMap);

                        // If on that sub-state we have some valid movement we add the score of it to our movement score
                        if (depthPiece != null) {
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
                        bestPiece = piece;
                        bestPiece.setMovement(movement);

                        return bestPiece;
                    }
                }

                if (bestPiece == null || bestPiece.getMovement().getScore() < movement.getScore()) {
                    bestPiece = piece;
                    bestPiece.setMovement(movement);
                }
            }
        }

        return bestPiece;
    }

    private Piece getBestPlayerResponse(CheckersMap copyMap) {
        LinkedList<Piece> playerPieces = copyMap.getPlayerPieces();
        Piece bestPlayerPiece = null;

        for (Piece piece : playerPieces) {
            int hasMoves = piece.checkValidMoves(copyMap.getMap());
            for (Movement movement: piece.getValidMoves()) {
                if (hasMoves == 2 && !movement.isEatMovement()) continue;
                CheckersMap tmpMap = new CheckersMap(copyMap);
                piece.setMovement(movement);

                tmpMap.movePiece(piece);
                movement.setScore(calculateMovementScore(piece, tmpMap));

                if (bestPlayerPiece == null || bestPlayerPiece.getMovement().getScore() < movement.getScore()) {
                    bestPlayerPiece = piece;
                    bestPlayerPiece.setMovement(movement);
                }
            }
        }

        return bestPlayerPiece;
    }

    /* Needs to be called after moved the piece */
    public int calculateMovementScore(Piece piece, CheckersMap map) {
        Movement movement = piece.getMovement();
        boolean eatable = isEatable(piece, map);
        int score = 0;

        if (eatable) {
            // Si la pieza no esta segura -10 puntos
            score -= 10;
            if (piece.isKing()) {
                score -= 20;
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
                score += 20;
                if (movement.getEatedPiece().isKing()) {
                    score += 20;
                }
            } else {
                score += 10;
                if (movement.getEatedPiece().isKing()) {
                    score += 20;
                }
            }
        }

        //System.out.println("[IA] Returned score: " + score);
        return score;
    }

    private boolean isEatable(Piece piece, CheckersMap map) {
        if(piece == null) return false;
        LinkedList<Piece> pieces;

        if (piece.getType() == 2) {
            pieces = map.getPlayerPieces();
        } else {
            pieces = map.getIaPieces();
        }

        for (Piece p: pieces) {
            if (piece.checkValidMoves(map.getMap()) != 2) continue;
            for (Movement m: p.getValidMoves()) {
                if (!m.isEatMovement()) continue;
                if (m.isEatMovement()) {
                    if (m.getEatedPiece().getId() == piece.getId()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public LinkedList<Piece> getValidPieces(CheckersMap map, Boolean IA) {
        LinkedList<Piece> validPieces = new LinkedList<Piece>();
        LinkedList<Piece> pieces;

        if (IA) {
            pieces = map.getIaPieces();

        } else {
            pieces =  map.getPlayerPieces();
        }

        for (Piece piece : pieces) {
            int hasMoves = piece.checkValidMoves(map.getMap());

            if (hasMoves > 0) {
                validPieces.add(piece);
            }
        }

        return validPieces;
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
