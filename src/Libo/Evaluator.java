
public class Evaluator
{
	private static final float[] weights={}; // TODO insert real weights!
	
	public static float mark( Node n )
	{
		float mark=0;
		int[] a;
		int height=pileHeight( n );
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
	
	private static int pileHeight( Node n )
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
