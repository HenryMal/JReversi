package com.thehe.Reversi.View.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ButtonCreator extends HBox {
	
	private TextCreator text;

	public ButtonCreator(String givenText, int fontSize) {
		
		text = new TextCreator(givenText, fontSize, Color.WHITE);
		setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(6), Insets.EMPTY)));
		
		setAlignment(Pos.CENTER);
		setMaxWidth(30);
		setMargin(text, new Insets(5, 15, 5, 15));
		
		getChildren().add(text);
		
	}

}