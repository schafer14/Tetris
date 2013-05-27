package Libo;
public class EvaluatorTest
{	
	public static void main( String[] args )
	{
		boolean[][] b=toBoolArr( new int[][]{
				{ 0,1,0,0,1,0  },
				{ 1,0,1,1,0,1 },
				{ 1,0,1,0,1,0 },
				{ 0,1,0,0,0,1 },
				{ 1,0,0,0,1,1 },
				{ 0,0,0,0,0,0 }} );
		int[] s={ 5,4,3,2,5,5 };
		Node n=new Node(b, s);
		Evaluator.mark( n );
	}
	
	public static boolean[][] toBoolArr( int[][] a )
	{
		int i, il=a.length, j, jl=a[0].length;
		boolean[][] b=new boolean[il][jl];
		int[] ar;
		boolean[] br;
		for( i=0 ; i<il ; i++ )
		{
			ar=a[i];
			br=b[i];
			for( j=0 ; j<jl ; j++ )
				br[j]=(ar[j]==1);
		}
		return b;
	}
}
