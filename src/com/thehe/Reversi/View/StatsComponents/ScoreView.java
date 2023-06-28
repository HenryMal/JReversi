package com.thehe.Reversi.View.StatsComponents;

import com.thehe.Reversi.Model.Piece;
import com.thehe.Reversi.View.BoardComponents.PieceView;
import com.thehe.Reversi.View.utils.TextCreator;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ScoreView extends HBox {
	
	private static final int WRAPPER_WIDTH = 100;
	
	private TextCreator text;
	private PieceView pieceView;
	private String count;
	
	public ScoreView (int fontSize, Piece givenPiece) {
		
		lockWidth(WRAPPER_WIDTH);
		setSpacing();
		initChildren(fontSize, givenPiece);
		
		getChildren().addAll(pieceView, text);
		
	}
	
	private void setSpacing() {
		setAlignment(Pos.CENTER);
		setSpacing(5);
	}
	
	private void lockWidth(int givenWidth) {
		setMaxWidth(givenWidth);
        setMinWidth(givenWidth); 
        setPrefWidth(givenWidth);
	}
	
	private void initChildren(int fontSize, Piece givenPiece) {
		count = " x ";
		pieceView = new PieceView(givenPiece);	
		text = new TextCreator(count + 0, fontSize, Color.WHITE);
		text.setWrappingWidth(55);
	}
	
	public void setScore(int givenScore) {
		text.setText(count + givenScore);
	}

}
