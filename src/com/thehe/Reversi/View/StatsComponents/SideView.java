package com.thehe.Reversi.View.StatsComponents;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.Model.Player;
import com.thehe.Reversi.View.utils.ButtonCreator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideView extends VBox {
	
	private PassView passView;
	private ScoreWrapper scoreWrapper;
	private ButtonWrapper buttonWrapper;
	
	public SideView(Player playerOne, Player playerTwo, int fontSize) {
		
		createBackground(Color.web("#303030"));
		setSpacing();
		initChildren(playerOne.getPiece(), playerTwo.getPiece(), fontSize);

		setPlayerOneScore(playerOne.getScore());
		setPlayerTwoScore(playerTwo.getScore());
		
		getChildren().addAll(scoreWrapper, passView, buttonWrapper);
		
	}
	
	private void createBackground(Color givenColor) {
		setBackground(new Background(new BackgroundFill(givenColor, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	private void setSpacing() {
		setAlignment(Pos.CENTER);
		setSpacing(25);
		setPadding(new Insets(15, 30, 15, 25));
	}
	
	private void initChildren(Piece givenPiece, Piece givenOtherPiece, int fontSize) {
		passView = new PassView(givenPiece, givenOtherPiece, fontSize);
		scoreWrapper = new ScoreWrapper(fontSize);
		buttonWrapper = new ButtonWrapper(fontSize);
	}
	
	public void setPassMessageVisible() {
		passView.setPassMessageVisible();
	}
	
	public void setPassMessageNotVisible() {
		passView.setPassMessageNotVisible();
	}
	
	public void setPlayerOneScore(int givenScore) {
		scoreWrapper.setPlayerOneScore(givenScore);
	}
	
	public void setPlayerTwoScore(int givenScore) {
		scoreWrapper.setPlayerTwoScore(givenScore);
	}
	
	public void setText(Piece givenPiece, Piece givenOtherPiece) {
		passView.setText(givenPiece, givenOtherPiece);
	}
	
	public ButtonCreator getForfeitButton() {
		return buttonWrapper.getForfeitButton();
	}
	
}
