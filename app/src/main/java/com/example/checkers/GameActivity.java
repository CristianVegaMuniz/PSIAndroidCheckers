package com.example.checkers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedList;

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

    private ImageView iaThinking;
    private TextView scorePlayer, scoreIa, logs;

    private Drawable blackPiece;
    private Drawable blackKing;
    private Drawable whitePiece;
    private Drawable whiteKing;

    private int green;
    private int background_blacks;
    private int blue;

    private final CheckersMap checkersMap = new CheckersMap();
    private final Player player = new Player();
    private CheckersIA ia;

    private int level = 0; // Easy = 0; Normal = 1; Hard = 2
    private int depth = 2; // minimax depth

    private boolean playerWhites = false;
    private boolean waitingForIA = false;
    private ScrollView logScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        level = getIntent().getIntExtra("LEVEL", 0);
        playerWhites = getIntent().getBooleanExtra("WHITES", false);

        if (playerWhites) {
            setContentView(R.layout.activity_game_whites);
        } else {
            setContentView(R.layout.activity_game);
        }

        blackKing = getResources().getDrawable(R.drawable.black_king);
        blackPiece = getResources().getDrawable(R.drawable.black);
        whitePiece = getResources().getDrawable(R.drawable.white);
        whiteKing = getResources().getDrawable(R.drawable.white_king);

        init();
        setClickListeners();

        if (level == 2) depth = 4;
        if (level == 3) depth = getIntent().getIntExtra("DEPTH", 1);

        System.out.println("[IA] The level selected is: " + level + " with depth: " + depth);
        TextView tvLevel = (TextView) findViewById(R.id.tvLevel);
        if (level > 0) {
            tvLevel.setText("Level: " + level + "\nDepth: " + depth);
        } else {
            tvLevel.setText("Level: " + level);
        }
        ia = new CheckersIA(checkersMap, level, depth);

        if (!playerWhites) {
            callIA();
        }
    }

    public void handlerIA(Piece iaPiece) {
        if (iaPiece != null) {
            System.out.println("[IA] Selected the movement: " + iaPiece.getMovement().toString());
            String logMsg = checkersMap.getLogMsg() + "\n  -> IA selected the movement: " + iaPiece.getMovement().toString();
            checkersMap.setLogMsg(logMsg);
            logs.setText(logMsg);

            deletePiece(iaPiece.getX(), iaPiece.getY());
            checkersMap.movePiece(iaPiece, imageViews, scoreIa, scorePlayer);
            drawPiece(iaPiece);
        }

        checkEndGame(true);
        autoScroll();
        iaThinking.setVisibility(View.INVISIBLE);
        waitingForIA = false;
    }

    private void callIA() {
        waitingForIA = true;
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Piece iaPiece = ia.moveIA();
                        handlerIA(iaPiece);
                    }
                });
            }
        };
        thread.start();
        iaThinking.setVisibility(View.VISIBLE);
    }

    private void autoScroll() {
        logScroll.post(new Runnable() {
            @Override
            public void run() {
                logScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void drawPiece(Piece p) {
        if (p.getType() == 1) {
            if (playerWhites) {
                if (p.isKing()) {
                    imageViews[p.getX()][p.getY()].setImageDrawable(whiteKing);
                } else {
                    imageViews[p.getX()][p.getY()].setImageDrawable(whitePiece);
                }
            } else {
                if (p.isKing()) {
                    imageViews[p.getX()][p.getY()].setImageDrawable(blackKing);
                } else {
                    imageViews[p.getX()][p.getY()].setImageDrawable(blackPiece);
                }
            }

        } else {
            if (playerWhites) {
                if (p.isKing()) {
                    imageViews[p.getX()][p.getY()].setImageDrawable(blackKing);
                } else {
                    imageViews[p.getX()][p.getY()].setImageDrawable(blackPiece);
                }
            } else {
                if (p.isKing()) {
                    imageViews[p.getX()][p.getY()].setImageDrawable(whiteKing);
                } else {
                    imageViews[p.getX()][p.getY()].setImageDrawable(whitePiece);
                }
            }

        }
    }

    private void deletePiece(int x, int y) {
        imageViews[x][y].setImageDrawable(null);
    }

    private void drawValidMoves(Piece piece) {
        int hasMoves = piece.checkValidMoves(checkersMap.getMap());
        if (hasMoves > 0) {
            for (Movement move : piece.getValidMoves()) {
                if (move.isEatMovement()) {
                    imageViews[move.getEatedPiece().getX()][move.getEatedPiece().getY()].setBackgroundColor(blue);
                } else {
                    imageViews[move.getGoX()][move.getGoY()].setBackgroundColor(blue);
                }
            }
        }
    }

    private void removeValidMoves(Piece piece) {
        if (piece.getValidMoves() != null) {
            for (Movement move : piece.getValidMoves()) {
                if (move.isEatMovement()) {
                    imageViews[move.getEatedPiece().getX()][move.getEatedPiece().getY()].setBackgroundColor(background_blacks);
                } else {
                    imageViews[move.getGoX()][move.getGoY()].setBackgroundColor(background_blacks);
                }
            }
        }
    }

    private void checkEndGame(boolean IA) {
        boolean finish = false;
        String msg = "";

        if (IA) {
            LinkedList<Piece> plValidPîeces = ia.getValidPieces(checkersMap, false);
            if (checkersMap.getPlayerPieces().isEmpty() || plValidPîeces.isEmpty()) {
                msg = "IA Wins!\nDo you want to play again?";
                finish = true;
            }
        } else {
            LinkedList<Piece> iaValidPîeces = ia.getValidPieces(checkersMap, true);
            if (checkersMap.getIaPieces().isEmpty() || iaValidPîeces.isEmpty()) {
                msg = "You Win!\nDo you want to play again?";
                finish = true;
            }
        }

        if (finish) {
            Context context = this;
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage(msg);
            builder1.setCancelable(true);

            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    System.exit(0);
                }
            });

            builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finishAffinity();
                    finish();
                    System.exit(0);
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private void playerClick(ImageView cell, int selX, int selY) {
        if (waitingForIA) return;
        if (player.getSelectedPiece() == null) {
            player.setSelectedPiece(checkersMap.getPlayerPiece(selX,selY));
            if (player.getSelectedPiece() != null) {
                cell.setBackgroundColor(green);
                drawValidMoves(player.getSelectedPiece());
            }

        } else {
            Piece selected = player.getSelectedPiece();
            int x = selected.getX();
            int y = selected.getY();

            imageViews[x][y].setBackgroundColor(background_blacks);
            selected.move(selX,selY);

            boolean moved = checkersMap.movePiece(selected, imageViews, scoreIa, scorePlayer);
            removeValidMoves(selected);

            if (moved) {
                selected.getMovement().setScore(ia.calculateMovementScore(selected, checkersMap));
                String msg = checkersMap.getLogMsg() + "\n  -> PL selected the movement: " + selected.getMovement().getStartX() + "-"
                        + selected.getMovement().getStartY() + " To: " + selected.getMovement().getGoX() + "-" + selected.getMovement().getGoY();
                checkersMap.setLogMsg(msg);
                logs.setText(msg);
                autoScroll();

                deletePiece(x,y);
                drawPiece(selected);
                checkEndGame(false);

                callIA();
            }

            player.setSelectedPiece(null);
        }
    }

    private void setClickListeners() {
        cell00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell00, 0, 0);
            }
        });

        cell01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell01, 0, 1);
            }
        });

        cell02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell02, 0, 2);
            }
        });

        cell03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell03, 0, 3);
            }
        });

        cell04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell04, 0, 4);
            }
        });

        cell05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell05, 0, 5);
            }
        });

        cell06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell06, 0, 6);
            }
        });

        cell07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell07, 0, 7);
            }
        });

        cell10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell10, 1, 0);
            }
        });

        cell11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell11, 1, 1);
            }
        });

        cell12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell12, 1, 2);
            }
        });

        cell13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell13, 1, 3);
            }
        });

        cell14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell14, 1, 4);
            }
        });

        cell15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell15, 1, 5);
            }
        });

        cell16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell16, 1, 6);
            }
        });

        cell17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell17, 1, 7);
            }
        });

        cell20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell20, 2, 0);
            }
        });

        cell21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell21, 2, 1);
            }
        });

        cell22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell22, 2, 2);
            }
        });

        cell23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell23, 2, 3);
            }
        });

        cell24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell24, 2, 4);
            }
        });

        cell25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell25, 2, 5);
            }
        });

        cell26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell26, 2, 6);
            }
        });

        cell27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell27, 2, 7);
            }
        });

        cell30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell30, 3, 0);
            }
        });

        cell31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell31, 3, 1);
            }
        });

        cell32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell32, 3, 2);
            }
        });

        cell33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell33, 3, 3);
            }
        });

        cell34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell34, 3, 4);
            }
        });

        cell35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell35, 3, 5);
            }
        });

        cell36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell36, 3, 6);
            }
        });

        cell37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell37, 3, 7);
            }
        });

        cell40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell40, 4, 0);
            }
        });

        cell41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell41, 4, 1);
            }
        });

        cell42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell42, 4, 2);
            }
        });

        cell43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell43, 4, 3);
            }
        });

        cell44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell44, 4, 4);
            }
        });

        cell45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell45, 4, 5);
            }
        });

        cell46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell46, 4, 6);
            }
        });

        cell47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell47, 4, 7);
            }
        });

        cell50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell50, 5, 0);
            }
        });

        cell51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell51, 5, 1);
            }
        });

        cell52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell52, 5, 2);
            }
        });

        cell53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell53, 5, 3);
            }
        });

        cell54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell54, 5, 4);
            }
        });

        cell55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell55, 5, 5);
            }
        });

        cell56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell56, 5, 6);
            }
        });

        cell57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell57, 5, 7);
            }
        });

        cell60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell60, 6, 0);
            }
        });

        cell61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell61, 6, 1);
            }
        });

        cell62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell62, 6, 2);
            }
        });

        cell63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell63, 6, 3);
            }
        });

        cell64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell64, 6, 4);
            }
        });

        cell65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell65, 6, 5);
            }
        });

        cell66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell66, 6, 6);
            }
        });

        cell67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell67, 6, 7);
            }
        });

        cell70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell70, 7, 0);
            }
        });

        cell71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell71, 7, 1);
            }
        });

        cell72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell72, 7, 2);
            }
        });

        cell73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell73, 7, 3);
            }
        });

        cell74.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell74, 7, 4);
            }
        });

        cell75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell75, 7, 5);
            }
        });

        cell76.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell76, 7, 6);
            }
        });

        cell77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerClick(cell77, 7, 7);
            }
        });
    }

    private void init() {
        iaThinking = findViewById(R.id.ia_think);

        green = getResources().getColor(R.color.green);
        background_blacks = getResources().getColor(R.color.background1);
        blue = getResources().getColor(R.color.purple_700);

        logScroll = findViewById(R.id.scrollLogs);

        scorePlayer = findViewById(R.id.scorePlayer);
        scoreIa = findViewById(R.id.scoreIA);

        logs = findViewById(R.id.tvLogs);
        checkersMap.setLogs(logs);

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
    }
}