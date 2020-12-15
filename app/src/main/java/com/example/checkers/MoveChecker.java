package com.example.checkers;

import java.util.LinkedList;

public class MoveChecker {
    static boolean checkMovement(Movement movement, Piece[][] map) {
        int x = movement.getStartX();
        int y = movement.getStartY();
        int nx = movement.getGoX();
        int ny = movement.getGoY();

        int type = movement.getPiece().getType();
        boolean isKing = movement.getPiece().isKing();

        if (movement.isValid()) {
            return true;
        }

        if ( (nx < 0 || nx > 7 || ny < 0 || ny > 7 || nx == x || ny == y) ) {
            return false;
        }

        if (isKing) {
            int tmpX = Math.abs(x - nx);
            int tmpY = Math.abs(y - ny);

            if (tmpY != tmpX) {
                return false;
            }

            if (tmpX > 1) {
                if (nx > x && ny > y) {
                    int j = y+1;
                    for(int i = x+1; i < nx; i++) {
                        if (map[i][j] != null) {
                            return false;
                        }
                        j++;
                    }
                }

                if (nx > x && ny < y) {
                    int j = y-1;
                    for(int i = x+1; i < nx; i++) {
                        if (map[i][j] != null) {
                            return false;
                        }
                        j--;
                    }
                }

                if (nx < x && ny > y) {
                    int j = y+1;
                    for(int i = x-1; i > nx; i--) {
                        if (map[i][j] != null) {
                            return false;
                        }
                        j++;
                    }
                }

                if (nx < x && ny < y) {
                    int j = y-1;
                    for(int i = x-1; i > nx; i--) {
                        if (map[i][j] != null) {
                            return false;
                        }
                        j--;
                    }
                }
            }

            if (map[nx][ny] != null) {
                if (map[nx][ny].getType() == movement.getPiece().getType()) {
                    return false;
                } else {
                    boolean canEat = checkEat(movement, map);

                    if (!canEat) {
                        movement.setEatMovement(false);
                        movement.setValid(false);
                        return false;
                    }

                    movement.setValid(true);
                    return true;
                }
            } else {
                movement.setEatMovement(false);
                movement.setValid(true);
                return true;
            }

        } else {
            switch (type) {
                case 1:
                    if ( (nx == x-1 && type == 1) ) {
                        if (ny == y-1 || ny == y+1) {
                            if (map[nx][ny] != null) {
                                if (map[nx][ny].getType() == 1)
                                    return false;
                                boolean canEat = checkEat(movement, map);

                                if (!canEat) {
                                    movement.setEatMovement(false);
                                    movement.setValid(false);
                                    return false;
                                }

                            }
                            movement.setValid(true);
                            return true;
                        }
                    }
                    break;
                case 2:
                    if (nx == x+1) {
                        if (ny == y-1 || ny == y+1) {
                            if (map[nx][ny] != null) {
                                if (map[nx][ny].getType() == 2)
                                    return false;
                                boolean canEat = checkEat(movement, map);

                                if (!canEat) {
                                    movement.setEatMovement(false);
                                    movement.setValid(false);
                                    return false;
                                }

                            }
                            movement.setValid(true);
                            return true;
                        }
                    }
                    break;

                default:
                    break;
            }

        }

        return false;
    }

    static LinkedList<Movement> getMovements(Piece d, Piece[][] map) {
        int startX = d.getX();
        int startY = d.getY();

        LinkedList<Movement> list = new LinkedList<Movement>();
        Piece p = new Piece(startX, startY);
        p.setType(d.getType());
        p.setKing(d.isKing());

        int nextX;
        int nextY;
        int max;

        if (p.isKing()) {
            max = 4;

            for (int i = 0; i < max; i++) {
                if (i == 0) {
                    boolean valid = false;
                    nextX = startX;
                    nextY = startY;

                    do {
                        nextX--;
                        nextY--;

                        p.move(nextX, nextY);

                        Movement m = new Movement(p.getMovement());
                        m.setPiece(p);

                        valid = MoveChecker.checkMovement(m, map);

                        if (valid) {
                            if (m.isEatMovement()) {
                                m.setScore(m.getScore()+5);
                            }
                            list.add(m);
                        }
                    } while (valid);

                } else if (i == 1) {
                    boolean valid = false;
                    nextX = startX;
                    nextY = startY;

                    do {
                        nextX--;
                        nextY++;

                        p.move(nextX, nextY);

                        Movement m = new Movement(p.getMovement());

                        m.setPiece(p);

                        valid = MoveChecker.checkMovement(m, map);

                        if (valid) {
                            if (m.isEatMovement()) {
                                m.setScore(m.getScore()+5);
                            }
                            list.add(m);
                        }
                    } while (valid);

                } else if (i == 2){
                    boolean valid = false;
                    nextX = startX;
                    nextY = startY;

                    do {
                        nextX++;
                        nextY--;

                        p.move(nextX, nextY);

                        Movement m = new Movement(p.getMovement());
                        m.setPiece(p);

                        valid = MoveChecker.checkMovement(m, map);

                        if (valid) {
                            if (m.isEatMovement()) {
                                m.setScore(m.getScore()+5);
                            }
                            list.add(m);
                        }
                    } while (valid);

                } else {
                    boolean valid = false;
                    nextX = startX;
                    nextY = startY;

                    do {
                        nextX++;
                        nextY++;

                        p.move(nextX, nextY);

                        Movement m = new Movement(p.getMovement());
                        m.setPiece(p);

                        valid = MoveChecker.checkMovement(m, map);

                        if (valid) {
                            if (m.isEatMovement()) {
                                m.setScore(m.getScore()+5);
                            }
                            list.add(m);
                        }
                    } while (valid);
                }
            }

        } else {
            max = 2;

            switch (p.getType()) {
                case 1:
                    nextX = startX-1;
                    for (int i = 0; i < max; i++) {
                        if (i == 0) {
                            nextY = startY-1;
                            p.move(nextX, nextY);
                            Movement m = new Movement(p.getMovement());
                            m.setPiece(p);

                            boolean valid = MoveChecker.checkMovement(m, map);
                            if (valid) {
                                if (m.isEatMovement()) {
                                    m.setScore(m.getScore()+5);
                                }
                                list.add(m);
                            }
                        } else {
                            nextY = startY+1;
                            p.move(nextX, nextY);
                            Movement m = new Movement(p.getMovement());
                            m.setPiece(p);

                            boolean valid = MoveChecker.checkMovement(m, map);
                            if (valid) {
                                if (m.isEatMovement()) {
                                    m.setScore(m.getScore()+5);
                                }
                                list.add(m);
                            }
                        }
                    }

                    break;

                case 2:
                    nextX = startX+1;
                    for (int i = 0; i < max; i++) {
                        if (i == 0) {
                            nextY = startY-1;
                            p.move(nextX, nextY);
                            Movement m = new Movement(p.getMovement());
                            m.setPiece(p);

                            boolean valid = MoveChecker.checkMovement(m, map);
                            if (valid) {
                                if (m.isEatMovement()) {
                                    m.setScore(m.getScore()+5);
                                }
                                list.add(m);
                            }
                        } else {
                            nextY = startY+1;
                            p.move(nextX, nextY);
                            Movement m = new Movement(p.getMovement());
                            m.setPiece(p);

                            boolean valid = MoveChecker.checkMovement(m, map);
                            if (valid) {
                                if (m.isEatMovement()) {
                                    m.setScore(m.getScore()+5);
                                }
                                list.add(m);
                            }
                        }
                    }

                    break;

                default:
                    break;
            }
        }
        /*
        if (list.size() > 0) {
            System.out.println("Checked valid moves. Nº of valid movements: " + list.size());
            for(Movement movenent : list) {
                if (movenent.getPiece().isKing()) {
                    System.out.print("\tKingMovement!");
                }
                if (movenent.isEatMovement()) {
                    System.out.println("\tStart [" + movenent.getStartX() + "," + movenent.getStartY() +"] - Destiny [" + movenent.getGoX() + "," + movenent.getGoY() + "] "+ "- Eat Movement!");
                } else {
                    System.out.println("\tStart [" + movenent.getStartX() + "," + movenent.getStartY() +"] - Destiny [" + movenent.getGoX() + "," + movenent.getGoY() + "] ");
                }
            }
        }
        */

        return list;
    }

    private static boolean checkEat(Movement movement, Piece[][] map) {
        int nx = movement.getGoX();
        int ny = movement.getGoY();
        int x = movement.getStartX();
        int y = movement.getStartY();

        int type = movement.getPiece().getType();
        boolean king = movement.getPiece().isKing();

        if ( (nx == 0 || nx == 7) || (ny == 0 || ny == 7) ) {
            return false;
        }

        if (king) {
            if (nx > x) {
                if (ny > y) {
                    if (map[nx+1][ny+1] != null) {
                        return false;
                    }
                    movement.setGoX(nx+1);
                    movement.setGoY(ny+1);
                } else if (ny < y) {
                    if (map[nx+1][ny-1] != null) {
                        return false;
                    }
                    movement.setGoX(nx+1);
                    movement.setGoY(ny-1);
                } else {
                    return false;
                }
            } else {
                if (ny > y) {
                    if (map[nx-1][ny+1] != null) {
                        return false;
                    }
                    movement.setGoX(nx-1);
                    movement.setGoY(ny+1);
                } else if (ny < y) {
                    if (map[nx-1][ny-1] != null) {
                        return false;
                    }
                    movement.setGoX(nx-1);
                    movement.setGoY(ny-1);
                } else {
                    return false;
                }
            }
        } else {
            if (type == 1) {
                if (ny > y) {
                    if (map[nx-1][ny+1] != null) {
                        return false;
                    }
                    movement.setGoX(nx-1);
                    movement.setGoY(ny+1);
                } else if (ny < y) {
                    if (map[nx-1][ny-1] != null) {
                        return false;
                    }
                    movement.setGoX(nx-1);
                    movement.setGoY(ny-1);
                } else {
                    return false;
                }
            } else if (type == 2) {
                if (ny > y) {
                    if (map[nx+1][ny+1] != null) {
                        return false;
                    }
                    movement.setGoX(nx+1);
                    movement.setGoY(ny+1);
                } else if (ny < y) {
                    if (map[nx+1][ny-1] != null) {
                        return false;
                    }
                    movement.setGoX(nx+1);
                    movement.setGoY(ny-1);
                } else {
                    return false;
                }
            }

        }
        movement.setEatedPiece(map[nx][ny]);
        //map[nx][ny].setEated(true);
        movement.setEatMovement(true);
        return true;
    }


}

