
public class Evaluator
{
	private static final int f=7; // f=features
	private static final float[] ew=new float[f]; // ew=evaluation weights TODO insert real weights!
	
	public static float mark( Node n )
	{
		int[] e=new int[f], a;
		int h=pileHeight( n );
		e[0]=h;
		a=colDiff( n );
		e[1]=a[0];
		e[2]=a[1];
		a=weightAndHole( n, h );
		e[3]=a[0];
		e[4]=a[1];
		e[5]=colTransition( n, h );
		e[6]=rowTransition( n, h );
		float v=0;
		for( h=0 ; h<f ; h++ )
			v+=ew[h]*e[h];
		return v;
	}
	
	private static int pileHeight( Node n )
	{
		int[] s=n.s;
		int i, j, l=s.length, m=0;
		for( i=0 ; i<l ; i++ )
		{
			j=s[i];
			if( j>m )
				m=j;
		}
		System.out.println( m );
		return m;
	}
	
	private static int[] colDiff( Node n )
	{
		int[] s=n.s;
		int i, j, l=s.length-1, t=0, m=0;
		for( i=0 ; i<l ; i++ )
		{
			j=s[i]-s[i+1];
			if( j<0 )
				j=-j;
			t+=j;
			if( j>m )
				m=j;
		}
		System.out.println( t );
		System.out.println( m );
		return new int[]{ t, m };
	}
	
	private static int[] weightAndHole( Node n, int h )
	{
		boolean[][] b=n.b;
		boolean[] r;
		int[] s=n.s;
		int i, j, l=s.length, w=0, c=0;
		for( i=0 ; i<h ; i++ )
		{
			r=b[i];
			for( j=0 ; j<l ; j++ )
			{
				if( r[j] )
					w+=i+1;
				else if( i<s[j] )
					c++;
			}
		}
		System.out.println( w/l );
		System.out.println( c );
		return new int[]{ w/l, c };
	}
	
	private static int colTransition( Node n, int h )
	{
		boolean[][] b=n.b;
		int[] s=n.s;
		int i, il=s.length, j, l, k=0;
		for( i=0 ; i<il ; i++ )
		{
			for( j=0, l=s[i]-1 ; j<l ; j++ )
			{
				if( b[j][i] ^ b[j+1][i] )
					k++;
			}
		}
		System.out.println( k );
		return k;
	}
	
	private static int rowTransition( Node n, int h )
	{
		boolean[][] b=n.b;
		int[] s=n.s;
		boolean[] r;
		int i, j, l=s.length-1, c=0;
		for( i=0 ; i<h ; i++ )
		{
			r=b[i];
			for( j=0 ; j<l ; j++ )
			{
				if( i<s[j] && i<s[j+1] && (r[j]^r[j+1]) )
					c++;
			}
		}
		System.out.println( c );
		return c;
	}
}
