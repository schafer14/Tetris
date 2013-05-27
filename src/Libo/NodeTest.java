package Libo;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {
	
	boolean[][]boardActual = 
		{{true, false} , {false,false}, {false, true}, {true, true}};
	boolean[][]boardExpected = 
		{{true, false} , {false,false}, {false, true}, {true, true}};
	
	int [] surfaceActual = {1, 2, 3, 4, 5};
	int [] surfaceExpected = {1, 2, 3, 4, 5};

	
	@Test
	public void testNode() {
		Node testNode = new Node (boardActual, surfaceActual);
		assertArrayEquals("Node defined incorrectly. Board incorrect.",
				boardExpected, 
				testNode.board);
		assertArrayEquals("Node defined incorrectly. Surface incorrect.",
				surfaceExpected,
				testNode.surface);
	}

	@Test
	public void testCompareTo() {
		Node testNode1 = new Node (boardActual, surfaceActual);
		Node testNode2 = new Node (boardExpected, surfaceExpected);
		
		testNode1.mark = 5;
		testNode2.mark = 6;
		
		assertEquals("Compare to returns wrong value.", 
				-1,
				testNode1.compareTo(testNode2));
		
		testNode2.mark = 3;
		
		assertEquals("Compare to returns wrong value.", 
				1,
				testNode1.compareTo(testNode2));
	}

	@Test
	public void testBranch() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminate() {
		boolean [][] board = {
				{true, true, true, true},
				{true, true, false, true},
				{true, true, true, true},
				{false, false, true, false}};
		
		boolean [][] expectedBoard = {
				{true, true, false, true },
				{false, false, true, false} };
		
		int [] surface = {3, 3, 4, 3};
		int [] expectedSurface = {1, 1, 2, 1};
		
		Node testNode = new Node (board, surface);
		
		assertEquals("Elimnate is not deleting the correct amount of rows",
				2,
				Node.eliminate(testNode) );
				
		assertArrayEquals("Elimnate is not changing the array correctly",
				expectedBoard,
				testNode.board);
		
		assertArrayEquals("Eliminate is not changing the surface correctly.",
				expectedSurface,
				testNode.surface);
		
	}

	@Test
	public void testIdentity() {
		boolean [] testArray1 = {true, true, true, true};
		boolean [] testArray2 = {false, false, false, false};
		boolean [] testArray3 = {true, false, true, false};
		
		assertEquals("Identity test failed on all true.", 
				1,
				Node.identity(testArray1));
		
		assertEquals("Identity test failed on all false.", 
				-1,
				Node.identity(testArray2));
		
		assertEquals("Identity test failed on mixed true and false.", 
				0,
				Node.identity(testArray3));
		
	}

}
