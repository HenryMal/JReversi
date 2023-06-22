package com.thehe.Reversi.Presenter;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.Model.ReversiModel;
import com.thehe.Reversi.View.components.BoardView;
import com.thehe.Reversi.View.utils.AnimationHandler;

import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Presenter {
	
	private AnimationHandler animationHandler;
	private ReversiModel reversiModel;
	private BoardView reversiView;
	
	public Presenter() {
		
		this.reversiModel = new ReversiModel();
		this.reversiView = new BoardView(reversiModel.getBoardSize());
		animationHandler = new AnimationHandler();
		
		init();
		/* TODO:
		 * 
		 *  1: make gui for keeping track of piece count
		 *     (every time we make a move, we grab the scores and pass them to the views)
		 *     
		 *  2: when a player cant make a move, display PASS on the screen
		 *     o (overlay that whatever player cannot make a move, and the other player
		 *        can make as much as he wants so long as you cannot make a move)
		 *     o (display this message once)
		 *     o (if it has already been shown, just show WHO passed)
		 *     
		 *     
		 *     
		 *  3: perhaps game overlay when game is over and restart the game.
		 *     o (port over code from 2048)
		 *     
		 *  4: forfeit buttons.
		 *     o (whoever turn it is, when they click it, they instantly lose no matter what)
		 *  
		 *  TODO: 
		 *  
		 *  refactor code. for example:
		 *  
		 *  have an animation handler class SPECIFICALLY for showing availableSpots
		 *  have an animation handler class SPECIFICALLY for flipping pieces
		 *  
		 *  so on.
		 *  
		 */
	}
	
	private void init() {
		bindSpots();
		synchronize();
		showAvailableSpots();
	}
	
	private void bindSpots() {

		for (int rowIndex = 0; rowIndex < reversiModel.getBoardSize(); ++rowIndex) {
			
			for (int colIndex = 0; colIndex < reversiModel.getBoardSize(); ++colIndex) {
				
				final int row = rowIndex;
				final int col = colIndex;
				
				reversiView.getSpot(rowIndex, colIndex).setOnMouseClicked(new EventHandler<MouseEvent>() {
					
					@Override
					public void handle(MouseEvent mouseEvent) {
				        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
				        	handleLeftClick(row, col);
				        }
						
					}
					
				});
			}
			
		}
	}
	
	private void synchronize() {
		
		for (int rowIndex = 0; rowIndex < reversiModel.getBoardSize(); ++rowIndex) {
			
			for (int colIndex = 0; colIndex < reversiModel.getBoardSize(); ++colIndex) {
				
				if (reversiModel.getSpot(rowIndex, colIndex) != null) {
					reversiView.setSpot(rowIndex, colIndex, reversiModel.getSpot(rowIndex, colIndex));
				}

			}
			
		}
		
	}
	
	private void showAvailableSpots() {
		
		animationHandler.clearAnimaiteIndicators();
		addAnimationToIndicators();
		animationHandler.playAnimateIndicators();

	}
	
	private void addAnimationToIndicators() {
		reversiModel.getAvailableMovesSet().forEach(coords -> {
			setIndicatorColor(coords[0], coords[1]);
			animationHandler.addFadeAnimation(reversiView.getSpot(coords[0], coords[1]).getIndicator());
		});
	}
	
	private void setIndicatorColor(int givenRowIndex, int givenColIndex) {
		Piece indicator = (reversiModel.getPlayerOne().getTurn() == true) ? 
				reversiModel.getPlayerOne().getPiece() : 
				reversiModel.getPlayerTwo().getPiece();
		
		reversiView.getSpot(givenRowIndex, givenColIndex).setIndicator(indicator);
	}	
	
	public void handleLeftClick(int rowIndex, int colIndex) {
		
		long lastTime = System.nanoTime();
		
		if (isClickable(rowIndex, colIndex)) {
			
			resetIndicators();
			
			reversiModel.makeMove(new int[] {rowIndex, colIndex}, reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());
	
			flipPieces();
			spawnPiece(rowIndex, colIndex);
			
			// needs refactoring, but yes it is DONE! (kinda hacky)
			// display an overlay that you can make the move. 
			if (reversiModel.getAvailableMovesSet().isEmpty()) {
				
				boolean pass = true; 
				System.out.println("pass");
				reversiModel.playerPass(reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());
				
				if (reversiModel.getAvailableMovesSet().isEmpty()) {
					boolean gameover = true;
					System.out.println("gameover!");

				}
				

			}
			
			showAvailableSpots();

		}
		
		long currentTime = System.nanoTime();
		System.out.println((currentTime - lastTime) / 1000000000.0);	

	}
	
	private boolean isClickable(int givenRowIndex, int givenColIndex) {
		return (reversiView.getSpot(givenRowIndex, givenColIndex).getPiece() == null && 
				reversiModel.getAvailableMovesSet().contains(new int[] {givenRowIndex, givenColIndex}) && 
				animationHandler.getFlippingAnimation().getStatus() != Status.RUNNING);
	}
	
	private void flipPieces() {
		
		animationHandler.clearFlipAnimation();
		addFlippingAnimations();
		animationHandler.playFlippingAnimation();
	
	}
	
	private void addFlippingAnimations() {
		reversiModel.getFlippedPieces().forEach(coords -> {
			animationHandler.addFlippingAnimation(reversiView.getSpot(coords[0], coords[1]).getPiece(), reversiModel.getSpot(coords[0], coords[1]));
		});	
	}
	
	private void spawnPiece(int givenRowIndex, int givenColIndex) {
		
		if (animationHandler.getSpawnAnimation().getStatus() == Status.RUNNING) {
			animationHandler.interruptSpawnAnimation();
		}

		initSpanwedPieceAnimation(givenRowIndex, givenColIndex);
		afterSpawnAnimationFinished(givenRowIndex, givenColIndex);
		
		animationHandler.playSpawnAnimation();
	}
	
	private void initSpanwedPieceAnimation(int givenRowIndex, int givenColIndex) {
		reversiView.setSpot(givenRowIndex, givenColIndex, reversiModel.getSpot(givenRowIndex, givenColIndex));
		reversiView.getSpot(givenRowIndex, givenColIndex).getPiece().setScale(0);
		animationHandler.addSpawnAnimation(reversiView.getSpot(givenRowIndex, givenColIndex).getPiece());
	}
	
	private void afterSpawnAnimationFinished(int givenRowIndex, int givenColIndex) {
		animationHandler.getSpawnAnimation().setOnFinished(event -> {
		    reversiView.getSpot(givenRowIndex, givenColIndex).getPiece().setScale(1.0);
		});
	}
	
	private void resetIndicators() {
		reversiModel.getAvailableMovesSet().forEach(coords -> {
			reversiView.getSpot(coords[0], coords[1]).removeIndicator();
		});
	}
	
	public Pane getPane() {
		return reversiView;
	}

}
