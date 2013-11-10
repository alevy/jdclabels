package edu.stanford.scs.difc.dclabel;

import static edu.stanford.scs.difc.dclabel.CNF.And;
import static edu.stanford.scs.difc.dclabel.Disjunction.Or;
import static org.junit.Assert.*;

import org.junit.Test;

public class CNFTest {

	@Test
	public void testImplies() {
		assertTrue(CNF.False.implies(CNF.True));
		assertFalse(CNF.True.implies(CNF.False));
		
		assertTrue(And("amit").implies(And("amit")));
		assertTrue(And(Or("amit"), Or("deian")).implies(And("amit")));
		
		assertFalse(And(Or("amit", "deian")).
				implies(And(Or("amit"), Or("deian"))));
		assertTrue(And(Or("amit"), Or("deian")).implies(And(Or("amit", "deian"))));
	}

	@Test
	public void testUnion() {
		assertEquals(And(Or("amit"), Or("deian")),
				And("amit").union(And("deian")));
		
		assertEquals(And(Or("amit"), Or("deian", "dm")),
				And("amit").union(And(Or("deian", "dm"))));
		
		assertEquals(CNF.False, CNF.False.union(And("amit")));
		assertEquals(CNF.False, CNF.False.union(CNF.False));
		assertEquals(CNF.True, CNF.True.union(And("amit")));
	}

	@Test
	public void testOr() {
		assertEquals(And(Or("amit", "deian")), And("amit").or(And("deian")));
		
		assertEquals(And(Or("amit", "deian"), Or("dm")),
				And("amit").or(And("deian")).union(And("dm")));
	}

	@Test
	public void testToString() {
		assertEquals("False", CNF.False.toString());
		assertEquals("True", CNF.True.toString());
		
		assertEquals("(amit \\/ deian) /\\ (dm)",
				And(Or("amit", "deian"), Or("dm")).toString());
	}

}
