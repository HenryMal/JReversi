package com.thehe.Reversi.Model;

public class Player {

	private final Piece playerColor;
	
	private int score;
	private boolean wonGame;
	private boolean turn;
	
	public Player (Piece color, boolean initialTurn) {
		this.playerColor = color;
		this.wonGame = false;
		this.turn = initialTurn;
	}
	
	// SCORE SHIT
	public int getScore() {
		return score;
	}	
	public void setScore(int givenScore) {
		score = givenScore;
	}
	
	
	// PLAYER WIN STATE
	public boolean getWonGame() {
		return wonGame;
	}
	public void setWonGame(boolean didWin) {
		wonGame = didWin;
	}
	
	// PLAYER'S TURN
	public boolean getTurn() {
		return turn;
	}
	public void setTurn(boolean givenTurn) {
		turn = givenTurn;
	}
	
	public Piece getPiece() {
		return playerColor;
	}
	
	public String toString() {
		return "{ color: " + playerColor 
				+ " | score: " + score
				+ " | did win: " + wonGame
				+ " | turn: " + turn
				+ " }";
	}
	
}
