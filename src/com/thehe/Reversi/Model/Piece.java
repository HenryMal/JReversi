package com.thehe.Reversi.Model;

public enum Piece {
	
	WHITE,
	BLACK;
	
	// O = WHITE
	// X = BLACK
	public String toString(Piece givenValue) {
		return (givenValue == WHITE) ? " 0 " : " O ";
	}
	
	public static Piece flipPiece(Piece givenValue) {
		return (givenValue == WHITE) ? BLACK : WHITE;
	}
}
