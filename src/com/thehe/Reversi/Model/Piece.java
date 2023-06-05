package com.thehe.Reversi.Model;

public enum Piece {
	
	WHITE,
	BLACK;
	
	private int[] coordinates = new int[2];
	
	public void setCoordinates(int rowCoords, int colCoords) {
		coordinates[0] = rowCoords;
		coordinates[1] = colCoords;
	}
	
	// O = WHITE
	// X = BLACK
	public String toString(Piece givenValue) {
		return (givenValue == WHITE) ? " 0 " : " O ";
	}
}
