import java.util.Arrays;

public class Game
{
	private static final int[][][][] tetEnum; // tetEnum=tetrimino enumeration. tetEnum[tetrimino][rotation][position][0]=lower bound (inclusive); .[1]=upper bound (exclusive). te[0] is not used.
	private static final int tetTypes; // tetTypes=number of tetriminos
	private final int boardWid, bufSize;
	private IO io;
	
	public Game( IO io, int boardWid, int bufSize )
	{
		this.io=io;
		this.boardWid=boardWid;
		this.bufSize=bufSize;
	}
	
	public long run()
	{
		long score=0;
		int[] headBuf=new int[tetTypes], currentBuf, headRootTet;
		int i, j, jl, k, kl, bufCursor=buildBuf( headBuf );
		Node head=new Node( new boolean[5][boardWid], new int[boardWid] ), current;
		head.buf=headBuf;
		SyncMaxHeap<Node> q;
		while( bufCursor!=0 )
		{
			q=new SyncMaxHeap<>();
			for( i=1 ; i<tetTypes ; i++ )
			{
				if( headBuf[i]==0 )
					continue;
				for( j=0, jl=tetEnum[i].length ; j<jl ; j++ )
				{
					for( k=0, kl=boardWid-tetEnum[i][j].length ; k<kl ; k++ )
					{
						current=Node.branch( head, tetEnum[i][j], k );
						currentBuf=Arrays.copyOf( headBuf, tetTypes );
						currentBuf[i]--;
						current.buf=currentBuf;
						score+=Node.eliminate( current );
						current.depth=bufCursor;
						current.mark=Evaluator.mark( current );
						current.root=current;
						current.rootTet=new int[]{ i, j, k };
						q.add( current );
					}
				}
			}
			head=search( q );
			headRootTet=head.rootTet;
			headBuf[headRootTet[0]]--;
			i=io.read();
			if( i>0 )
				headBuf[i]++;
			else
				bufCursor--;
			io.write( headRootTet[0] + "\t" + headRootTet[1] + "\t" + headRootTet[2] + "\n" );
		}
		return score;
	}
	
	private int buildBuf( int[] buf )
	{
		int i, j;
		for( i=0 ; i<bufSize ; i++ )
		{
			j=io.read();
			if( j==-1 )
				break;
			buf[j]++;
		}
		return i;
	}
	
	private Node search( SyncMaxHeap<Node> q )
	{
		Search s=new Search( q );
		Thread t=new Thread( s );
		t.start();
		try
		{
			t.join();
		}
		catch( InterruptedException e )
		{}
		while( s.isRunning() )
		{
			try
			{
				s.wait();
			}
			catch( InterruptedException e )
			{
				continue;
			}
		}
		return s.optimal.root;
	}
	
	private class Search implements Runnable
	{
		private final int threadMax;
		private int threadCount=0;
		private long cutOff=180000;
		private SyncMaxHeap<Node> q;
		public Node optimal;
		private final int[] threadMon=new int[0];
		
		private Search( SyncMaxHeap<Node> q )
		{
			this.q=q;
			threadMax=Runtime.getRuntime().availableProcessors();
			optimal=q.peek();
			cutOff+=System.nanoTime();
		}
		
		@Override
		public void run()
		{
			synchronized( threadMon )
			{
				if( System.nanoTime()>cutOff )
				{
					this.notify();
					return;
				}
				threadCount++;
				newThread();
			}
			Node head=q.head(), current;
			if( head==null || head.depth==0 )
			{
				postRun();
				return;
			}
			int i, j, jl, k, kl;
			int[] headBuf=head.buf;
			boolean realTet;
			for( i=1 ; i<tetTypes ; i++ )
			{
				realTet=head.buf[i]>0;
				for( j=0, jl=tetEnum[i].length ; j<jl ; j++ )
				{
					for( k=0, kl=boardWid-tetEnum[i][j].length ; k<kl ; k++ )
					{
						current=Node.branch( head, tetEnum[i][j], k );
						current.buf=Arrays.copyOf( headBuf, tetTypes );
						if( realTet )
							current.buf[i]--;
						current.depth=head.depth-1;
						Node.eliminate( current );
						current.mark=realTet ? Evaluator.mark( current ) : Evaluator.mark( current )/tetTypes;
						if( current.mark>optimal.mark )
							optimal=current;
						current.root=head.root;
						current.rootTet=head.rootTet;
						q.add( current );
					}
				}
			}
			postRun();
		}
		
		private void postRun()
		{
			synchronized( threadMon )
			{
				threadCount--;
				if( threadCount==0 && q.isEmpty() )
					this.notify();
				else
					newThread();
			}
		}
		
		private void newThread()
		{
			if( threadCount<threadMax )
			{
				for( int i=threadCount ; i<threadMax ; i++ )
					new Thread( this ).start();
			}
		}
		
		public boolean isRunning()
		{
			synchronized( threadMon )
			{
				return threadCount!=0;
			}
		}
	}
	
	static
	{
		int[] a={ 0, 1 }, b={ 0, 2 }, c={ 0, 3 }, d={ 1, 2 }, e={ 2, 3 }, f={ 1, 3 };
		tetTypes=8;
		tetEnum=new int[8][][][];
		tetEnum[1]=new int[2][][];
		tetEnum[1][0]=new int[1][];
		tetEnum[1][0][0]=new int[]{ 0, 4 };
		tetEnum[1][1]=new int[][]{ a, a, a, a };
		tetEnum[2]=new int[1][][];
		tetEnum[2][0]=new int[][]{ b, b };
		tetEnum[3]=new int[4][][];
		tetEnum[3][0]=new int[][]{ c, d };
		tetEnum[3][1]=new int[][]{ a, b, a };
		tetEnum[3][2]=new int[][]{ d, c };
		tetEnum[3][3]=new int[][]{ d, b, d };
		tetEnum[4]=new int[4][][];
		tetEnum[4][0]=new int[][]{ c, e };
		tetEnum[4][1]=new int[][]{ b, a, a };
		tetEnum[4][2]=new int[][]{ a, c };
		tetEnum[4][3]=new int[][]{ d, d, b };
		tetEnum[5]=new int[4][][];
		tetEnum[5][0]=new int[][]{ e, c };
		tetEnum[5][1]=new int[][]{ b, d, d };
		tetEnum[5][2]=new int[][]{ c, a };
		tetEnum[5][3]=new int[][]{ a, a, b };
		tetEnum[6]=new int[2][][];
		tetEnum[6][0]=new int[][]{ f, b };
		tetEnum[6][1]=new int[][]{ a, b, d };
		tetEnum[7]=new int[2][][];
		tetEnum[7][0]=new int[2][];
		tetEnum[7][0][0]=b;
		tetEnum[7][0][1]=new int[]{ 1, 3 };
		tetEnum[7][1]=new int[][]{ d, b, a };
	}
}
