package com.thehe.Reversi.View.StatsComponents;

import com.thehe.Reversi.Model.Piece;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ScoreWrapper extends HBox {
	
	private static final int WRAPPER_WIDTH = 400;
	
	private ScoreView playerOneScore;
	private ScoreView playerTwoScore;
	private DropShadow dropShadow;

	public ScoreWrapper(int fontSize) {
		
		createBackground(Color.web("#424242"));
		createDropShadow();
		setSpacing();
		lockWidth(WRAPPER_WIDTH);
		initChildren(fontSize);

		getChildren().addAll(playerOneScore, playerTwoScore);
		
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
		setSpacing(150);
		setPadding(new Insets(15, 25, 15, 25));
	}
	
	private void lockWidth(int givenWidth) {
		setMaxWidth(givenWidth);
        setMinWidth(givenWidth); 
        setPrefWidth(givenWidth); 
	}
	
	private void initChildren(int fontSize) {
		playerOneScore = new ScoreView(fontSize, Piece.WHITE);
		playerTwoScore = new ScoreView(fontSize, Piece.BLACK);
	}
	
	public void setPlayerOneScore(int givenScore) {
		playerOneScore.setScore(givenScore);
	}
	
	public void setPlayerTwoScore(int givenScore) {
		playerTwoScore.setScore(givenScore);
	}


	
}
