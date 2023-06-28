package com.thehe.Reversi.View.BoardComponents;

import javafx.scene.shape.Rectangle;

import com.thehe.Reversi.Model.Piece;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Spot extends StackPane {
	
	private final static double SIZE = 60; 
	
	private Rectangle background;
	private PieceView piece;
	private AvailableMovesView availableMovesIndicator;
	private Color color = Color.web("#078744");
	
	
	public Spot() {

		background = new Rectangle(SIZE, SIZE, color);
		background.setStroke(Color.BLACK);
		
		getChildren().add(background);

	}
	
	public void setPiece(Piece givenPiece) {
		
		piece = new PieceView(givenPiece);
		getChildren().add(piece);

	}
	
	public PieceView getPiece() {
		return piece;
	}
	
	public AvailableMovesView getIndicator() {
		return availableMovesIndicator;
	}
	
	public void removePiece() {
		getChildren().remove(piece);
		piece = null;
	}
	
	public void setIndicator(Piece givenPiece) {
		
		availableMovesIndicator = new AvailableMovesView(givenPiece);
		getChildren().add(availableMovesIndicator);
		
	}
	
	public void removeIndicator() {
		getChildren().remove(availableMovesIndicator);
		availableMovesIndicator = null;
	}

}
