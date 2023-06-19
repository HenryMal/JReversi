package com.thehe.Reversi.Presenter;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.Model.ReversiModel;
import com.thehe.Reversi.View.BoardView;
import com.thehe.Reversi.View.PieceView;
import com.thehe.Reversi.View.utils.AnimationHandler;

import java.util.Arrays;

import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Presenter {
	
	private AnimationHandler animationHandler;
	private ReversiModel reversiModel;
	private BoardView reversiView;
	
	public Presenter() {
		
		this.reversiModel = new ReversiModel();
		this.reversiView = new BoardView();
		animationHandler = new AnimationHandler();
		
		bindSpots();
		synchronize();
		
		// we dont need to synch EVERY time we make a move
		// we just need to updated what is needed to be updated.
		// so we are good at the moment.
		
		// lets just get this shit working tomorrow 
		
		// it works!
		// but its inefficient. we just need to flip pieces.
		
		/* TODO:
		 * 1: play a fading animation for the indicators. (DONE)
		 * 2: play a animation where we set the scale of the piece from 0 to 1 when we place it. (DONE)
		 * 3: play animation for all pieces flipped at the same time. (parallel transition)
		 * 
		 * TODO: handle the other logic later:
		 * 
		 * 1: handle finishing a game
		 * 2: when both players cant make a turn.
		 * 3: keep track of score. 
		 *  
		 *  animation has a much higher priority for me. i think its more sexy.
		 *  
		 */
		showAvailableSpots();
		
	}
	
	private void showAvailableSpots() {
		
		animationHandler.clearAnimaiteIndicators();
		
		reversiModel.getAvailableMovesSet().forEach(coords -> {
			
			Piece indicator = (reversiModel.getPlayerOne().getTurn() == true) ? 
					reversiModel.getPlayerOne().getPiece() : 
					reversiModel.getPlayerTwo().getPiece();
			
			
			reversiView.getSpot(coords[0], coords[1]).setIndicator(indicator);
			animationHandler.addFadeAnimation(reversiView.getSpot(coords[0], coords[1]).getIndicator());
		
		});
		
		animationHandler.playAnimateIndicators();

	}
	
	private void bindSpots() {

		for (int rowIndex = 0; rowIndex < 8; ++rowIndex) {
			
			for (int colIndex = 0; colIndex < 8; ++colIndex) {
				
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
		
		int numCols = reversiModel.getBoardSize();
		int numRows = reversiModel.getBoardSize();
		
		for (int rowIndex = 0; rowIndex < numRows; ++rowIndex) {
			
			for (int colIndex = 0; colIndex < numCols; ++colIndex) {
				
				if (reversiModel.getSpot(rowIndex, colIndex) != null) {
					reversiView.setSpot(rowIndex, colIndex, reversiModel.getSpot(rowIndex, colIndex));
				}

			}
			
		}
		
	}
	
	
	public void handleLeftClick(int rowIndex, int colIndex) {
		
		long lastTime = System.nanoTime();
		
		if (reversiView.getSpot(rowIndex, colIndex).getPiece() == null && 
				reversiModel.getAvailableMovesSet().contains(new int[] {rowIndex, colIndex} )) {
			
			reversiModel.makeMove(new int[] {rowIndex, colIndex}, reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());
			
			resetBoard();
			synchronize();
			
			spawnPiece(rowIndex, colIndex);
			showAvailableSpots();
			
			
		}
		
		else {
			System.out.println("invalid move nig");
		}
		
		long currentTime = System.nanoTime();
//		
		System.out.println((currentTime - lastTime) / 1000000000.0);


	}
	
	private void spawnPiece(int givenRowIndex, int givenColIndex) {
		if (animationHandler.getSpawnAnimation().getStatus() == Status.RUNNING) {
			Duration totalDuration = animationHandler.getSpawnAnimation().getTotalDuration();
		    animationHandler.getSpawnAnimation().jumpTo(totalDuration); 
		}
		
		reversiView.getSpot(givenRowIndex, givenColIndex).getPiece().setScaleX(0);
		reversiView.getSpot(givenRowIndex, givenColIndex).getPiece().setScaleY(0);
		
		animationHandler.addSpawnAnimation(reversiView.getSpot(givenRowIndex, givenColIndex).getPiece());
		
		animationHandler.getSpawnAnimation().setOnFinished(event -> {
		    reversiView.getSpot(givenRowIndex, givenColIndex).getPiece().setScaleX(1.0);
		    reversiView.getSpot(givenRowIndex, givenColIndex).getPiece().setScaleY(1.0);
		});
		
		animationHandler.playSpawnAnimation();
	}
	
	
	private void resetBoard() {
		int numCols = reversiModel.getBoardSize();
		int numRows = reversiModel.getBoardSize();
		
		for (int rowIndex = 0; rowIndex < numRows; ++rowIndex) {
			
			for (int colIndex = 0; colIndex < numCols; ++colIndex) {
				
				reversiView.getSpot(rowIndex, colIndex).removePiece();
				reversiView.getSpot(rowIndex, colIndex).removeIndicator();

			}
			
		}
	}
	
	public Pane getPane() {
		return reversiView;
	}

}
