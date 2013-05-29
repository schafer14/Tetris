
public class Main
{
	public static void main( String[] args )
	{
		IO io=new FileIO( args[0], args[1], 1024, 100 );
		int boardWid=Integer.parseInt( args[2] ), bufSize=Integer.parseInt( args[3] );
		/*IO io=new RandomInConsoleOut( 10000 );
		int boardWid=10, bufSize=5;*/
		new Game( io, boardWid, bufSize ).run();
	}
}
