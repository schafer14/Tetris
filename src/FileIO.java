import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		Scanner s = null;
		try {
			s = new Scanner(new File(input));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (s.hasNext())
		{
			int piece = Integer.parseInt(s.next());
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
		
		Helper.printBufferContents();
		
		return;
	}

}
