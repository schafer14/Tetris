
/**
 * Contains all the data relevant to the current state of the program
 * 
 * @version 1.0 15 May 2013
 * 
 * @author Banner B. Schafer
 * @author Libo Yin
 * 
 * @version 1.0
 */
public class State {
	
	//for storing the number of each pieces
	public static int [] buffer = new int [7];
	
	static int w = 11;
	
	//input/output file location
	static String input = "/home/banner/workspace/Tetris/input";
	static String output = "/home/banner/workspace/Tetris/output";
	
	//board keeps track of the highest block on each of the levels. 
	static int [] profile = new int [w];

	
}
