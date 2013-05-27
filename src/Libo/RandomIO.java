package Libo;
import java.util.Random;

public class RandomIO implements IO
{
	private Random r=new Random();
	
	@Override
	public int read()
	{
		return r.nextInt( 7 )+1;
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
