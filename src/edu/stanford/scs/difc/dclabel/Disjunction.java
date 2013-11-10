package edu.stanford.scs.difc.dclabel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A disjunction of principals. Disjunctions are immutable (the underlying data
 * is hidden and all operations make a copy of the data if it is mutated).
 * @author alevy
 *
 */
public class Disjunction implements Iterable<String>, ToCNF {
	
	public static final Disjunction False = new Disjunction(new HashSet<String>());
	
	private HashSet<String> set;

	private Disjunction(HashSet<String> set) {
		this.set = set;
	}
	
	public Disjunction(String... principals) {
		this.set = new HashSet<String>();
		for (String s : principals) {
			this.set.add(s);
		}
	}

	public Disjunction(Collection<String> principals) {
		this.set = new HashSet<String>(principals);
	}

	public boolean implies(Disjunction disjT) {
		return disjT.set.containsAll(this.set);
	}

	public Disjunction union(Disjunction other) {
		Disjunction result = new Disjunction(new HashSet<String>());
		result.set.addAll(this.set);
		result.set.addAll(other.set);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Disjunction)) {
			return false;
		}
		return this.set.equals(((Disjunction) obj).set);
	}

	@Override
	public int hashCode() {
		return this.set.hashCode();
	}
	
	@Override
	public String toString() {
		if (this.set.isEmpty()) {
			return "False";
		}
		
		ArrayList<String> principalsList = new ArrayList<String>(this.set);
		Collections.sort(principalsList);
		Iterator<String> itr = principalsList.iterator();
		String result = "";
		if (itr.hasNext()) {
			String cur;
			for (cur = itr.next(); itr.hasNext(); cur = itr.next()) {
				result +=  cur + " \\/ ";
			}
			result = "(" + result + cur + ")";
		}
		return result;
	}
	
	public static Disjunction Or(String... principals) {
		return new Disjunction(principals);
	}

	@Override
	public Iterator<String> iterator() {
		return this.set.iterator();
	}

	@Override
	public CNF toCNF() {
		return new CNF(this);
	}
}
