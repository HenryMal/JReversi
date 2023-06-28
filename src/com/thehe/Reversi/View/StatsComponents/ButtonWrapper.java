package com.thehe.Reversi.View.StatsComponents;

import com.thehe.Reversi.View.utils.ButtonCreator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ButtonWrapper extends HBox {

	private ButtonCreator forfeit;
	private DropShadow dropShadow;
	
	public ButtonWrapper(int fontSize) {
		
		initChildren(fontSize);
		
		createBackground(Color.web("#424242"));
		createDropShadow();
		setSpacing();
		
		setAlignment(Pos.CENTER);
		setSpacing(10);
		
		getChildren().add(forfeit);
		
	}
	
	private void createBackground(Color givenColor) {
		setBackground(new Background(new BackgroundFill(givenColor, new CornerRadii (6), Insets.EMPTY)));
	}
	
	private void createDropShadow() {
		dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.5, 0, 0);
		setEffect(dropShadow);
	}
	
	private void setSpacing() {
		setAlignment(Pos.CENTER);
		setSpacing(10);
		setPadding(new Insets(10, 25, 10, 25));
	}
	
	private void initChildren(int fontSize) {
		forfeit = new ButtonCreator("FORFEIT?", fontSize);
	}
	
	public ButtonCreator getForfeitButton() {
		return forfeit;
	}
}
