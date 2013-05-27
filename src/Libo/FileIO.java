package Libo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.Channels;

/**
 * This class is an almost general purpose character I/O handler. There exists a small amount of task specific code in the load() method.
 * A note on encoding: There is no convenient way to automatically detect the right char set to use. Java.io.FileReader assumes system default charset.
 */
public class FileIO implements IO
{
	private FileInputStream is;
	private FileOutputStream os;
	private Reader r;
	private Writer w;
	private char[] a; // a=input buffer array
	private boolean[] v; // v=buffer validity flags
	private int c, l; // c=buffer cursor, l=effective buffer length
	
	/**
	 * @param i - input file name
	 * @param o - output file name
	 * @param l - length of input buffer
	 */
	public FileIO( String i, String o, int l )
	{
		try
		{
			is=new FileInputStream( i );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		try
		{
			os=new FileOutputStream( o );
		}
		catch( FileNotFoundException e )
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
		i=System.getProperty( "file.encoding" );
		r=Channels.newReader( is.getChannel(), i );
		w=Channels.newWriter( os.getChannel(), i );
		this.l=l;
		a=new char[l];
		v=new boolean[l];
		c=l;
	}
	
	/**
	 * This method loads the input buffer, do necessary transformation, and labels each element as valid or invalid.
	 * @return true if EOF is reached, false otherwise
	 */
	private boolean load()
	{
		try
		{
			l=r.read( a );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			close();
		}
		if( l==-1 )
			return true;
		// Code within the for loop is task specific. To generalize, introduce an interface with transform( int i ) method,	which returns the transformed value if i is accepted, and -1 otherwise.
		for( int i=0 ; i<l ; i++ )
		{
			if( a[i]<49 || a[i]>55 )
				v[i]=false;
			else
			{
				a[i]-=48;
				v[i]=true;
			}
		}
		c=0;
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
		if( l==-1 )
			return -1;
		while( true )
		{
			if( c==l )
			{
				if( load() )
					return -1;
			}
			if( !v[c] )
			{
				c++;
				continue;
			}
			return a[c++];
		}
	}
	
	/**
	 * This method writes a given String to the output file.
	 * The write operation is not buffered. This is to maximise the amount of data preserved when the potential crash happens.
	 * @param s - the String to write
	 */
	@Override
	public void write( String s )
	{
		try
		{
			w.write( s );
			w.flush();
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
