package com.example.checkers;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class CheckersMap {
    private LinkedList<Piece> playerPieces = new LinkedList<Piece>();
    private LinkedList<Piece> iaPieces = new LinkedList<Piece>();
    private Piece[][] map = new Piece[8][8];
    private int scorePlayer = 0;
    private int scoreIA = 0;
    // 1 = negras, 2 = blancasIA

    private TextView logs = null;
    private String logMsg = "";

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public void setLogs(TextView logs) {
        this.logs = logs;
    }

    public CheckersMap() {
        setStartPosition();
    }

    public CheckersMap(CheckersMap m) {
        this.playerPieces = new LinkedList<Piece>();
        this.iaPieces = new LinkedList<Piece>();
        this.scorePlayer = m.scorePlayer;
        this.scoreIA = m.scoreIA;

        for (int  i = 0; i < 8; i++) {
            for(int j = 0; j < 8 ; j++) {
                if (m.map[i][j] != null) {
                    this.map[i][j] = new Piece(m.getMap()[i][j]);
                    if (this.map[i][j].getType() == 2) this.iaPieces.add(this.map[i][j]);
                    else playerPieces.add(this.map[i][j]);
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

    // Methods for the copy Map when minmax is called

    void eatPiece(Piece eaten, Piece eater) {
        for (int i = 0; i < map[0].length; i++)
            for (int j = 0; j < map[1].length; j++)
                if (map[i][j] != null) {
                    if (map[i][j].getId() == eaten.getId()) {
                        eaten = map[i][j];
                        map[i][j] = null;
                    }
                }

        if (eater.getType() == 1) {
            iaPieces.remove(eaten);
        } else {
            playerPieces.remove(eaten);
        }
    }

    boolean movePiece(Piece d) {
        int x = d.getX();
        int y = d.getY();

        if (MoveChecker.checkMovement(d.getMovement(), map)) {
            int nx = d.getMovement().getGoX();
            int ny = d.getMovement().getGoY();
            Piece movedPiece = map[x][y];

            map[x][y] = null;
            map[nx][ny] = movedPiece;

            if ( (d.getType() == 1 && nx == 0) || (d.getType() == 2 && nx == 7) ) {
                d.setKing(true);
            }

            movedPiece.setX(nx);
            movedPiece.setY(ny);

            if(d.getMovement().isEatMovement()) {
                eatPiece(d.getMovement().getEatedPiece(), d);
            }

            return true;
        } else {
            return false;
        }
    }

    // ---------------------

    void eatPiece(Piece eaten, Piece eater, TextView tvScoreIa, TextView tvScorePlayer) {
        for (int i = 0; i < map[0].length; i++)
            for (int j = 0; j < map[1].length; j++)
                if (map[i][j] != null) {
                    if (map[i][j].getId() == eaten.getId()) {
                        map[i][j] = null;
                    }
                }

        if (eater.getType() == 1) {
            //System.out.println("\tThe Player eated an IAPiece on ["+ eaten.getX() + "," + eaten.getY() + "]");
            logMsg += "\n  -> EAT: The Player eated an IAPiece on ["+ eaten.getX() + "," + eaten.getY() + "]\n";
            scorePlayer++;
            tvScorePlayer.setText("Player Score: " + scorePlayer);
            iaPieces.remove(eaten);
        } else {
            //System.out.println("\tThe IA eated a PlayerPiece on ["+ eaten.getX() + "," + eaten.getY() + "]");
            logMsg += "\n  -> EAT -> The IA eated a PlayerPiece on ["+ eaten.getX() + "," + eaten.getY() + "]\n";
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
            Piece movedPiece = map[x][y];

            String msg = "";

            if (d.getType() == 1) {
                msg = "\n  -> Map will move a playerPiece from [" + x + "," + y + "] to [" + nx + "," + ny + "]";
            }

            //System.out.println(msg);
            logMsg += msg;
            logs.setText(logMsg);
            map[x][y] = null;
            map[nx][ny] = movedPiece;

            if ( (d.getType() == 1 && nx == 0) || (d.getType() == 2 && nx == 7) ) {
                d.setKing(true);
            }

            movedPiece.setX(nx);
            movedPiece.setY(ny);

            if(d.getMovement().isEatMovement()) {
                images[d.getMovement().getEatedPiece().getX()][d.getMovement().getEatedPiece().getY()].setImageDrawable(null);
                eatPiece(d.getMovement().getEatedPiece(), d, tvScoreIa, tvScorePlayer);
            }

            return true;
        } else {
            //System.out.println("Wrong move. Try again!");
            return false;
        }
    }

    void setStartPosition() {
        int id = 0;
        for (int i = 0; i < map.length; i++) {
            if (i%2 == 0) {
                Piece d = new Piece(0, i);
                map[0][i] = d;
                d.setType(2);
                d.setId(id++);
                iaPieces.addLast(d);
            }

        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 != 0) {
                Piece d = new Piece(1, i);
                map[1][i] = d;
                d.setType(2);
                d.setId(id++);
                iaPieces.addLast(d);

            }
        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 == 0) {
                Piece d = new Piece(2, i);
                map[2][i] = d;
                d.setType(2);
                d.setId(id++);
                iaPieces.addLast(d);
            }
        }


        for (int i = 0; i < map.length; i++) {
            if (i%2 != 0) {
                Piece d = new Piece(5, i);
                map[5][i] = d;
                d.setType(1);
                d.setId(id++);
                playerPieces.addLast(d);

            }
        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 == 0) {
                Piece d = new Piece(6, i);
                map[6][i] = d;
                d.setType(1);
                d.setId(id++);
                playerPieces.addLast(d);
            }
        }

        for (int i = 0; i < map.length; i++) {
            if (i%2 != 0) {
                Piece d = new Piece(7, i);
                map[7][i] = d;
                d.setType(1);
                d.setId(id++);
                playerPieces.addLast(d);
            }
        }
    }

    public LinkedList<Piece> getPlayerPieces() {
        return playerPieces;
    }

    public void setPlayerPieces(LinkedList<Piece> playerPieces) {
        this.playerPieces = playerPieces;
    }

    public LinkedList<Piece> getIaPieces() {
        return iaPieces;
    }

    public void setIaPieces(LinkedList<Piece> iaPieces) {
        this.iaPieces = iaPieces;
    }

    public Piece[][] getMap() {
        return map;
    }

    public void setMap(Piece[][] map) {
        this.map = map;
    }

}