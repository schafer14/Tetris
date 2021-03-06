import static java.util.Arrays.copyOf;

/**
 * This class is a synchronized implementation of maximum heap. Function calls to add( E e ), head(), and peek() are mutually exclusive.
 * No documentation is created for methods, as they are exactly the same as a naive max heap.
 * @param <E> extends Comparable<E>
 */
public class SyncMaxHeap<E extends Comparable<E>>
{
	@SuppressWarnings( "unchecked" )
	private E[] a=(E[]) new Comparable[10];
	private int c=0;
	private final int[] m=new int[0];
	
	public int size()
	{
		synchronized( m )
		{
			return c;
		}
	}
	
	public void add( E e )
	{
		synchronized( m )
		{
			if( c==a.length )
				a=copyOf( a, c*2 );
			a[c]=e;
			up( c++ );
		}
	}
	
	public E peek()
	{
		synchronized( m )
		{
			if( c==0 )
				return null;
			return a[0];
		}
	}
	
	public E head()
	{
		synchronized( m )
		{
			if( c==0 )
				return null;
			E r=a[0];
			if( c--==1 )
				a[0]=null;
			else
			{
				a[0]=a[c];
				a[c]=null;
				down( 0 );
			}
			if( a.length>=20 && c<a.length/4 )
				a=copyOf( a, a.length/2 );
			return r;
		}
	}
	
	private void up( int i )
	{
		int j;
		while( i!=0 )
		{
			j=( (i&1)==1 )?(i/2):(i/2-1);
			if( a[i].compareTo( a[j] )>0 )
			{
				swap( i, j );
				i=j;
			}
			else
				break;
		}
	}
	
	private void down( int i )
	{
		int j;
		while( true )
		{
			j=2*i+1;
			if( j>=c )
				break;
			if( j+1==c )
			{
				if( a[i].compareTo( a[j] )<0 )
					swap( i, j );
				break;
			}
			else
			{
				if( a[i].compareTo( a[j] )<0 )
				{
					if( a[i].compareTo( a[j+1] )<0 )
					{
						if( a[j].compareTo( a[j+1] )<0 )
						{
							swap( i, j+1 );
							i=j+1;
						}
						else
						{
							swap( i, j );
							i=j;
						}
					}
					else
					{
						swap( i, j );
						i=j;
					}
				}
				else
				{
					if( a[i].compareTo( a[j+1] )<0 )
					{
						swap( i, j+1 );
						i=j+1;
					}
					else
						break;
				}
			}
		}
	}
	
	private void swap( int i, int j )
	{
		E t;
		t=a[j];
		a[j]=a[i];
		a[i]=t;
	}
}
