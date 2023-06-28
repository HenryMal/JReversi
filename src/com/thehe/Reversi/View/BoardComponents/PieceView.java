package com.thehe.Reversi.View.BoardComponents;

import com.thehe.Reversi.Model.Piece;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PieceView extends Circle {

	
	private double pieceSize = 25;
	private Color pieceColor;
	
	public PieceView(Piece givenPiece) {
		
		setRadius(pieceSize);
		pieceColor = Color.web(givenPiece.toString());
		setFill(pieceColor);

		
	}
	
	public void setScale(double givenSize) {
		setScaleX(givenSize);
		setScaleY(givenSize);
	}
	
	public void setColor(Piece givenPiece) {
		
		pieceColor = Color.web(givenPiece.toString());
		setFill(pieceColor);

	}
	
	
}
