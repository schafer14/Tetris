
public class Evaluator
{
	public static double[] weights={-5.299385964268184, -6.561291591578163, 0.0, 0.0, -10.0, -6.806243294025367};
	// TODO see if these values are different for different board width and different buffer size. If so, consider creating a 3d array.
	
	/**
	 * This method evaluates the fitness of a board.
	 * For the definition of features, refer to:
	 * Tetris Agent Optimization Using Harmony Search Algorithm, V.M.Romero et al, 2011
	 * @param n - node
	 * @return fitness value
	 */
	public static float mark( Node n )
	{
		float mark=0;
		int height=(int)n.mark;
		// n.mark stores the pile height of a board, which is added by Node.branch().
		// pile height is defined by feature 1 of REF.
		mark+=height*weights[0];
		int[] a=colDiff( n );
		mark+=a[0]*weights[1];
		mark+=a[1]*weights[2];
		mark+=mass( n, height )*weights[3];
		mark+=colTransition( n )*weights[4];
		mark+=rowTransition( n, height )*weights[5];
		return mark;
	}
	
	// TODO for debugging only
	public static int pileHeight( Node n )
	{
		int[] s=n.surface;
		int i, j, l=s.length, max=0;
		for( i=0 ; i<l ; i++ )
		{
			j=s[i];
			if( j>max )
				max=j;
		}
		return max;
	}
	
	/**
	 * This method returns the altitude difference and maximum well depth of a board, defined by feature 5 & 6 of REF.
	 * @param n - node
	 * @return array { altitude difference, maximum well depth }
	 */
	private static int[] colDiff( Node n )
	{
		int[] s=n.surface;
		int i, j, l=s.length-1, sum=0, max=0;
		for( i=0 ; i<l ; i++ )
		{
			j=s[i]-s[i+1];
			if( j<0 )
				j=-j;
			sum+=j;
			if( j>max )
				max=j;
		}
		return new int[]{ sum, max };
	}
	
	/**
	 * This method returns the average mass per column, which is similar to the feature weighted blocks defined by feature 10 of REF, but:
	 * 1. is divided by number of culumns, and 2. has row weight (row+1)^2 instead of (row+1)
	 * @param n - node
	 * @return average mass per column
	 */
	private static int mass( Node n, int height )
	{
		boolean[][] b=n.board;
		boolean[] r;
		int[] s=n.surface;
		int i, j, l=s.length, mass=0;
		for( i=0 ; i<height ; i++ )
		{
			r=b[i];
			for( j=0 ; j<l ; j++ )
			{
				if( r[j] )
					mass+=(i+1)*(i+1);
			}
		}
		return mass/l;
	}
	
	/**
	 * This method returns the column transition of a board, defined by feature 12 of REF.
	 * @param n - node
	 * @return column transition
	 */
	private static int colTransition( Node n )
	{
		boolean[][] b=n.board;
		int[] s=n.surface;
		int i, il=s.length, j, l, count=0;
		for( i=0 ; i<il ; i++ )
		{
			for( j=0, l=s[i]-1 ; j<l ; j++ )
			{
				if( b[j][i] ^ b[j+1][i] )
					count++;
			}
		}
		return count;
	}
	
	/**
	 * This method returns the row transition of a board, defined by feature 11 of REF.
	 * @param n - node
	 * @return row transition
	 */
	private static int rowTransition( Node n, int height )
	{
		boolean[][] b=n.board;
		int[] s=n.surface;
		boolean[] r;
		int i, j, l=s.length-1, count=0;
		for( i=0 ; i<height ; i++ )
		{
			r=b[i];
			for( j=0 ; j<l ; j++ )
			{
				if( i<s[j] && i<s[j+1] && (r[j]^r[j+1]) )
					count++;
			}
		}
		return count;
	}
}

/*private static final double[] weights={
	(float)-6.995504718655336, // pile height
	(float)-6.5540427782726915, // altitude difference
	(float)-5.3661487440419116, // maximum well depth
	(float)-3.7024248993650226, // average weighted blocks
	(float)-15.03314189008874, // column transition
	(float)-6.919180803194508 }; // row transition*/
