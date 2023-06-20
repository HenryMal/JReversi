package com.thehe.Reversi.Model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ReversiModel {

	private final static int[][] DIRECTIONS = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };

	private final static int BOARD_SIZE = 7;
	private Piece[][] board;
	
	// getters for these.
	private int playerOneScore;
	private int playerTwoScore;
	
	private Player playerOne;
	private Player playerTwo;
	
	private List<int[]> surroundingPieces = new LinkedList<int[]>();
	
	// we need getters for these.
	private Set<int[]> availableMoves = new TreeSet<int[]>(Arrays::compare);
	private List<int[]> validFlippedPieces = new LinkedList<int[]>();
	private List<int[]> piecesToBeFlipped = new LinkedList<int[]>();
	
	public ReversiModel() {
		
		this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
		
		playerOne = new Player(Piece.WHITE, true);
		playerTwo = new Player(Piece.BLACK, false);
		
		initBoard();
		calculateScores();
		getPlayerAllAvailableMoves(playerOne);
		
	}

	
	private void initBoard() {
	
		for (int row = 0; row < BOARD_SIZE; row++) {
			
			for (int col = 0; col < BOARD_SIZE; col++) {
	
				if (row == 0 || col == 0 || row == BOARD_SIZE - 1 || col == BOARD_SIZE - 1)	{

					board[row][col] = Piece.WHITE;

				}

			}

		}
		
		board[3][3] = Piece.WHITE;
		board[4][4] = Piece.WHITE;
		
		board[3][4] = Piece.BLACK;		
		board[4][3] = Piece.BLACK;
		
	}
	
	private void getSurroundingSpots(int givenRowIndex, int givenColIndex) {
		for(int[] direction : DIRECTIONS) {
			
			int rowIndex = givenRowIndex + direction[0];
			int colIndex = givenColIndex + direction[1];
			
			surroundingPieces.add(new int[] { rowIndex, colIndex });
			
		}		
	}
	
	private void removeOutOfBounds() {
		surroundingPieces.removeIf(
				array -> Arrays.stream(array).anyMatch(number -> number < 0 ||
						number >= BOARD_SIZE));
	}
	

	private void removeUnneededElements(Piece playerPiece) {
		surroundingPieces.removeIf(
				array -> board[array[0]][array[1]] == playerPiece ||
						board[array[0]][array[1]] == null);
	}
	
	private void filterUselessSpots(int givenRowIndex, int givenColIndex, Piece playerPiece) {
		getSurroundingSpots(givenRowIndex, givenColIndex);
		removeOutOfBounds();
		removeUnneededElements(playerPiece);
	}
	
	private void getAvailableSpots(int givenRowIndex, int givenColIndex, Piece playerPiece) {
		
		filterUselessSpots(givenRowIndex, givenColIndex, playerPiece);
		boardTraversal(givenRowIndex, givenColIndex, playerPiece, true);
		surroundingPieces.clear();

	}

	private void placePiece(int givenRowIndex, int givenColIndex, Piece playerPiece) {
		
		board[givenRowIndex][givenColIndex] = playerPiece;
		
		filterUselessSpots(givenRowIndex, givenColIndex, playerPiece);
		boardTraversal(givenRowIndex, givenColIndex, playerPiece, false);
		
		surroundingPieces.clear();

	}
	
	private void boardTraversal(
			int givenRowIndex, 
			int givenColIndex, 
			Piece playerPiece,
			boolean shouldNotFlip
			) {

		surroundingPieces.forEach(array -> {

			int[] direction = new int[2];
			int[] currentIndex = new int[2];
			
			direction[0] = array[0] - givenRowIndex;
			direction[1] = array[1] - givenColIndex; 
			
			currentIndex[0] = givenRowIndex + direction[0];
			currentIndex[1] = givenColIndex + direction[1];
			
			if (shouldNotFlip) {
				traverseForNullSpots(currentIndex, direction, playerPiece);
				
			} 
			
			else {
				traverseForFlippingPieces(currentIndex, direction, playerPiece);

			}			
			
		});
		

		
	}

	private void traverseForNullSpots(int[] currentIndex, int[] direction, Piece playerPiece) { 

		while(keepCheckingSpots(currentIndex, playerPiece)) {
			currentIndex[0] += direction[0];
			currentIndex[1] += direction[1];
		}
		
		if (isValidEmptySpot(currentIndex, playerPiece)) {
			availableMoves.add(new int[] { currentIndex[0], currentIndex[1] });
		}
		
		
	}

	private void traverseForFlippingPieces(int[] currentIndex, int[] direction, Piece playerPiece) { 

		validFlippedPieces.clear();
		
		while(keepCheckingSpots(currentIndex, playerPiece)) {
			
			validFlippedPieces.add(new int[] { currentIndex[0], currentIndex[1] });

			currentIndex[0] += direction[0];
			currentIndex[1] += direction[1];
			
			if (isValidEmptySpot(currentIndex, playerPiece)) {
				validFlippedPieces.clear();
			}
			
		}
		
		if (isSamePiece(currentIndex, playerPiece)) {
			addToBeFlippedList();
		}

	}

	
	private boolean borderCheck(int givenRowIndex, int givenColIndex) {
		
		return (givenRowIndex < 0 ||
				givenColIndex < 0 ||
				givenColIndex > BOARD_SIZE - 1 ||
				givenRowIndex > BOARD_SIZE - 1);
		
	}
	
	private boolean keepCheckingSpots(int[] givenCurrentIndex, Piece givenPlayerPiece) {
		
		return (isOppositePiece(givenCurrentIndex, givenPlayerPiece) && 
				board[givenCurrentIndex[0]][givenCurrentIndex[1]] != null);
		
	}
	
	private boolean isValidEmptySpot(int[] givenCurrentIndex, Piece givenPlayerPiece) {
		
		return (isOppositePiece(givenCurrentIndex, givenPlayerPiece) &&
				board[givenCurrentIndex[0]][givenCurrentIndex[1]] == null);
		
	}
	
	private boolean isOppositePiece(int[] givenCurrentIndex, Piece givenPlayerPiece) {
		
		return (!borderCheck(givenCurrentIndex[0], givenCurrentIndex[1]) && 
				board[givenCurrentIndex[0]][givenCurrentIndex[1]] != givenPlayerPiece);
		
	}
	
	private boolean isSamePiece(int[] givenCurrentIndex, Piece givenPlayerPiece) {
		
		return (!borderCheck(givenCurrentIndex[0], givenCurrentIndex[1]) && 
				board[givenCurrentIndex[0]][givenCurrentIndex[1]] == givenPlayerPiece);
				
	}
	
	private void addToBeFlippedList() {
		
		validFlippedPieces.forEach(coords -> {
			
			piecesToBeFlipped.add(coords);
			
		});
			
	}
	
	private void flipPieces() {
		
		piecesToBeFlipped.forEach(coords -> {
			
			board[coords[0]][coords[1]] = Piece.flipPiece(board[coords[0]][coords[1]]);
			
		});
		
	}
	
	private void getPlayerAllAvailableMoves(Player givenPlayer) {
		
		for (int row = 0; row < BOARD_SIZE; row++) {
			
			for (int col = 0; col < BOARD_SIZE; col++) {
	
				if (board[row][col] == givenPlayer.getPiece())	{

					getAvailableSpots(row, col, givenPlayer.getPiece());

				}

			}

		}
		
	}
	
	private void makePlayerTurn (int[] spotCoords, Player givenPlayer, Player givenOtherPlayer) {
		if (givenPlayer.getTurn()) {
			placePiece(spotCoords[0], spotCoords[1], givenPlayer.getPiece());
		}
		
		else if (givenOtherPlayer.getTurn()){
			placePiece(spotCoords[0], spotCoords[1], givenOtherPlayer.getPiece());
		}
	}
	
	private void resetPlayerTurns (Player givenPlayer, Player givenOtherPlayer) {
		givenPlayer.setTurn(!givenPlayer.getTurn());
		givenOtherPlayer.setTurn(!givenOtherPlayer.getTurn());
	}
	
	private void getPlayersMoves(Player givenPlayer, Player givenOtherPlayer) {
		if (givenPlayer.getTurn()) {
			getPlayerAllAvailableMoves(givenPlayer);
		}
		
		else if (givenOtherPlayer.getTurn()){
			getPlayerAllAvailableMoves(givenOtherPlayer);
		}
	}
	
	private void resetPlayerMoves(Player givenPlayer, Player givenOtherPlayer) {
		availableMoves.clear();
		getPlayersMoves(givenPlayer, givenOtherPlayer);
	}
	
	public void playerPass(Player givenPlayer, Player givenOtherPlayer) {
		resetPlayerTurns(givenPlayer, givenOtherPlayer);
		resetPlayerMoves(givenPlayer, givenOtherPlayer);
	}
	
	private void calculateScores() {
		
		playerOneScore = 0;
		playerTwoScore = 0;
		
		for (int row = 0; row < BOARD_SIZE; row++) {
			
			for (int col = 0; col < BOARD_SIZE; col++) {
	
				if (board[row][col] == Piece.WHITE)	{
					playerOneScore += 1;
				} 
				
				if (board[row][col] == Piece.BLACK) {
					playerTwoScore += 1;
				}

			}

		}
		
	}
	
	// there is still some edge cases, like when a player cant make a move and must pass.
	public void makeMove(int[] spotCoords, Player givenPlayer, Player givenOtherPlayer) {
		
		piecesToBeFlipped.clear();
		makePlayerTurn(spotCoords, givenPlayer, givenOtherPlayer);
		resetPlayerTurns(givenPlayer, givenOtherPlayer);
		flipPieces();
		resetPlayerMoves(givenPlayer, givenOtherPlayer);
		calculateScores();
		System.out.println("PLAYER 1:" + playerOneScore + " | PLAYER 2: " + playerTwoScore);
		
	}
	
	public List<int[]> getFlippedPieces() {
		return piecesToBeFlipped;
	}
	
	public Set<int[]> getAvailableMovesSet() {
		return availableMoves;
	}
	
	public int getBoardSize() {
		return BOARD_SIZE;
	}
	
	public Piece getSpot(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}
	
	public Player getPlayerOne() {
		return playerOne;
	}
	
	public Player getPlayerTwo() {
		return playerTwo;
	}
	
	public int getPlayerOneScore() {
		return playerOneScore;
	}
	
	public int getPlayerTwoScore() {
		return playerTwoScore;
	}
	
	// 0 = WHITE
	// O = BLACK
	public String toString() {
		
		StringBuilder boardString = new StringBuilder();
		String content = "";
		
		for (int row = 0; row < BOARD_SIZE; row++) {
			
			boardString.append("[");
			
			for (int col = 0; col < BOARD_SIZE; col++) {
				
				content = (board[row][col] == null) ? " - " : board[row][col].toString(board[row][col]);
				boardString.append(content);
				
				if (col < BOARD_SIZE - 1) boardString.append("|");
				
			}
			
			boardString.append("]").append(System.lineSeparator());
			
		}
		
		return boardString.toString();
		
	}
	
}
