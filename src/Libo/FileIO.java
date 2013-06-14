import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.Channels;

public class FileIO implements IO
{
	private FileInputStream is;
	private FileOutputStream os;
	private Reader r;
	private Writer w;
	private char[] ib; // ib=input buffer
	private boolean[] iv; // v=input buffer validity flags
	private int ic, il; // ic=input buffer cursor; il=effective input buffer length
	
	/**
	 * This initializer returns a new FileIO object.
	 * @param inputFileName - input file name
	 * @param outputFileName - output file name
	 * @param inputBufferSize - input buffer size
	 */
	public FileIO( String inputFileName, String outputFileName, int inputBufferSize )
	{
		try
		{
			is=new FileInputStream( inputFileName );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		File f=new File( outputFileName );
		try
		{
			f.createNewFile();
			os=new FileOutputStream( f );
		}
		catch( IOException e ) // for JDK 1.7 or above: catch( IOException | SecurityException e )
		{
			e.printStackTrace();
			try
			{
				is.close();
			}
			catch( IOException e1 )
			{
				e1.printStackTrace();
			}
		}
		String e=System.getProperty( "file.encoding" );
		r=Channels.newReader( is.getChannel(), e );
		w=Channels.newWriter( os.getChannel(), e );
		il=inputBufferSize;
		ib=new char[inputBufferSize];
		iv=new boolean[inputBufferSize];
		ic=inputBufferSize; // this line ensures that the buffer is loaded at the first time read() is called
	}
	
	/**
	 * This method loads the input buffer, do necessary transformation, and labels each element as valid or invalid.
	 * @return true if EOF is reached, false otherwise
	 */
	private boolean load()
	{
		try
		{
			il=r.read( ib );
		}
		catch( IOException e )
		{
			e.printStackTrace();
			close();
		}
		if( il==-1 )
			return true;
		// Code within the for loop is task specific. This is the only instance of task specific code in class FileIO.
		// To generalize, introduce an interface with transform( int i ) method, which returns the transformed value if i is accepted, and -1 otherwise.
		for( int i=0 ; i<il ; i++ )
		{
			if( ib[i]<49 || ib[i]>55 )
				iv[i]=false;
			else
			{
				ib[i]-=48;
				iv[i]=true;
			}
		}
		ic=0;
		return false;
	}
	
	/**
	 * This method returns the next valid input. The definition of a valid input is defined in the load() method.
	 * The read operation is buffered.
	 * @return the next valid input, or -1 if EOF is reached
	 */
	@Override
	public int read()
	{
		while( true )
		{
			if( ic==il || il==-1 )
			{
				if( load() )
					return -1;
			}
			if( !iv[ic] )
			{
				ic++;
				continue;
			}
			return ib[ic++];
		}
	}
	
	/**
	 * This method writes a given String to the output file. No EOL is appended after the String.
	 * The write operation is not buffered.
	 * @param s - the String to write
	 */	
	@Override
	public void write( String s )
	{
		try
		{
			w.write(s);
		}
		catch( IOException e )
		{
			e.printStackTrace();
			close();
		}
	}
	
	/**
	 * This method closes the FileInputStream and the FileOutputStream.
	 * This method should be explicitely called after all IO operations are finished.
	 */
	@Override
	public void close()
	{
		try
		{
			w.flush();
			is.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch( IOException e )
			{
				e.printStackTrace();
			}
		}
	}
}
