package edu.stanford.scs.difc.dclabel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class CNF {
	
	public static final CNF True = new CNF(new HashSet<Disjunction>());

	public static final CNF False = new CNF(Disjunction.False);
	
	private HashSet<Disjunction> set;

	public CNF(Collection<Disjunction> principals) {
		this.set = new HashSet<Disjunction>(principals);
	}

	public CNF(Disjunction... disjs) {
		new CNF(disjs);
	}

	public HashSet<Disjunction> getSet() {
		return this.set;
	}

	public boolean implies(CNF other) {
		for (Disjunction disjO : other.set) {
			boolean result = false;
			for (Disjunction disjT : this.set) {
				if (disjO.implies(disjT)) {
					result = true;
					break;
				}
			}
			if (!result) {
				return false;
			}
		}
		return true;
	}

	public CNF union(CNF other) {
		CNF result = new CNF(this.set);
		for (Disjunction o : other.set) {
			for (Disjunction t : this.set) {
				if (!t.implies(o)) {
					result.set.add(o);
				}
			}
		}
		return result;
	}

	public CNF or(CNF other) {
		CNF result = new CNF(new HashSet<Disjunction>());
		for (Disjunction t : this.set) {
			for (Disjunction o : other.set) {
				result.set.add(t.union(o));
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CNF)) {
			return false;
		}
		return this.set.equals(((CNF)obj).set);
	}
	
	@Override
	public int hashCode() {
		return this.set.hashCode();
	}
	
	@Override
	public String toString() {
		Iterator<Disjunction> itr = this.set.iterator();
		Disjunction cur = itr.next();
		if (cur == null) {
			return "";
		}
		
		String result = "(" + cur.toString() + ")";
		for (cur = itr.next(); cur != null; cur = itr.next()) {
			result += " \\/ (" + cur.toString() + ")";
		}
		return result;
	}
}
