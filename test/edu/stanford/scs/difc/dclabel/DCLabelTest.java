package edu.stanford.scs.difc.dclabel;

import static edu.stanford.scs.difc.dclabel.CNF.And;
import static edu.stanford.scs.difc.dclabel.Disjunction.Or;
import static org.junit.Assert.*;

import org.junit.Test;

public class DCLabelTest {

	@Test
	public void testCanFlowTo() {
		assertTrue(DCLabel.Public.canFlowTo(DCLabel.Top));
		assertTrue(DCLabel.Bottom.canFlowTo(DCLabel.Top));
		assertTrue(DCLabel.Bottom.canFlowTo(DCLabel.Public));
		assertFalse(DCLabel.Top.canFlowTo(DCLabel.Public));
		assertFalse(DCLabel.Top.canFlowTo(DCLabel.Bottom));
		assertFalse(DCLabel.Public.canFlowTo(DCLabel.Bottom));
		
		assertTrue(new DCLabel(Or("amit", "deian"), CNF.True).canFlowTo(
				new DCLabel(Or("amit"), CNF.True)));
		assertFalse(new DCLabel(Or("amit"), CNF.True).canFlowTo(
				new DCLabel(Or("amit", "deian"), CNF.True)));
		
		assertTrue(new DCLabel(CNF.True, Or("amit")).canFlowTo(
				new DCLabel(CNF.True, Or("amit", "deian"))));
		assertFalse(new DCLabel(CNF.True, Or("amit", "deian")).canFlowTo(
				new DCLabel(CNF.True, Or("amit"))));
		
		assertTrue(new DCLabel(Or("amit"), CNF.True).canFlowTo(
				new DCLabel(And("amit", "deian"), CNF.True)));
		assertFalse(new DCLabel(And("amit", "deian"), CNF.True).canFlowTo(
				new DCLabel(Or("amit"), CNF.True)));
	}

	@Test
	public void testLeastUpperBound() {
		assertEquals(new DCLabel(And("amit", "deian"), CNF.True),
				new DCLabel("amit", CNF.True).
					leastUpperBound(new DCLabel("deian", CNF.True)));
		
		assertEquals(new DCLabel(CNF.True, Or("amit", "deian")),
				new DCLabel(CNF.True, "amit").
					leastUpperBound(new DCLabel(CNF.True, "deian")));
	}

	@Test
	public void testGreatestLowerBound() {
		assertEquals(new DCLabel(Or("amit", "deian"), CNF.True),
				new DCLabel("amit", CNF.True).
					greatestLowerBound(new DCLabel("deian", CNF.True)));
		
		assertEquals(new DCLabel(CNF.True, And("amit", "deian")),
				new DCLabel(CNF.True, "amit").
					greatestLowerBound(new DCLabel(CNF.True, "deian")));
	}

	@Test
	public void testToString() {
		assertEquals("True %% True", DCLabel.Public.toString());
		assertEquals("False %% True", DCLabel.Top.toString());
		assertEquals("True %% False", DCLabel.Bottom.toString());
	}

}
