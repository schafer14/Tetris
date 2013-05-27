import java.util.Random;

public class RandomIO implements IO
{
	private Random r=new Random();
	private long cutOff=60000000;
	
	public RandomIO()
	{
		cutOff+=System.nanoTime();
	}
	
	@Override
	public int read()
	{
		if( System.nanoTime()<cutOff )
			return r.nextInt( 7 )+1;
		else
			return -1;
	}

	@Override
	public void write( String s )
	{
		System.out.println( s );
	}

	@Override
	public void close()
	{
		r=null;
	}
}
