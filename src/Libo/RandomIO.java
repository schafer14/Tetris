import java.util.Random;

public class RandomIO implements IO
{
	private Random r=new Random();
	private long cutOff;
	
	public RandomIO( long cutOff )
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
		System.out.print( s );
	}

	@Override
	public void close()
	{
		r=null;
	}
}
