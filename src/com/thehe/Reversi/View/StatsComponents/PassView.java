package com.thehe.Reversi.View.StatsComponents;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.View.utils.TextCreator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PassView extends VBox {
	
	private TextCreator turnIndicator;
	private TextCreator passMessage;
	private DropShadow dropShadow;
	
	public PassView(Piece givenPiece, Piece otherPiece, int fontSize) {
		
		createBackground(Color.web("#424242"));
		createDropShadow();
		setSpacing();
		initChildren(givenPiece, otherPiece, fontSize);
		
		getChildren().addAll(turnIndicator, passMessage);
		
	}
	
	private void createBackground(Color givenColor) {
		setBackground(new Background(new BackgroundFill(givenColor, new CornerRadii (6), Insets.EMPTY)));
	}
	
	private void createDropShadow() {
		dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.5, 0, 0);
		setEffect(dropShadow);
	}
	
	private void setSpacing() {
		setAlignment(Pos.CENTER);
		setSpacing(10);
		setPadding(new Insets(10, 15, 10, 15));
	}
	
	private void initChildren(Piece givenPiece, Piece otherPiece, int fontSize) {
		turnIndicator = new TextCreator(givenPiece.toString() + "'S TURN!", fontSize, Color.WHITE);
		passMessage  = new TextCreator("", fontSize, Color.WHITE);
	}
	
	public void setText(Piece givenPiece, Piece otherPiece) {
		turnIndicator.setText(givenPiece.toString() + "'S TURN!");
		passMessage.setText(otherPiece.toString() + " has no legal moves!");
	}
	
	public void setPassMessageVisible() {
		passMessage.setVisible(true);
	}
	
	public void setPassMessageNotVisible() {
		passMessage.setVisible(false);
	}
	
}
