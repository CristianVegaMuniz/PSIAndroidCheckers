package com.example.checkers;

public class Player {
    private  Piece selectedPiece = null;

    public Player() {
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }
}
