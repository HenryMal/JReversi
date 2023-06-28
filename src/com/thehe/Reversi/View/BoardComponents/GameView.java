package com.thehe.Reversi.View.BoardComponents;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.View.utils.ButtonCreator;

import javafx.scene.layout.StackPane;

public class GameView extends StackPane {
	
	private BoardView boardView;
	private GameOverOverlay gameOver;
	
	public GameView(int givenBoardSize, int fontSize) {
		
		initChildren(givenBoardSize, fontSize);
		
		getChildren().addAll(boardView);
		
	}
	
	public void initChildren(int givenBoardSize, int fontSize) {
		boardView = new BoardView(givenBoardSize);
	}
	
	public void setSpot(int givenRow, int givenCol, Piece givenPiece) {
		boardView.setSpot(givenRow, givenCol, givenPiece);
	}
	
	public Spot getSpot(int givenRow, int givenCol) {
		return boardView.getSpot(givenRow, givenCol);
	}
	
	public void setWinnerText(Piece givenPiece) {
		gameOver.setWinnerText(givenPiece);
	}
	
	public void removeGameOver() {
		getChildren().remove(gameOver);
	}
	
	public void addGameOver() {
		gameOver = new GameOverOverlay(boardView.getHeight(), boardView.getWidth());
		getChildren().add(gameOver);
	}
	
	public void setTieText() {
		gameOver.setTieText();
	}
	
	public GameOverOverlay getGameOver() {
		return gameOver;
	}
	
	public ButtonCreator getResetGame() {
		return gameOver.getResetGame();
	}

}
