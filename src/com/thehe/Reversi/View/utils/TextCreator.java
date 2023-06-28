package com.thehe.Reversi.View.utils;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TextCreator extends Text {
	
	private Font font;
	
	public TextCreator (String givenText, int givenSize, Color fontColor) {
		
		font = Font.font("SF Pro", FontWeight.BOLD, givenSize);
		
		setFont(font);
		setText(givenText);
		setSmooth(true);
		setFill(fontColor);
		
	}

}
