import java.io.*;
import java.util.*;

/**
 * The FileIO is a library for abstraction of all the file input output and parsing functions
 * 
 * @version 1.0 15 May 2013
 * 
 * @author Banner B. Schafer
 * @author Libo Yin
 * 
 */

public class FileIO {
	//for storing the number of each pieces
	public static int [] buffer = new int [7];
	
	/**
	 * This file reads from input file to buffer
	 * @author Banner B. Schafer
	 * @version 1.0
	 * 
	 */
	static void readInput (String input) 
	{
		Scanner in = null;
		try {
			in = new Scanner(new File(input));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (in.hasNext())
		{
			int piece = Integer.parseInt(in.next());
			switch (piece) 
			{
				case 0:
					buffer[0] ++;
					break;
				case 1:
					buffer[1] ++;
					break;
				case 2:
					buffer[2] ++;
					break;
				case 3:
					buffer[3] ++;
					break;
				case 4:
					buffer[4] ++;
					break;
				case 5:
					buffer[5] ++;
					break;
				case 6:
					buffer[6] ++;
					break;
				default:
					//do nothing
					break;
			}
		}
				
		return;
	}
	
	/**
	 * Writes a move to the output file
	 * @author Banner B. Schafer
	 * @version 1.0
	 * 
	 * @param piece Which piece is being played (0-6)
	 * @param rotation How far it's been rotated clockwise * 90 (0-3)
	 * @param xDist How far from the left wall it's been moved (0 .. )
	 * @param output The file which to print to
	 */
	static void writeOut(int piece, int rotation, int xDist, PrintWriter out)
	{
		out.write(piece + " " + rotation + " " + xDist + "\n");

		return;
	}
	
	/**
	 * Prepares input and output files for useage
	 * @author Banner B. Schafer
	 * @version 1.0
	 * 
	 */
	static void validateIOFiles(String input, String output)
	{
		File in = new File (input);
		File out = new File (output);
		
		if (! out.exists())
		{
			try {
				out.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (! in.exists()) 
		{
			System.out.println("The input file could not be found");
		}
		
		return;
	}
	
	
	/**
	 * Creates a PrintWriter for the Driver
	 * @author Banner B. Schafer
	 * @version 1.0
	 * 
	 */
	static PrintWriter outPW(String output)
	{
		PrintWriter o = null;
		
		try {
			o = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}


}
