package com.thehe.Reversi;

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
		Scene scene = new Scene(pane);
		
		
		System.out.println("123");
		stage.setScene(scene);
		stage.setTitle("test");
		stage.show();
		
		
		
	}

}
