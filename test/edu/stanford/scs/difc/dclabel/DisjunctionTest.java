package edu.stanford.scs.difc.dclabel;

import static edu.stanford.scs.difc.dclabel.Disjunction.Or;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DisjunctionTest {

	@Test
	public void testImplies() {
		assertTrue(Or("amit").implies(Or("amit", "deian")));
		assertFalse(Or("amit", "deian").implies(Or("amit")));
		
		assertTrue(Or("amit", "deian").implies(Or("deian", "amit")));
		assertFalse(Or("amit").implies(Disjunction.False));
	}

	@Test
	public void testUnion() {
		assertEquals(Or("amit", "deian"), Or("amit").union(Or("deian")));
		assertEquals(Or("amit", "deian", "dm"),
				Disjunction.False.union(Or("amit", "deian", "dm")));
	}
	
	@Test
	public void testToString() {
		assertEquals("False", Disjunction.False.toString());
		assertEquals("(amit)", Or("amit").toString());
		assertEquals("(amit \\/ deian)", Or("amit", "deian").toString());
	}

}
