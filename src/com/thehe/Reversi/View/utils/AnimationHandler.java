package com.thehe.Reversi.View.utils;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.View.components.AvailableMovesView;
import com.thehe.Reversi.View.components.PieceView;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

public class AnimationHandler {
	
	private ParallelTransition animateFlippedPieces;
	private ParallelTransition animateIndicators;
	private ScaleTransition spawnPiece;

	
	public AnimationHandler() {
		animateIndicators = new ParallelTransition();
		spawnPiece = new ScaleTransition();
		animateFlippedPieces = new ParallelTransition();
		
	}
	
	public void addFadeAnimation(AvailableMovesView givenIndicator) {
		
		FadeTransition fadeIndicator = new FadeTransition(Duration.millis(1000), givenIndicator);
		fadeIndicator.setFromValue(0.1);
		fadeIndicator.setToValue(1.0);
		fadeIndicator.setCycleCount(Animation.INDEFINITE);
		fadeIndicator.setAutoReverse(true);
		
		animateIndicators.getChildren().add(fadeIndicator);
		
	}
	
	public void addFlippingAnimation(PieceView givenPieceView, Piece givenPiece) {
		
	    ScaleTransition flipAnimationPartOne = new ScaleTransition(Duration.millis(100), givenPieceView);
	    setScaleTransitionSettings(flipAnimationPartOne, 1.0, 0);
	    
	    flipAnimationPartOne.setOnFinished(event -> {
	    	givenPieceView.setColor(givenPiece);
	    });
	    
	    ScaleTransition flipAnimationPartTwo = new ScaleTransition(Duration.millis(100), givenPieceView);
	    setScaleTransitionSettings(flipAnimationPartTwo, 0, 1.0);

	    SequentialTransition flipping = new SequentialTransition(flipAnimationPartOne, flipAnimationPartTwo);
		animateFlippedPieces.getChildren().add(flipping);

	}
	
	private void setScaleTransitionSettings(ScaleTransition animation, double setFrom, double setTo) {
		animation.setFromX(setFrom);
		animation.setToX(setTo);
		animation.setInterpolator(Interpolator.EASE_BOTH);
	}
	
	public void addSpawnAnimation(PieceView givenPiece) {
		
		spawnPiece.setNode(givenPiece);
		spawnPiece.setDuration(Duration.millis(125));
		spawnPiece.setToX(1.0);
		spawnPiece.setToY(1.0);
		spawnPiece.setInterpolator(Interpolator.EASE_OUT);
		
	}
	
	public void interruptSpawnAnimation() {
		Duration totalDuration = spawnPiece.getTotalDuration();
		spawnPiece.jumpTo(totalDuration); 
	}
	
	public void interruptFlippingAnimation() {
		Duration totalDuration = animateFlippedPieces.getTotalDuration();
		animateFlippedPieces.jumpTo(totalDuration); 
	}
	
	public void playSpawnAnimation() {
		spawnPiece.play();
	}
	
	public void playAnimateIndicators() {
		animateIndicators.play();
	}
	
	public void clearAnimaiteIndicators() {
		animateIndicators.stop();
		animateIndicators.getChildren().clear();
	}
	
	public void clearFlipAnimation() {
		animateFlippedPieces.getChildren().clear();
	}
	
	public void playFlippingAnimation() {
		animateFlippedPieces.play();
	}
	
	public ScaleTransition getSpawnAnimation() {
		return spawnPiece;
	}
	
	public ParallelTransition getFlippingAnimation() {
		return animateFlippedPieces;
	}

}
