package Libo;
public interface IO
{	
	/**
	 * This method returns the next valid input.
	 * @return the next valid input, or -1 if input has ended
	 */
	public int read();
	
	/**
	 * This method writes a given String to the output.
	 * @param s - the String to write
	 */
	public void write( String s );
	
	/**
	 * This method should be explicitely called after all IO operations are finished.
	 */
	public void close();
}
