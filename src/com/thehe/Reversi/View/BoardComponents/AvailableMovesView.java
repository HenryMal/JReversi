package com.thehe.Reversi.View.BoardComponents;

import com.thehe.Reversi.Model.Piece;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AvailableMovesView extends Circle {
	
	private final static double OUTLINE_SIZE = 20;
	private DropShadow outline;
	private Color outlineColor;
	
	public AvailableMovesView(Piece givenPiece) {
		
		setRadius(OUTLINE_SIZE);
		setDropShadow(givenPiece);
		
		setEffect(outline);
		setFill(Color.TRANSPARENT);
		setStroke(outlineColor);
		setStrokeWidth(1.5);
		
	}
	
	public void setColor(Piece givenPiece) {
		
		setDropShadow(givenPiece);
		setEffect(outline);

	}
	
	private void setDropShadow(Piece givenPiece) {
		outlineColor = Color.web(givenPiece.toString());
		outline = new DropShadow(BlurType.THREE_PASS_BOX, outlineColor, 10, 0.5, 0, 0);
	}
	
	
}
