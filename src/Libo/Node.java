
public class Node implements Comparable<Node>
{
	public boolean[][] b; // b=board
	public int[] a, s; // a=buffer, s=surface
	public int d; // d=remaining depth
	public float v; // v=score
	
	public Node( boolean[][] b, int[] a, int[] s, int d, float v )
	{
		this.b=b;
		this.a=a;
		this.s=s;
		this.d=d;
		this.v=v;
	}
	
	@Override
	public int compareTo( Node n )
	{
		if( v-n.v<0 )
			return -1;
		else
			return 1;
	}
}
