package com.thehe.Reversi.View;

import com.thehe.Reversi.Model.Piece;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardView extends GridPane {
	
	private Spot[][] board;
	
	public BoardView() {
		
		board = new Spot[8][8];
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
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
