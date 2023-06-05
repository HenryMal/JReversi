package com.thehe.Reversi.Model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

	/*
	 * checks in this order
	 * 
	 *  - - -
	 *  - X -
	 *  - - -
	 *  
	 *  where X is the element chosen by the user.
	 *  
	 */
	private final static int[][] DIRECTIONS = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };

	private final static int BOARD_SIZE = 8;
	private Piece[][] board;
	
	private int playerOneScore;
	private int playerTwoSc0re;
	
	private Player playerOne;
	private Player playerTwo;
	
	List<int[]> surroundingPieces = new LinkedList<int[]>();
	List<int[]> availableMoves = new LinkedList<int[]>();
	
	public Board() {
		
		this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
		
		playerOne = new Player(Piece.WHITE, true);
		playerTwo = new Player(Piece.BLACK, false);
		
		initBoard();
		
		// all testing
		System.out.println(playerOne.toString());
		System.out.println(playerTwo.toString());
		
		for (int row = 0; row < BOARD_SIZE; row++) {
			
			for (int col = 0; col < BOARD_SIZE; col++) {
				
				// important to clear to reduce redundant code
				surroundingPieces.clear();
			
				if (board[row][col] == Piece.WHITE)	{
					System.out.println("CURRENT ROW: " + row + "| CURRENT COL: " + col);
					getSurroundingPieces(row, col, playerOne.getPiece());
					availableMoves.forEach(array -> System.out.println(Arrays.toString(array)));
				}

				//getSurroundingPieces(row, col, playerOne.getPiece());
			}

		}
		
		//getSurroundingPieces(3, 3, playerOne.getPiece());
		
		//surroundingPieces.forEach(array -> System.out.println(Arrays.toString(array)));
		//availableMoves.forEach(array -> System.out.println(Arrays.toString(array)));

		//testMarkAvailableSpots();
		

	}
	
	private void testMarkAvailableSpots() {
		for (int[] marked : availableMoves) {
			board[marked[0]][marked[1]] = Piece.WHITE;
		}
	}
	
	private void initBoard() {
		
		board[3][3] = Piece.WHITE;
		board[4][4] = Piece.WHITE;
		
		board[3][4] = Piece.BLACK;
		//board[3][5] = Piece.BLACK;
		
		board[4][3] = Piece.BLACK;
		
	}
	
	private void getSurroundingElements(int givenRowIndex, int givenColIndex) {

		Arrays.asList(DIRECTIONS).forEach(array -> {
			int rowIndex = givenRowIndex + array[0];
			int colIndex = givenColIndex + array[1];
			
			surroundingPieces.add(new int[] { rowIndex, colIndex });
		});
		
	}
	
	private void removeOutOfBounds() {
		surroundingPieces.removeIf(
				array -> Arrays.stream(array)
				.anyMatch(number -> number < 0 || number >= BOARD_SIZE));
	}
	
	private void removeUnneededElements(Piece playerPiece) {
		surroundingPieces.removeIf(
				array -> board[array[0]][array[1]] == playerPiece
				|| board[array[0]][array[1]] == null);
	}
	
	private void getSurroundingPieces(int givenRowIndex, int givenColIndex, Piece playerPiece) {
		
		getSurroundingElements(givenRowIndex, givenColIndex);
		removeOutOfBounds();
		removeUnneededElements(playerPiece);
		checkAvailableSpots(givenRowIndex, givenColIndex, playerPiece);
		
	}
	
	private void checkAvailableSpots(int givenRowIndex, int givenColIndex, Piece playerPiece) {
		
		int[] direction = new int[2];
		int[] currentIndex = new int[2];
		
		surroundingPieces.forEach(array -> {
			currentIndex[0] = givenRowIndex;
			currentIndex[1] = givenColIndex;
			direction[0] = array[0] - givenRowIndex; // this should be = 0 FST // this should be = 1
			direction[1] = array[1] - givenColIndex; // this should be = 1 FST // this should be = 0 
			
			while(board[currentIndex[0]][currentIndex[1]] != null) {
				currentIndex[0] += direction[0];
				currentIndex[1] += direction[1];
			}
			
			availableMoves.add(new int[] { currentIndex[0], currentIndex[1] });
		});
		
	}
	
	/*
	 * THE PROBLEM: so we know WHERE the pieces are and need to check all the elements following them
	 * [ - | - | - | - | - | - | - | - ]
     * [ - | - | - | - | - | - | - | - ]
     * [ - | - | - | - | - | - | - | - ]
	 * [ - | - | - | 0 | O | X | X | X ]
	 * [ - | - | - | O | 0 | - | - | - ]
     * [ - | - | - | X | - | - | - | - ]
     * [ - | - | - | X | - | - | - | - ]
     * [ - | - | - | X | - | - | - | - ]
	 * 
	 * this scenario, we are the WHITE PIECE and we want to check if we can place a piece FOLLOWING the black piece
	 * so we know that the coordinates of the back pieces are 
	 * 
	 * [3, 4]
	 * [4, 3]
	 * 
	 * so what we can do is subtract the both the ORIGINAL COORDINATES and other peices coordinates to get the direction of them
	 * [3, 4] - [3, 3] = [0, 1]
	 * new - ORIGINAL = direction
	 * 
	 * we got the direction of the other peice! now we just need to iterate UNTIL find a null space!
	 * we will have to check ALL whiet pieces though.
	 * 
	 * however, the design of the program will NOT allow for reundanancy when adding coordinates!
	 * 
	 */
	
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
	
	// UNIMPLEMENTED METHODS AND SHIT
	
	private void placePiece() {}

	private void transformPieces() {};
	
}
