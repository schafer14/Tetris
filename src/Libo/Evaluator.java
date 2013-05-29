
public class Evaluator
{
	public static double[] weights={ -5.299385964268184, -6.561291591578163, -10.0, -6.806243294025367 };
	
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
		// n.mark stores the pile height of a board, generated by Node.branch().
		mark+=height*weights[0];
		mark+=altDiff( n )*weights[1];
		mark+=colTransition( n )*weights[2];
		mark+=rowTransition( n, height )*weights[3];
		return mark;
	}
	
	/**
	 * This method returns the pile height of a board, defined by feature 1 of REF.
	 * @param n - node
	 * @return pile height
	 */
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
	 * This method returns the summed altitude difference of a board, defined by feature 5 of REF.
	 * @param n - node
	 * @return altitude difference
	 */
	private static int altDiff( Node n )
	{
		int[] s=n.surface;
		int i, j, l=s.length-1, sum=0;
		for( i=0 ; i<l ; i++ )
		{
			j=s[i]-s[i+1];
			if( j<0 )
				j=-j;
			sum+=j;
		}
		return sum;
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
