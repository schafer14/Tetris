import java.util.Arrays;

public class Node implements Comparable<Node>
{
	/**
	 * Game board. Cell board[row][column] is true if concrete, false otherwise, where row is from bottom to top, and column is from left to right.
	 */
	public boolean[][] board;
	
	/**
	 * Surface of game board. Cell surface[column] denotes the hight in a column (exclusive), where column is from left to right.
	 */
	public int[] surface;
	
	/**
	 * Tetrimino buffer. Cell buf[tet] denotes the number of tetrimino type tet, where tet is from 1 to tetTypes. Cell buf[0] is not used.
	 * Constant tetTypes is defined in class Game, denoting the total number of tetriminoes available.
	 */
	public int[] buf;
	
	/**
	 * Cell rootTet[0] denotes the tetrimino closen at the root expansion (the expansion from a physical node to a hypothetical one).
	 * Cell rootTet[1] denotes the rotation of the tetrimino.
	 * Cell rootTet[2] denotes the position of the anchor point of the tetrimino.
	 * Cell rootTet[3] denotes the number of eliminated rows caused by placing the tetrimino.
	 */
	public int[] rootTet;
	
	/**
	 * Depth denotes the remaining search depth allowed. Search is force stopped when depth==0 or timed out.
	 * Depth is always greater than or equal to the number of tetriminoes in the buffer. The equality holds when an available tetrimino is chosen at every steps.
	 */
	public int depth;
	
	/**
	 * Mark denotes the fitness of the board.
	 * Note: It does not denote the fitness of the choice of tetrimino. Such design is due to the fact that we can choose any tetriminoes in the buffer.
	 */
	public float mark;
	
	/**
	 * Root denotes the Node at the root expansion.
	 */
	public Node root;
	
	/**
	 * This initializer returns a new Node object with given board and surface. All other fields are set to either 0 or null.
	 * @param board - board
	 * @param surface - surface
	 */
	public Node( boolean[][] board, int[] surface )
	{
		this.board=board;
		this.surface=surface;
	}
	
	/**
	 * This method branches a node by placing a given tetrimino to a given position.
	 * Rows that become full are not eliminated, and the surface array is updated according to the uneliminated board.
	 * The pile height of board is stored in mark, which is to be updated by the evaluator.
	 * Branching is limited to board, surface array, and mark. Other fields of the returned node are set to either 0 or null.
	 * Such design is because an expansion of a physical node is handled differently with that of a hypothetical node.
	 * The input parameters are not altered.
	 * @param n - node
	 * @param tet - tetrimino
	 * @param pos - position of the anchor point
	 * @return branched node
	 */
	public Node branch( int[][] tet, int pos )
	{
		int i, j, l, max, tetWid=tet.length;
		boolean[][] b=board;
		int[] s=surface;
		// assume the tetrimino is at the bottom of board, find the number of rows that a tetrimino has to be lifted
		for( i=0, max=0 ; i<tetWid ; i++ )
		{
			j=s[pos+i]-tet[i][0];
			if( j>max )
				max=j;
		}
		s=Arrays.copyOf( s, s.length );
		// update surface
		for( i=0 ; i<tetWid ; i++ )
			s[pos+i]=tet[i][1]+max;
		// find pile height
		for( i=0, max=0, l=s.length ; i<l ; i++ )
		{
			j=s[i];
			if( j>max )
				max=j;
		}
		l=b.length;
		b=( max>l ) ? copyOf( b, l+5 ) : ( max<l-5 ? copyOf( b, l-5 ) : copyOf( b, l ) );
		// update the board
		for( i=tetWid-1 ; i>=0 ; i-- )
		{
			for( j=s[pos+i]-tet[i][1]+tet[i][0], l=s[pos+i] ; j<l ; j++ )
				b[j][pos+i]=true;
		}
		Node n=new Node( b, s );
		n.mark=max;
		return n;
	}
	
	/**
	 * This method eliminates all rows that are full in the board of a given node. The surface array of the node is also updated.
	 * The board and surface array of the given node are altered.
	 * @param n - node
	 * @return number of eliminated rows
	 */
	public int eliminate()
	{
		boolean[][] b=board;
		int[] s=surface;
		int i, j, height=b.length, width=s.length, count=0;
		LABEL:
		for( i=0 ; i<height ; i++ )
		{
			switch( identity( b[i] ) )
			{
				case -1: // a line of all false
					break LABEL;
				case 0: // a line of true and false
					continue;
				case 1: // a line of all true
					/* Consider revising: Currently, the board is moved down by 1 row each time a full row is detected.
					 * However, full rows must be consecutive, and the maximum number of such consecutive rows is 4.*/
					for( j=i, height-- ; j<height ; j++ )
						b[j]=b[j+1];
					b[height]=new boolean[width];
					count++;
					i--;
			}
		}
		// update surface array
		if( count>0 )
		{
			for( i=0 ; i<width ; i++ )
			{
				for( j=s[i]-count-1 ; j>=0 ; j-- )
				{
					if( b[j][i] )
						break;
				}
				s[i]=j+1;
			}
		}
		return count;
	}
	
	/** 
	 * This method implements the Comparable<Node> interface.
	 * Note: As this method only returns relative rank of elements, any sortings applied to Node instances should only use mutual comparisons.
	 * @return -1 if this.mark is smaller than n.mark, 0 if they are equal, and 1 if this.mark is larger than n.mark
	 */
	@Override
	public int compareTo( Node n )
	{
		if( mark-n.mark<0 )
			return -1;
		else
			return 1;
	}
	
	/**
	 * This method overrides the Object.toString() method.
	 * @return readable representation of the node
	 */
	@Override
	public String toString()
	{
		int i, j, l;
		StringBuilder sb=new StringBuilder();
		sb.append( "----------\nboard:\n" );
		for( i=board.length-1 ; i>=0 ; i-- )
		{
			for( j=0, l=surface.length ; j<l ; j++ )
				sb.append( board[i][j] ? 'x' : 'o' );
			sb.append( '\n' );
		}
		sb.append( "surface:\n" );
		sb.append( Arrays.toString( surface ) );
		sb.append( "\nbuffer:\n" );
		sb.append( Arrays.toString( buf ) );
		sb.append( "\ndepth=" );
		sb.append( depth );
		sb.append( "\nmark=" );
		sb.append( mark );
		sb.append( "\nrootTet:\n" );
		sb.append( Arrays.toString( rootTet ) );
		return sb.toString();
	}
	
	/**
	 * This method checks whether elements in a given boolean array are all true, all false, or not all the same.
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
			// if anything is different with the first element of the array
			if( f ^ a[i] )
				return 0;
		}
		return f?1:-1;
	}
	
	/**
	 * This method is an analogy to the Arrays.copyOf(), but for 2D boolean array with fixed width.
	 * The input parameters are not altered.
	 * @param original - original array
	 * @param l - length of copied array
	 * @return copied array
	 */
	private static boolean[][] copyOf( boolean[][] original, int newLength )
	{
		int i, j=original.length, width=original[0].length;
		boolean[][] a=new boolean[newLength][width];
		newLength=(j<newLength)?j:newLength;
		boolean[] ar, or; // row pointers for micro-optimisation
		for( i=0 ; i<newLength ; i++ )
		{
			ar=a[i];
			or=original[i];
			for( j=0 ; j<width ; j++ )
				ar[j]=or[j];
		}
		return a;
	}
}
