package com.thehe.Reversi.View.BoardComponents;

import com.thehe.Reversi.Model.Piece;

import javafx.scene.layout.GridPane;

public class BoardView extends GridPane {
	
	private Spot[][] board;
	
	public BoardView(int givenBoardSize) {
		
		board = new Spot[givenBoardSize][givenBoardSize];
		
		for (int row = 0; row < givenBoardSize; row++) {
			for (int col = 0; col < givenBoardSize; col++) {
				board[row][col] = new Spot();
			}
			
			addRow(row, board[row]);
		}

	}
	
	  
	public void setSpot(int givenRow, int givenCol, Piece givenPiece) {
		board[givenRow][givenCol].setPiece(givenPiece);
	}
	
	public Spot getSpot(int givenRow, int givenCol) {
		return board[givenRow][givenCol];
	}
	
}
