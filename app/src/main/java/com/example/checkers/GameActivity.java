package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class GameActivity extends AppCompatActivity {
    private ImageView cell00, cell01, cell02, cell03, cell04, cell05, cell06, cell07;
    private ImageView cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17;
    private ImageView cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27;
    private ImageView cell30, cell31, cell32, cell33, cell34, cell35, cell36, cell37;
    private ImageView cell40, cell41, cell42, cell43, cell44, cell45, cell46, cell47;
    private ImageView cell50, cell51, cell52, cell53, cell54, cell55, cell56, cell57;
    private ImageView cell60, cell61, cell62, cell63, cell64, cell65, cell66, cell67;
    private ImageView cell70, cell71, cell72, cell73, cell74, cell75, cell76, cell77;

    private ImageView[][] imageViews = new ImageView[8][8];

    private CheckersMap checkersMap = new CheckersMap();
    private Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        init();
        setClickListeners();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //Map m = new Map();
        checkersMap.showMap();
        boolean fin = false;
        System.out.println("\n**********************************");
        System.out.println("         Starting match!");
        System.out.println("*********************************\n");
        //m.showMap();
        System.out.println("\n       You are Player 1 (blacks)");
        System.out.print("\n AQUI => " + checkersMap.getWhites());
        String s = "";
    }



    private void moveIA() {
        boolean moved = false;
        LinkedList<Piece> movePieces = new LinkedList<Piece>();
        LinkedList<Piece> iaPieces = checkersMap.getWhites();

        Movement newMovement = null;
        Movement este = null;
        Piece p = null;
        for (Piece piece : iaPieces) {
            boolean hasMoves = piece.checkValidMoves(checkersMap.getMap());

            if (hasMoves) {
                movePieces.add(piece);

                for (Movement movement : piece.getValidMoves()) {
                    if (movement.isEatMovement()) {
                        newMovement = movement;
                        p = piece;
                        p.setMovement(newMovement);
                    }
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
                este = p.getValidMoves().get(random);
                imageViews[p.getX()][p.getY()].setImageDrawable(null);
                imageViews[este.getGoX()][este.getGoY()].setImageDrawable(getResources().getDrawable(R.drawable.whites));
                p.setMovement(este);
            }
            checkersMap.movePiece(p);
        }
    }

  
    private void setClickListeners() {
        cell00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,0));
                    if (player.getSelectedPiece() != null) {
                        cell00.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();
                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,1));
                    if (player.getSelectedPiece() != null) {
                        cell01.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();
                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,2));
                    if (player.getSelectedPiece() != null) {
                        cell02.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,3));
                    if (player.getSelectedPiece() != null) {
                        cell03.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,4));
                    if (player.getSelectedPiece() != null) {
                        cell04.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,5));
                    if (player.getSelectedPiece() != null) {
                        cell05.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,6));
                    if (player.getSelectedPiece() != null) {
                        cell06.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);

                }
            }
        });

        cell07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(0,7));
                    if (player.getSelectedPiece() != null) {
                        cell07.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(0,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,0));
                    if (player.getSelectedPiece() != null) {
                        cell10.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,1));
                    if (player.getSelectedPiece() != null) {
                        cell11.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,2));
                    if (player.getSelectedPiece() != null) {
                        cell12.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,3));
                    if (player.getSelectedPiece() != null) {
                        cell13.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,4));
                    if (player.getSelectedPiece() != null) {
                        cell14.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,5));
                    if (player.getSelectedPiece() != null) {
                        cell15.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,6));
                    if (player.getSelectedPiece() != null) {
                        cell16.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(1,7));
                    if (player.getSelectedPiece() != null) {
                        cell17.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(1,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,0));
                    if (player.getSelectedPiece() != null) {
                        cell20.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,1));
                    if (player.getSelectedPiece() != null) {
                        cell21.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,2));
                    if (player.getSelectedPiece() != null) {
                        cell22.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }

                    player.setSelectedPiece(null);
                }
            }
        });

        cell23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,3));
                    if (player.getSelectedPiece() != null) {
                        cell23.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,4));
                    if (player.getSelectedPiece() != null) {
                        cell24.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,5));
                    if (player.getSelectedPiece() != null) {
                        cell25.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,6));
                    if (player.getSelectedPiece() != null) {
                        cell26.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(2,7));
                    if (player.getSelectedPiece() != null) {
                        cell27.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(2,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,0));
                    if (player.getSelectedPiece() != null) {
                        cell30.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,1));
                    if (player.getSelectedPiece() != null) {
                        cell31.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,2));
                    if (player.getSelectedPiece() != null) {
                        cell32.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,3));
                    if (player.getSelectedPiece() != null) {
                        cell33.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,4));
                    if (player.getSelectedPiece() != null) {
                        cell34.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,5));
                    if (player.getSelectedPiece() != null) {
                        cell35.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,6));
                    if (player.getSelectedPiece() != null) {
                        cell36.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(3,7));
                    if (player.getSelectedPiece() != null) {
                        cell37.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(3,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,0));
                    if (player.getSelectedPiece() != null) {
                        cell40.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,1));
                    if (player.getSelectedPiece() != null) {
                        cell41.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,2));
                    if (player.getSelectedPiece() != null) {
                        cell42.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,3));
                    if (player.getSelectedPiece() != null) {
                        cell43.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,4));
                    if (player.getSelectedPiece() != null) {
                        cell44.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,5));
                    if (player.getSelectedPiece() != null) {
                        cell45.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,6));
                    if (player.getSelectedPiece() != null) {
                        cell46.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(4,7));
                    if (player.getSelectedPiece() != null) {
                        cell47.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(4,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,0));
                    if (player.getSelectedPiece() != null) {
                        cell50.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,1));
                    if (player.getSelectedPiece() != null) {
                        cell51.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,2));
                    if (player.getSelectedPiece() != null) {
                        cell52.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,3));
                    if (player.getSelectedPiece() != null) {
                        cell53.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,4));
                    if (player.getSelectedPiece() != null) {
                        cell54.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,5));
                    if (player.getSelectedPiece() != null) {
                        cell55.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,6));
                    if (player.getSelectedPiece() != null) {
                        cell56.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(5,7));
                    if (player.getSelectedPiece() != null) {
                        cell57.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(5,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,0));
                    if (player.getSelectedPiece() != null) {
                        cell60.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,1));
                    if (player.getSelectedPiece() != null) {
                        cell61.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,2));
                    if (player.getSelectedPiece() != null) {
                        cell62.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,3));
                    if (player.getSelectedPiece() != null) {
                        cell63.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,4));
                    if (player.getSelectedPiece() != null) {
                        cell64.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,5));
                    if (player.getSelectedPiece() != null) {
                        cell65.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,6));
                    if (player.getSelectedPiece() != null) {
                        cell66.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(6,7));
                    if (player.getSelectedPiece() != null) {
                        cell67.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(6,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,0));
                    if (player.getSelectedPiece() != null) {
                        cell70.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,0);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,1));
                    if (player.getSelectedPiece() != null) {
                        cell71.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,1);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,2));
                    if (player.getSelectedPiece() != null) {
                        cell72.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,2);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,3));
                    if (player.getSelectedPiece() != null) {
                        cell73.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,3);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell74.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,4));
                    if (player.getSelectedPiece() != null) {
                        cell74.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,4);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,5));
                    if (player.getSelectedPiece() != null) {
                        cell75.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,5);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell76.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,6));
                    if (player.getSelectedPiece() != null) {
                        cell76.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,6);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });

        cell77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED! Piece: " + player.getSelectedPiece());
                if (player.getSelectedPiece() == null) {
                    player.setSelectedPiece(checkersMap.getBlackPiece(7,7));
                    if (player.getSelectedPiece() != null) {
                        cell77.setBackgroundColor(getColor(R.color.green));
                    }
                } else {
                    Piece selected = player.getSelectedPiece();
                    int x = selected.getX();
                    int y = selected.getY();
                    imageViews[x][y].setBackgroundColor(getColor(R.color.black));
                    selected.move(7,7);
                    boolean moved = checkersMap.movePiece(selected);
                    if (moved) {
                        imageViews[x][y].setImageDrawable(null);
                        x = selected.getX();
                        y = selected.getY();
                        imageViews[x][y].setImageDrawable(getResources().getDrawable(R.drawable.black));
                        moveIA();

                    }
                    player.setSelectedPiece(null);
                }
            }
        });
    }

    private void init() {
        cell00 = findViewById(R.id.square_00);
        imageViews[0][0] = cell00;
        cell01 = findViewById(R.id.square_01);
        imageViews[0][1] = cell01;
        cell02 = findViewById(R.id.square_02);
        imageViews[0][2] = cell02;
        cell03 = findViewById(R.id.square_03);
        imageViews[0][3] = cell03;
        cell04 = findViewById(R.id.square_04);
        imageViews[0][4] = cell04;
        cell05 = findViewById(R.id.square_05);
        imageViews[0][5] = cell05;
        cell06 = findViewById(R.id.square_06);
        imageViews[0][6] = cell06;
        cell07 = findViewById(R.id.square_07);
        imageViews[0][7] = cell07;
        cell10 = findViewById(R.id.square_10);
        imageViews[1][0] = cell10;
        cell11 = findViewById(R.id.square_11);
        imageViews[1][1] = cell11;
        cell12 = findViewById(R.id.square_12);
        imageViews[1][2] = cell12;
        cell13 = findViewById(R.id.square_13);
        imageViews[1][3] = cell13;
        cell14 = findViewById(R.id.square_14);
        imageViews[1][4] = cell14;
        cell15 = findViewById(R.id.square_15);
        imageViews[1][5] = cell15;
        cell16 = findViewById(R.id.square_16);
        imageViews[1][6] = cell16;
        cell17 = findViewById(R.id.square_17);
        imageViews[1][7] = cell17;
        cell20 = findViewById(R.id.square_20);
        imageViews[2][0] = cell20;
        cell21 = findViewById(R.id.square_21);
        imageViews[2][1] = cell21;
        cell22 = findViewById(R.id.square_22);
        imageViews[2][2] = cell22;
        cell23 = findViewById(R.id.square_23);
        imageViews[2][3] = cell23;
        cell24 = findViewById(R.id.square_24);
        imageViews[2][4] = cell24;
        cell25 = findViewById(R.id.square_25);
        imageViews[2][5] = cell25;
        cell26 = findViewById(R.id.square_26);
        imageViews[2][6] = cell26;
        cell27 = findViewById(R.id.square_27);
        imageViews[2][7] = cell27;
        cell30 = findViewById(R.id.square_30);
        imageViews[3][0] = cell30;
        cell31 = findViewById(R.id.square_31);
        imageViews[3][1] = cell31;
        cell32 = findViewById(R.id.square_32);
        imageViews[3][2] = cell32;
        cell33 = findViewById(R.id.square_33);
        imageViews[3][3] = cell33;
        cell34 = findViewById(R.id.square_34);
        imageViews[3][4] = cell34;
        cell35 = findViewById(R.id.square_35);
        imageViews[3][5] = cell35;
        cell36 = findViewById(R.id.square_36);
        imageViews[3][6] = cell36;
        cell37 = findViewById(R.id.square_37);
        imageViews[3][7] = cell37;
        cell40 = findViewById(R.id.square_40);
        imageViews[4][0] = cell40;
        cell41 = findViewById(R.id.square_41);
        imageViews[4][1] = cell41;
        cell42 = findViewById(R.id.square_42);
        imageViews[4][2] = cell42;
        cell43 = findViewById(R.id.square_43);
        imageViews[4][3] = cell43;
        cell44 = findViewById(R.id.square_44);
        imageViews[4][4] = cell44;
        cell45 = findViewById(R.id.square_45);
        imageViews[4][5] = cell45;
        cell46 = findViewById(R.id.square_46);
        imageViews[4][6] = cell46;
        cell47 = findViewById(R.id.square_47);
        imageViews[4][7] = cell47;
        cell50 = findViewById(R.id.square_50);
        imageViews[5][0] = cell50;
        cell51 = findViewById(R.id.square_51);
        imageViews[5][1] = cell51;
        cell52 = findViewById(R.id.square_52);
        imageViews[5][2] = cell52;
        cell53 = findViewById(R.id.square_53);
        imageViews[5][3] = cell53;
        cell54 = findViewById(R.id.square_54);
        imageViews[5][4] = cell54;
        cell55 = findViewById(R.id.square_55);
        imageViews[5][5] = cell55;
        cell56 = findViewById(R.id.square_56);
        imageViews[5][6] = cell56;
        cell57 = findViewById(R.id.square_57);
        imageViews[5][7] = cell57;
        cell60 = findViewById(R.id.square_60);
        imageViews[6][0] = cell60;
        cell61 = findViewById(R.id.square_61);
        imageViews[6][1] = cell61;
        cell62 = findViewById(R.id.square_62);
        imageViews[6][2] = cell62;
        cell63 = findViewById(R.id.square_63);
        imageViews[6][3] = cell63;
        cell64 = findViewById(R.id.square_64);
        imageViews[6][4] = cell64;
        cell65 = findViewById(R.id.square_65);
        imageViews[6][5] = cell65;
        cell66 = findViewById(R.id.square_66);
        imageViews[6][6] = cell66;
        cell67 = findViewById(R.id.square_67);
        imageViews[6][7] = cell67;
        cell70 = findViewById(R.id.square_70);
        imageViews[7][0] = cell70;
        cell71 = findViewById(R.id.square_71);
        imageViews[7][1] = cell71;
        cell72 = findViewById(R.id.square_72);
        imageViews[7][2] = cell72;
        cell73 = findViewById(R.id.square_73);
        imageViews[7][3] = cell73;
        cell74 = findViewById(R.id.square_74);
        imageViews[7][4] = cell74;
        cell75 = findViewById(R.id.square_75);
        imageViews[7][5] = cell75;
        cell76 = findViewById(R.id.square_76);
        imageViews[7][6] = cell76;
        cell77 = findViewById(R.id.square_77);
        imageViews[7][7] = cell77;
        moveIA();
    }


}