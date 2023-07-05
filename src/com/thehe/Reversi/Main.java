package com.thehe.Reversi;

import com.thehe.Reversi.Presenter.Presenter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		Pane pane = new Pane();
		Presenter presenter = new Presenter();
		
		pane.getChildren().add(presenter.getPane());
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("JReversi");
		stage.setResizable(false);
		stage.show();
		
		
		
	}

}
