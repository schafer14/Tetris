import java.util.Random;

public class RandomInConsoleOut implements IO
{
	private Random r=new Random();
	private long cutOff;
	
	public RandomInConsoleOut( long cutOff )
	{
		this.cutOff=cutOff+System.currentTimeMillis();
	}
	
	@Override
	public int read()
	{
		if( System.currentTimeMillis()<cutOff )
			return r.nextInt( 7 )+1;
		else
			return -1;
	}
	
	@Override
	public void write( String s )
	{
		synchronized( r )
		{
			System.out.print( s );
		}
	}
	
	@Override
	public void close()
	{
		r=null;
	}
}
