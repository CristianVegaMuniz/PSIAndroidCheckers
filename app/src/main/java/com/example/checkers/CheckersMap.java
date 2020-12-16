package com.example.checkers;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class CheckersMap {
    private LinkedList<Piece> playerPieces = new LinkedList<Piece>();
    private LinkedList<Piece> iaPieces = new LinkedList<Piece>();
    private int scorePlayer = 0;
    private int scoreIA = 0;
    private Piece[][] map = new Piece[8][8];

    private TextView logs = null;
    private String logMsg = "";

    public void setLogs(TextView logs) {
        this.logs = logs;
    }

    public CheckersMap() {
        setStartPosition();
    }

    public CheckersMap(CheckersMap checkersMap) {
        this.playerPieces = new LinkedList<Piece>(checkersMap.getPlayerPieces());
        this.iaPieces = new LinkedList<Piece>(checkersMap.getIaPieces());
        this.scorePlayer = checkersMap.getScorePlayer();
        this.scoreIA = checkersMap.getScoreIA();

        for (int  i = 0; i < 8; i++) {
            for(int j = 0; j < 8 ; j++) {
                if (checkersMap.getMap()[i][j] != null) {
                    this.map[i][j] = new Piece(checkersMap.getMap()[i][j]);
                }
            }
        }
    }

    void showMap() {
        String msg = "";
        for (int i = 0; i < map.length; i++) {
            msg += i + "->";
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] != null) {
                    msg += "|"+map[i][j].getType();
                } else {
                    msg += "| ";
                }
            }
            msg += "|\n";
        }

        msg += "    0 1 2 3 4 5 6 7\n";
        msg += "Player Score: " + scorePlayer + "\n";
        msg += "IA Score: " + scoreIA + "\n";

        System.out.println(msg);
    }

    Piece getPlayerPiece(int x, int y) {
        Piece d = map[x][y];

        if (d != null) {
            if (d.getType() == 2) {
                return null;
            }
        }
        return d;
    }

    Piece getIaPiece(int x, int y) {
        Piece d = map[x][y];
        if (d != null) {
            if (d.getType() == 1) {
                return null;
            }
        }
        return d;
    }

    public int getScorePlayer() {
        return scorePlayer;
    }

    public void setScorePlayer(int scorePlayer) {
        this.scorePlayer = scorePlayer;
    }

    public int getScoreIA() {
        return scoreIA;
    }

    public void setScoreIA(int scoreIA) {
        this.scoreIA = scoreIA;
    }

    void eatPiece(Piece eaten, Piece eater, TextView tvScoreIa, TextView tvScorePlayer) {
        for (int i = 0; i < map[0].length; i++)
            for (int j= 0; j < map[1].length; j++)
                if(map[i][j] == eaten) {
                    map[i][j] = null;
                }

        if (eater.getType() == 1) {
            System.out.println("\tThe Player eated an IAPiece on ["+ eaten.getX() + "," + eaten.getY() + "]");
            logMsg += "\n\n EAT -> The Player eated an IAPiece on ["+ eaten.getX() + "," + eaten.getY() + "]\n";
            scorePlayer++;
            tvScorePlayer.setText("Player Score: " + scorePlayer);
            iaPieces.remove(eaten);
        } else {
            System.out.println("\tThe IA eated a PlayerPiece on ["+ eaten.getX() + "," + eaten.getY() + "]");
            logMsg += "\n\n EAT -> The IA eated a PlayerPiece on ["+ eaten.getX() + "," + eaten.getY() + "]\n";
            scoreIA++;
            tvScoreIa.setText("IA Score: " + scoreIA);
            playerPieces.remove(eaten);
        }
        logs.setText(logMsg);
    }

    boolean movePiece(Piece d, ImageView[][] images, TextView tvScoreIa, TextView tvScorePlayer) {
        int x = d.getX();
        int y = d.getY();

        if (MoveChecker.checkMovement(d.getMovement(), map)) {
            int nx = d.getMovement().getGoX();
            int ny = d.getMovement().getGoY();

            String msg = "";

            if (d.getType() == 1) {
                msg = "\n  -> Map will move a playerPiece from [" + x + "," + y + "] to [" + nx + "," + ny + "]";
            } else {
                msg = "\n  -> Map will move a iaPiece from [" + x + "," + y + "] to [" + nx + "," + ny + "]";
            }

            System.out.println(msg);
            logMsg += msg;
            logs.setText(logMsg);
            map[x][y] = null;
            map[nx][ny] = d;

            if ( (d.getType() == 1 && nx == 0) || (d.getType() == 2 && nx == 7) ) {
                d.setKing(true);
            }

            d.setX(nx);
            d.setY(ny);

            if(d.getMovement().isEatMovement()) {
                images[d.getMovement().getEatedPiece().getX()][d.getMovement().getEatedPiece().getY()].setImageDrawable(null);
                eatPiece(d.getMovement().getEatedPiece(), d, tvScoreIa, tvScorePlayer);
            }
            try {
                wait(1000000000);
            }catch (Exception e) {

            }
            return true;
        } else {
            System.out.println("Wrong move. Try again!");
            return false;
        }

    }

    void setStartPosition() {
        for (int i = 0; i < map.length; i++) {
            if (i%2 == 0) {
                Piece d = new Piece(0, i);
                map[0][i] = d;
                d.setType(2);
                iaPieces.addLast(d);
            }

        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 != 0) {
                Piece d = new Piece(1, i);
                map[1][i] = d;
                d.setType(2);
                iaPieces.addLast(d);

            }
        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 == 0) {
                Piece d = new Piece(2, i);
                map[2][i] = d;
                d.setType(2);
                iaPieces.addLast(d);


            }
        }


        for (int i = 0; i < map.length; i++) {
            if (i%2 != 0) {
                Piece d = new Piece(5, i);
                map[5][i] = d;
                d.setType(1);
                playerPieces.addLast(d);

            }
        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 == 0) {
                Piece d = new Piece(6, i);
                map[6][i] = d;
                d.setType(1);
                playerPieces.addLast(d);
            }
        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 != 0) {
                Piece d = new Piece(7, i);
                map[7][i] = d;
                d.setType(1);
                playerPieces.addLast(d);
            }
        }
    }

    public LinkedList<Piece> getPlayerPieces() {
        return playerPieces;
    }

    public void setPlayerPieces(LinkedList<Piece> blacks) {
        this.playerPieces = blacks;
    }

    public LinkedList<Piece> getIaPieces() {
        return iaPieces;
    }

    public void setIaPieces(LinkedList<Piece> whites) {
        this.iaPieces = whites;
    }

    public Piece[][] getMap() {
        return map;
    }

    public void setMap(Piece[][] map) {
        this.map = map;
    }

}
