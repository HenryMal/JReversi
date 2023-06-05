package com.thehe.Reversi.Model;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		
		Board board = new Board();
		
		System.out.println(board.toString());
		
		long currentTime = System.nanoTime();
		
		System.out.println((currentTime - lastTime) / 1000000000.0);


	}

}
