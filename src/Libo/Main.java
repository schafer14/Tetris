
public class Main
{
	public static void main( String[] args )
	{
		int[] a;
		for( int i=5, j, k ; i<=20 ; i++ ) // board width
		{
			for( j=1 ; j<=4 ; j++ ) // buffer size
			{
				for( k=1 ; k<=10 ; k++ )
				{
					a=new Game( new RandomInConsoleOut( 1000 ), i, j ).run();
					System.out.println( i + "," + j + "," + a[0] + "," + a[1] + "," + (a[0]-i*a[1]) );
				}
			}
		}
		/*int i=11, j=10;
		int[] a=new Game( new RandomInConsoleOut( 10000 ), i, j ).run();
		System.out.println( i + "," + j + "," + a[0] + "," + a[1] + "," + (a[0]-i*a[1]) );*/
	}
}
