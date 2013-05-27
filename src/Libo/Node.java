import java.util.Arrays;

public class Node implements Comparable<Node>
{
	public boolean[][] board;
	public int[] buf, surface, rootTet;
	public int depth;
	public float mark;
	public Node root;
	
	public Node( boolean[][] board, int[] surface )
	{
		this.board=board;
		this.surface=surface;
	}
	
	@Override
	public int compareTo( Node n )
	{
		if( mark-n.mark<0 )
			return -1;
		else
			return 1;
	}
	
	/**
	 * This method branches a node by placing a given tetrimino to a given position.
	 * Branching is limited to the board and the surface array. Other fields of the returned node are set to either 0 or null.
	 * Rows that become full are not eliminated.
	 * The input parameters are not altered.
	 * @param n - node
	 * @param tet - tetrimino
	 * @param pos - position of the anchor point
	 * @return branched node
	 */
	public static Node branch( Node n, int[][] tet, int pos )
	{
		int i, j, l, max, tetWid=tet.length;
		boolean[][] board=n.board;
		int[] surface=n.surface;
		for( i=0, max=0 ; i<tetWid ; i++ )
		{
			j=surface[pos+i]-tet[i][0];
			if( j>max )
				max=j;
		}
		tet=copyOf( tet, tetWid );
		for( i=0 ; i<tetWid ; i++ )
		{
			tet[i][0]+=max;
			tet[i][1]+=max;
		}
		for( i=0, max=0 ; i<tetWid ; i++ )
		{
			j=tet[i][1];
			if( j>max )
				max=j;
		}
		l=board.length;
		board=( max>l ) ? copyOf( board, l+5 ) : ( max<l-5 ? copyOf( board, l-5 ) : copyOf( board, l ) );
		surface=Arrays.copyOf( surface, surface.length );
		for( i=pos+tetWid-1 ; i>=pos ; i-- )
		{
			for( j=tet[i][0], l=tet[i][1] ; j<l ; j++ )
				board[i][j]=true;
			surface[i]=l;
		}
		return new Node( board, surface );
	}
	
	/**
	 * This method eliminates all rows that are full in the board of a given node. The surface array of the node is also updated.
	 * The board and surface array of the given node are altered.
	 * @param n - node
	 * @return number of eliminated rows
	 */
	public static int eliminate( Node n )
	{
		boolean[][] b=n.board;
		int[] s=n.surface;
		int i, j, height=b.length, width=s.length, count=0;
		LABEL:
		for( i=0 ; i<height ; i++ )
		{
			switch( identity( b[i] ) )
			{
				case -1:
					break LABEL;
				case 0:
					continue;
				case 1:
					// Consider revising: Currently, the board is moved down each time a full row is detected.
					// However, full rows must be consecutive, and the maximum number of such consecutive rows is 4.
					for( j=i, height-- ; j<height ; j++ )
						b[j]=b[j+1];
					b[height]=new boolean[width];
					count++;
					i--;
			}
		}
		for( i=0 ; i<width ; i++ )
			s[i]-=count;
		return count;
	}
	
	/**
	 * This methods check whether elements in a given boolean array are all true, all false, or not all the same.
	 * The input parameters are not altered.
	 * @param a - boolean array
	 * @return 1 if all true, -1 if all false, 0 if not all the same  
	 */
	public static int identity( boolean[] a )
	{
		int i, l=a.length;
		boolean f=a[0];
		for( i=1 ; i<l ; i++ )
		{
			if( f ^ a[i] )
				return 0;
		}
		return f?1:-1;
	}
	
	/**
	 * This method is an analogy to the Arrays.copyOf(), but for 2D boolean array with fixed width.
	 * The input parameters are not altered.
	 * @param s - source array
	 * @param l - length of copied array
	 * @return copied array
	 */
	private static boolean[][] copyOf( boolean[][] s, int l )
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
	private static int[][] copyOf( int[][] s, int l )
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
