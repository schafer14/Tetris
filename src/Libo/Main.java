package Libo;
public class Main
{	
	// te=tetrimino enumeration. te[tetrimino][rotation][position][0]=lower bound (inclusive); .[1]=upper bound (exclusive). te[0] is not used.
	public static final int[][][][] te=new int[8][][][]; 
	// ew=evaluation weight vector. TODO ew[0]=; ew[1]=; ew[2]=; ew[3]=; ew[4]=
	public static final float[] ew={ 0, 0, 0, 0, 0 };
	
	
	static
	{
		int[] a={ 0, 1 }, b={ 0, 2 }, c={ 0, 3 }, d={ 1, 2 }, e={ 2, 3 }, f={ 1, 3 };
		te[1]=new int[2][][];
		te[1][0]=new int[1][];
		te[1][0][0]=new int[]{ 0, 4 };
		te[1][1]=new int[][]{ a, a, a, a };
		te[2]=new int[1][][];
		te[2][0]=new int[][]{ b, b };
		te[3]=new int[4][][];
		te[3][0]=new int[][]{ c, d };
		te[3][1]=new int[][]{ a, b, a };
		te[3][2]=new int[][]{ d, c };
		te[3][3]=new int[][]{ d, b, d };
		te[4]=new int[4][][];
		te[4][0]=new int[][]{ c, e };
		te[4][1]=new int[][]{ b, a, a };
		te[4][2]=new int[][]{ a, c };
		te[4][3]=new int[][]{ d, d, b };
		te[5]=new int[4][][];
		te[5][0]=new int[][]{ e, c };
		te[5][1]=new int[][]{ b, d, d };
		te[5][2]=new int[][]{ c, a };
		te[5][3]=new int[][]{ a, a, b };
		te[6]=new int[2][][];
		te[6][0]=new int[][]{ f, b };
		te[6][1]=new int[][]{ a, b, d };
		te[7]=new int[2][][];
		te[7][0]=new int[2][];
		te[7][0][0]=b;
		te[7][0][1]=new int[]{ 1, 3 };
		te[7][1]=new int[][]{ d, b, a };
	}
	
	public static void main( String[] args )
	{

	}
}
