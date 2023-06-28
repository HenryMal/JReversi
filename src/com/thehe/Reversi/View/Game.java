package com.thehe.Reversi.View;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.Model.Player;
import com.thehe.Reversi.View.BoardComponents.GameOverOverlay;
import com.thehe.Reversi.View.BoardComponents.GameView;
import com.thehe.Reversi.View.BoardComponents.Spot;
import com.thehe.Reversi.View.StatsComponents.SideView;
import com.thehe.Reversi.View.utils.ButtonCreator;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class Game extends BorderPane {
	
	GameView gameView;
	SideView sideView;
	
	public Game(int givenBoardSize, Player playerOne, Player playerTwo, int fontSize) {
		
		initChildren(givenBoardSize, playerOne, playerTwo, fontSize);
		
		setCenter(gameView);
		setRight(sideView);
		
		setAlignment(sideView, Pos.CENTER);
		setAlignment(gameView, Pos.CENTER);
		
	}
	
	private void initChildren(int givenBoardSize, Player playerOne, Player playerTwo, int fontSize) {
		
		gameView = new GameView(givenBoardSize, fontSize);
		sideView = new SideView(playerOne, playerTwo, fontSize);
		
	}
	
	public void setSpot(int givenRow, int givenCol, Piece givenPiece) {
		gameView.setSpot(givenRow, givenCol, givenPiece);
	}
	
	public Spot getSpot(int givenRow, int givenCol) {
		return gameView.getSpot(givenRow, givenCol);
	}
	
	public void setWinnerText(Piece givenPiece) {
		gameView.setWinnerText(givenPiece);
	}
	
	public void setGameOverOpacity(double givenDouble) {
		gameView.setOpacity(givenDouble);
	}
	
	public void setTieText() {
		gameView.setTieText();
	}
	
	public void removeGameOver() {
		gameView.removeGameOver();
	}
	
	public void addGameOver() {
		gameView.addGameOver();
	}
	
	public GameOverOverlay getGameOver() {
		return gameView.getGameOver();
	}
	
	public void setPassMessageVisible() {
		sideView.setPassMessageVisible();
	}
	
	public void setPassMessageNotVisible() {
		sideView.setPassMessageNotVisible();
	}
	
	public void setPlayerOneScore(int givenScore) {
		sideView.setPlayerOneScore(givenScore);
	}
	
	public void setPlayerTwoScore(int givenScore) {
		sideView.setPlayerTwoScore(givenScore);
	}
	
	public void setText(Player playerOne, Player playerTwo) {
		sideView.setText(playerOne.getPiece(), playerTwo.getPiece());
	}
	
	public ButtonCreator getForfeitButton() {
		return sideView.getForfeitButton();
	}
	
	public ButtonCreator getResetGame() {
		return gameView.getResetGame();
	}
	
}
