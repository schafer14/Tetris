import java.util.Arrays;

public class Test
{
	/**
	 * This mathod creates the enumeration of all possible rotations of the 7 standatd tetrimios.
	 * te[tetrimino][rotation][position][0]=lower bound (inclusive), .[1]=upper bound (exclusive)
	 * @return tetris enumerations
	 */
	public static int[][][][] makeTets()
	{
		int[][][][] te=new int[7][][][];
		int[] a={ 0, 1 }, b={ 0, 2 }, c={ 0, 3 }, d={ 1, 2 }, e={ 2, 3 }, f={ 1, 3 };
		te[0]=new int[2][][];
		te[0][0]=new int[1][];
		te[0][0][0]=new int[]{ 0, 4 };
		te[0][1]=new int[][]{ a, a, a, a };
		te[1]=new int[1][][];
		te[1][0]=new int[][]{ b, b };
		te[2]=new int[4][][];
		te[2][0]=new int[][]{ c, d };
		te[2][1]=new int[][]{ a, b, a };
		te[2][2]=new int[][]{ d, c };
		te[2][3]=new int[][]{ d, b, d };
		te[3]=new int[4][][];
		te[3][0]=new int[][]{ c, e };
		te[3][1]=new int[][]{ b, a, a };
		te[3][2]=new int[][]{ a, c };
		te[3][3]=new int[][]{ d, d, b };
		te[4]=new int[4][][];
		te[4][0]=new int[][]{ e, c };
		te[4][1]=new int[][]{ b, d, d };
		te[4][2]=new int[][]{ c, a };
		te[4][3]=new int[][]{ a, a, b };
		te[5]=new int[2][][];
		te[5][0]=new int[][]{ f, b };
		te[5][1]=new int[][]{ a, b, d };
		te[6]=new int[2][][];
		te[6][0]=new int[2][];
		te[6][0][0]=b;
		te[6][0][1]=new int[]{ 1, 3 };
		te[6][1]=new int[][]{ d, b, a };
		return te;
	}
	
	/**
	 * This method updates a board by putting a given tetrimino into a given position. Rows that become full are not eliminated.
	 * The input parameters are not altered.
	 * @param b - board
	 * @param t - tetrimino
	 * @param p - position of anchor point
	 * @return updated board
	 */
	public static boolean[][] update( boolean b[][], int t[][], int p )
	{
		int tw=t[0].length;
		b=boardCopy( b );
		t=tetCopy( t );
		int[] s=boardSurface( b, p, tw );
		tetAdd( t, max( s ) );
		while( !gapTest( s, t ) )
			tetAdd( t, -1 );
		if( max( t, 1 )>b.length )
			b=boardExt( b );
		for( int i=p+tw-1, j, l ; i>=p ; i-- )
		{
			for( j=t[i][0], l=t[i][1] ; j<l ; j++ )
				b[i][j]=true;
		}
		return b;
	}
	
	/**
	 * This methods tests whether a falling tetrimino has touched the ground
	 * The input parameters are not altered.
	 * @param s - surface
	 * @param t - tetrimino
	 * @return true if there is still a gap, false if the tetrimino has touched the ground
	 */
	public static boolean gapTest( int[] s, int[][] t )
	{
		int i, l=s.length;
		for( i=0 ; i<l ; i++ )
		{
			if( s[i]==t[i][0] )
				return false;
		}
		return true;
	}
	
	/**
	 * This method adds an int constant to every entries of a given 2D int array.
	 * The input array is altered.
	 * @param a - int array
	 * @param v - value
	 */
	public static void tetAdd( int[][] a, int v )
	{
		int w=a.length, i;
		for( i=0 ; i<w ; i++ )
		{
			a[i][0]+=v;
			a[i][1]+=v;
		}
	}
	
	/**
	 * This method returns the largest value in a given int array.
	 * The input parameter is not altered.
	 * @param a - int array
	 * @return largest value in the given array
	 */
	public static int max( int[] a )
	{
		int i, l=a.length, m=a[0];
		for( i=1 ; i<l ; i++ )
		{
			if( a[i]>m )
				m=a[i];
		}
		return m;
	}
	
	/**
	 * This method returns the largest value in the specified column of a given 2D int array.
	 * The input parameters are not altered.
	 * @param a - int array
	 * @param c - column number
	 * @return largest value in the specified column of the given array
	 */
	public static int max( int[][] a, int c )
	{
		int i, l=a.length, m=a[0][c];
		for( i=1 ; i<l ; i++ )
		{
			if( a[i][c]>m )
				m=a[i][c];
		}
		return m;
	}
	
	/**
	 * This method evaluates the surface of a board within a specified range.
	 * The input parameters are not altered.
	 * @param b - board
	 * @param f - from (inclusive)
	 * @param s - size
	 * @return an int array containing the height of each column (exclusive)
	 */
	public static int[] boardSurface( boolean[][] b, int f, int s )
	{
		int i, j, l=b.length-1;
		int[] a=new int[s];
		for( i=f+s-1 ; i>=f ; i-- )
		{
			for( j=l ; j>=0 ; j-- )
			{
				if( b[j][i] )
					break;
			}
			a[i]=j+1;
		}
		return a;
	}
	
	/**
	 * This method adds the height of a board by 5.
	 * The input parameter is not altered.
	 * @param b - source board
	 * @return extended board
	 */
	public static boolean[][] boardExt( boolean[][] b )
	{
		int h=b.length, w=b[0].length, i;
		boolean[][] a=Arrays.copyOf( b, h+5 );
		for( i=h, h+=10 ; i<h ; i++ )
			a[i]=new boolean[w];
		return a;
	}
	
	/**
	 * This method creates a deep copy of a board.
	 * The input parameter is not altered.
	 * @param b - source board
	 * @return copy of board
	 */
	public static boolean[][] boardCopy( boolean[][] b )
	{
		int h=b.length, w=b[0].length, i, j;
		boolean[][] a=new boolean[h][w];
		for( i=0 ; i<h ; i++ )
		{
			for( j=0 ; j<w ; j++ )
				a[i][j]=b[i][j];
		}
		return a;
	}
	
	/**
	 * This method creates a deep copy of a tetrimino.
	 * The input parameter is not altered.
	 * @param t - source tetrimino
	 * @return copy of tetrimino
	 */
	public static int[][] tetCopy( int[][] t )
	{
		int w=t.length, i;
		int[][] a=new int[w][2];
		for( i=0 ; i<w ; i++ )
		{
			a[i][0]=t[i][0];
			a[i][1]=t[i][1];
		}
		return a;
	}
}
