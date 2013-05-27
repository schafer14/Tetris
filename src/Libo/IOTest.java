package Libo;
import java.util.Arrays;
import java.util.Random;

public class IOTest
{
	private static String in="test.in.txt", out="test.out.txt", nolt="test.nolt.txt";
	
	public static void main( String[] args )
	{
		rndFile();
		IO io=new FileIO( in, out, 1024 );
		int i, j, l=36;
		char[] a=new char[l];
		while( true )
		{
			for( i=0 ; i<l ; i++ )
			{
				j=io.read();
				if( j==-1 )
				{
					l=i;
					io.write( new String( Arrays.copyOf( a, l ) ) );
					io.close();
					return;
				}
				a[i]=(char)j;
			}
			io.write( new String( a ) );
		}
	}
	
	private static void rndFile()
	{
		IO io=new FileIO( nolt, in, 1024 );
		int i, j, l=5000;
		char[] a;
		Random r=new Random();
		a=new char[l];
		for( i=0 ; i<l ; i++ )
		{
			for( j=0 ; j<l ; j++ )
				a[j]=(char)( r.nextInt(93)+33 );
			io.write( new String( a ) );
		}
		io.close();
	}
}
