package com.thehe.Reversi.View.BoardComponents;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.View.utils.ButtonCreator;
import com.thehe.Reversi.View.utils.TextCreator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameOverOverlay extends VBox {

	ButtonCreator resetGame;
	TextCreator gameOverText;
	
	public GameOverOverlay(double givenHeight, double givenWidth) {
		
		setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF", 0.50), CornerRadii.EMPTY, Insets.EMPTY)));
		
		setSize(givenWidth, givenHeight);
		initChildren();

		setSpacing(25);
		setAlignment(Pos.CENTER);
		
		getChildren().addAll(gameOverText, resetGame);
		
	}
	
	private void setSize(double givenWidth, double givenHeight) {
		setMaxWidth(givenWidth);
		setMinWidth(givenWidth);
		setMinHeight(givenHeight);
		setMaxHeight(givenHeight);
	}
	
	private void initChildren() {
		gameOverText = new TextCreator("", 18, Color.BLACK);
		resetGame = new ButtonCreator("New game?", 18);
	}
	
	public void setWinnerText(Piece givenPiece) {
		gameOverText.setText(givenPiece.toString() + " WINS!");
	}
	
	public void setTieText() {
		gameOverText.setText("IT'S A TIE!");
	}
	
	public ButtonCreator getResetGame() {
		return resetGame;
	}
}
