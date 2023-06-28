package com.thehe.Reversi.Presenter;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.Model.ReversiModel;
import com.thehe.Reversi.View.Game;
import com.thehe.Reversi.View.utils.AnimationHandler;

import javafx.animation.Animation.Status;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Presenter {
	
	private final static int FONT_SIZE = 24;
	
	private AnimationHandler animationHandler;
	private ReversiModel reversiModel;
	private Game reversiView;
	private boolean isGameOver = false;
	
	public Presenter() {
		
		this.reversiModel = new ReversiModel();
		reversiView = new Game(reversiModel.getBoardSize(), 
				reversiModel.getPlayerOne(), 
				reversiModel.getPlayerTwo(),
				FONT_SIZE);
		
		animationHandler = new AnimationHandler();
		
		init();
	}
	
	private void init() {
		bindSpots();
		synchronize();
		showAvailableSpots();
		initializeForfeitButton();
	}
	
	
	private void initializeForfeitButton() {
		reversiView.getForfeitButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton() == MouseButton.PRIMARY && !isGameOver) {
		        	handleForfeit();
		        	
		        }
			}
		});
	}
	
	private void initializeGameOverButton() {
		reversiView.getResetGame().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
		        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
		        	resetGame();
		        }
			}
			
		});
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
		
		if (isClickable(rowIndex, colIndex)) {
			
			reversiView.setPassMessageNotVisible();	
			resetIndicators();
			reversiModel.makeMove(new int[] {rowIndex, colIndex}, reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());
			flipPieces();
			spawnPiece(rowIndex, colIndex);
			checkInvalidMoves();
			displayScores();
			displayTurn();
			showAvailableSpots();

		}
	
	}
	
	private void checkInvalidMoves() {
		
		if (reversiModel.getAvailableMovesSet().isEmpty()) {
			
			handlePass();
			
			if (reversiModel.getAvailableMovesSet().isEmpty()) {

				handleGameOver();
				
			}
			
		}
	}
	
	private void handlePass() {
		reversiModel.playerPass(reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());
		reversiView.setPassMessageVisible();
	}

	private void setAppropiateText() {
		if (reversiModel.getPlayerOne().getScore() == reversiModel.getPlayerTwo().getScore()) {
			reversiView.setTieText();
		}
		
		if (reversiModel.getPlayerOne().getScore() < reversiModel.getPlayerTwo().getScore()) {
			reversiView.setWinnerText(reversiModel.getPlayerTwo().getPiece());
		}
		
		if (reversiModel.getPlayerOne().getScore() > reversiModel.getPlayerTwo().getScore()) {
			reversiView.setWinnerText(reversiModel.getPlayerOne().getPiece());

		}
	}
	
	private void displayTurn() {
		if (reversiModel.getPlayerOne().getTurn()) {
			reversiView.setText(reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());
		}
		
		else {
			reversiView.setText(reversiModel.getPlayerTwo(), reversiModel.getPlayerOne());
		}
	}
	
	private void displayScores() {
		reversiView.setPlayerOneScore(reversiModel.getPlayerOneScore());
		reversiView.setPlayerTwoScore(reversiModel.getPlayerTwoScore());
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
	
	private void handleForfeit() {
    	isGameOver = true;
		reversiView.addGameOver();
		if (reversiModel.getPlayerOne().getTurn()) {
			reversiView.setWinnerText(reversiModel.getPlayerTwo().getPiece());
		}
		
		else  {
			reversiView.setWinnerText(reversiModel.getPlayerOne().getPiece());
		}
		animationHandler.gameOverAnimation(reversiView.getGameOver());
		initializeGameOverButton();
		resetIndicators();
	}
	
	private void handleGameOver() {
    	isGameOver = true;
		reversiView.addGameOver();
		setAppropiateText();
		animationHandler.gameOverAnimation(reversiView.getGameOver());
		initializeGameOverButton();
		resetIndicators();
	}
	
	private void resetGame() {
		
		resetIndicators();
		removeAllPieces();
		
	    reversiModel = new ReversiModel();
	    animationHandler = new AnimationHandler();
	    
	    reversiView.removeGameOver();
	    reversiView.setPassMessageNotVisible();
	    reversiView.setPlayerOneScore(reversiModel.getPlayerOneScore());
	    reversiView.setPlayerTwoScore(reversiModel.getPlayerTwoScore());
	    reversiView.setText(reversiModel.getPlayerOne(), reversiModel.getPlayerTwo());

	    init();
		isGameOver = false;
		
	}

	
	private void removeAllPieces() {
	    for (int rowIndex = 0; rowIndex < reversiModel.getBoardSize(); ++rowIndex) {
	        for (int colIndex = 0; colIndex < reversiModel.getBoardSize(); ++colIndex) {
	            reversiView.getSpot(rowIndex, colIndex).removePiece();
	        }
	    }
	}
	
	
	public Pane getPane() {
		return reversiView;
	}

}
