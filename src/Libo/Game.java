import java.util.Arrays;

public class Game
{
	// draft
	public void run()
	{
		int bw=11;
		SyncMaxHeap<Node> h=new SyncMaxHeap<>();
		int[][][][] te=Main.te;
		Node n=h.head();
		int i, j, jl, k, kl;
		for( i=1 ; i<8 ; i++ )
		{
			for( j=0, jl=te[i].length ; j<jl ; j++ )
			{
				for( k=0, kl=bw-te[i][j].length ; k<kl ; k++ )
					h.add( fork( n, i, j, k ) );
				// search depth control: min( remaining tetriminos in the buffer, search level )
			}
		}
	}
	
	/**
	 * This method updates a board by putting a given tetrimino into a given position. Rows that become full are not eliminated.
	 * The input parameters are not altered.
	 * @param n - node
	 * @param ti - tetrimino index
	 * @param i - tetrimino rotation
	 * @param p - position of anchor point
	 * @return new board
	 */
	public static Node fork( Node n, int i, int j, int p )
	{
		float v;
		int[] a=Arrays.copyOf( n.a, n.a.length );
		if( a[i]==0 )
			v=(float)1/7;
		else
		{
			v=1F;
			a[i]--;
		}
		int[][] t=Main.te[i][j];
		int m, l, w=t.length; // w=tetrimino width
		t=copyOf( t, w );
		boolean[][] b=n.b;
		int[] s=n.s;
		for( i=1, j=s[p]-t[0][0], m=j>0?j:0 ; i<w ; i++ )
		{
			j=s[p+i]-t[i][0];
			if( j>m )
				m=j;
		}
		for( i=0 ; i<w ; i++ )
		{
			t[i][0]+=m;
			t[i][1]+=m;
		}
		for( i=1, m=t[0][1] ; i<w ; i++ )
		{
			j=t[i][1];
			if( j>m )
				m=j;
		}
		l=b.length;
		b=( m>l ) ? copyOf( b, l+5 ) : ( m<l-5 ? copyOf( b, l-5 ) : copyOf( b, l ) );
		s=Arrays.copyOf( s, s.length );
		for( i=p+w-1 ; i>=p ; i-- )
		{
			for( j=t[i][0], l=t[i][1] ; j<l ; j++ )
				b[i][j]=true;
			s[i]=l;
		}
		v*=evaluate( b, s ); // must not be merged into the nect line, as evaluate() changes its args
		return new Node( b, a, s, n.d-1, v );
	}
	
	public static float evaluate( boolean[][] b, int[] s )
	{
		float[] ew=Main.ew;
		float v=0;
		return v;
	}
	
	/**
	 * This method is an analogy to the Arrays.copyOf(), but for 2D boolean array with fixed width.
	 * The input parameters are not altered.
	 * @param s - source array
	 * @param l - length of copied array
	 * @return copied array
	 */
	public static boolean[][] copyOf( boolean[][] s, int l )
	{
		int i, j=s.length, il=s[0].length;
		l=(j<l)?j:l;
		boolean[][] a=new boolean[l][il];
		boolean[] ar, sr;
		for( i=0 ; i<l ; i++ )
		{
			ar=a[i];
			sr=s[i];
			for( j=0 ; j<il ; j++ )
				ar[j]=sr[j];
		}
		return a;
	}
	
	/**
	 * This method is an analogy to the Arrays.copyOf(), but for 2D int array with fixed width.
	 * The input parameters are not altered.
	 * @param s - source array
	 * @param l - length of copied array
	 * @return copied array
	 */
	public static int[][] copyOf( int[][] s, int l )
	{
		int i, j=s.length, il=s[0].length;
		l=(j<l)?j:l;
		int[][] a=new int[l][il];
		int[] ar, sr;
		for( i=0 ; i<l ; i++ )
		{
			ar=a[i];
			sr=s[i];
			for( j=0 ; j<il ; j++ )
				ar[j]=sr[j];
		}
		return a;
	}
}
