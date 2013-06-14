import java.io.FileWriter;
import java.io.IOException;

public class RandomInFileOut extends RandomInConsoleOut
{
	private FileWriter w;
	
	public RandomInFileOut( long cutOff, String outputFileName )
	{
		super( cutOff );
		try
		{
			w=new FileWriter( outputFileName );
		}
		catch( IOException e )
		{
			e.printStackTrace();
			close();
		}
	}
	
	@Override
	public void write( String s )
	{
		synchronized( w )
		{
			try
			{
				w.write( s );
			}
			catch( IOException e )
			{
				e.printStackTrace();
				close();
			}
		}
	}
	
	@Override
	public void close()
	{
		try
		{
			w.flush();
			w.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
}
