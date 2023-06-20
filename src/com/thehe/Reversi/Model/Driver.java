package com.thehe.Reversi.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		
		ReversiModel board = new ReversiModel();
		
		System.out.println(board.toString());
		
		long currentTime = System.nanoTime();
		
		System.out.println((currentTime - lastTime) / 1000000000.0);
		
		List<int[]> debug = new LinkedList<int[]>();
		
		debug.add(new int[] {2, 3} );
		debug.add(new int[] {2, 3} );
		debug.add(new int[] {2, 3} );
		debug.add(new int[] {2, 3} );
		
		debug.add(new int[] {2, 5} );
		debug.add(new int[] {2, 5} );
		debug.add(new int[] {2, 5} );
		
		debug.subList(3 + 2, debug.size()).clear();
		debug.forEach(array -> System.out.println(Arrays.toString(array)));

		
	}

}
