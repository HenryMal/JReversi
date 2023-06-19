package com.thehe.Reversi.View.utils;

import com.thehe.Reversi.View.AvailableMovesView;
import com.thehe.Reversi.View.PieceView;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class AnimationHandler {
	
	private ParallelTransition animateIndicators;
	private ScaleTransition spawnPiece;
	
	public AnimationHandler() {
		animateIndicators = new ParallelTransition();
		spawnPiece = new ScaleTransition();
	}
	
	public void addFadeAnimation(AvailableMovesView givenIndicator) {
		
		FadeTransition fadeIndicator = new FadeTransition(Duration.millis(1000), givenIndicator);
		fadeIndicator.setFromValue(1.0);
		fadeIndicator.setToValue(0.1);
		fadeIndicator.setCycleCount(Animation.INDEFINITE);
		fadeIndicator.setAutoReverse(true);
		
		animateIndicators.getChildren().add(fadeIndicator);
		
	}
	
	public void addSpawnAnimation(PieceView givenPiece) {
		
		spawnPiece.setNode(null);
		
		spawnPiece.setNode(givenPiece);
		spawnPiece.setDuration(Duration.millis(125));
		spawnPiece.setToX(1.0);
		spawnPiece.setToY(1.0);
		spawnPiece.setInterpolator(Interpolator.EASE_OUT);
		
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
	
	public ScaleTransition getSpawnAnimation() {
		return spawnPiece;
	}

}
