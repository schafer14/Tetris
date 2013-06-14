
public class Main
{
	public static void main( String[] args )
	{
		/* arg[0]=input file name
		 * arg[1]=output file name
		 * arg[2]=board width
		 * arg[3]=buffer size
		 */
		IO io=new FileIO( args[0], args[1], 1024, 100 );
		int boardWid=Integer.parseInt( args[2] ), bufSize=Integer.parseInt( args[3] );
		
		//IO io=new FileIO( "test.in.txt", "test.out.txt", 1024, 100 );
		//IO io=new FileIO( "equal-1000.txt", "test.out.txt", 1024, 100 );
		//IO io=new RandomInConsoleOut( 10000 );
		//IO io=new RandomInFileOut( 1000, "test.out.txt" );
		//int boardWid=11, bufSize=1;
		
		new Game( io, boardWid, bufSize ).run();
		io.close();
	}
}
