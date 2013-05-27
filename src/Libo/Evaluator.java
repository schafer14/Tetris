
public class Evaluator
{
	private static final double[] weights={ -6.995504718655336, -6.5540427782726915, -5.3661487440419116, -3.7024248993650226, 0.0, -15.03314189008874, -6.919180803194508 };
	// TODO insert real weights, and see if these values are different for different board width and different buffer size. If so, consider creating a 3d array.
	
	/**
	 * This method evaluates the fitness of a board.
	 * Note: It does not evaluate the fitness of a choice.
	 * For the definition of features, refer to:
	 * Tetris Agent Optimization Using Harmony Search Algorithm, V.M.Romero et al, 2011
	 * @param n - node
	 * @return fitness value
	 */
	public static float mark( Node n )
	{
		float mark=0;
		int[] a;
		int height=(int)n.mark;
		// n.mark stores the pile height of a board, which is added by Node.branch().
		// pile height is defined by feature 1 of REF.
		mark+=height*weights[0];
		a=colDiff( n );
		mark+=a[0]*weights[1];
		mark+=a[1]*weights[2];
		a=massAndHole( n, height );
		mark+=a[0]*weights[3];
		mark+=a[1]*weights[4];
		mark+=colTransition( n )*weights[5];
		mark+=rowTransition( n, height )*weights[6];
		return mark;
	}
	
	// TODO debugging only
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
	 * This method returns the average weighted blocks per column and holes of a board, defined by feature 10 & 2 of REF.
	 * Note: The feature weighted blocks is not divided by column in REF.
	 * @param n - node
	 * @return array { average weighted blocks per column, holes }
	 */
	private static int[] massAndHole( Node n, int height )
	{
		boolean[][] b=n.board;
		boolean[] r;
		int[] s=n.surface;
		int i, j, l=s.length, mass=0, hole=0;
		for( i=0 ; i<height ; i++ )
		{
			r=b[i];
			for( j=0 ; j<l ; j++ )
			{
				if( r[j] )
					mass+=i+1;
				else if( i<s[j] )
					hole++;
			}
		}
		return new int[]{ mass/l, hole };
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
