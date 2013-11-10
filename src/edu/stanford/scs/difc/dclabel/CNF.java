package edu.stanford.scs.difc.dclabel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A boolean formula in Conjuctive Normal Form (ors on the inside, ands on the
 * outside). CNFs are immutable (the underlying data is hidden and all
 * operations make a copy of the data if it is mutated).
 * @author alevy
 *
 */
public class CNF implements Iterable<Disjunction>, ToCNF {
	
	public static final CNF True = new CNF(new HashSet<Disjunction>());

	public static final CNF False = new CNF(Disjunction.False);
	
	private HashSet<Disjunction> set;

	public CNF(Collection<Disjunction> principals) {
		this.set = new HashSet<Disjunction>(principals);
	}

	public CNF(Disjunction... disjs) {
		this.set = new HashSet<Disjunction>();
		for (Disjunction d : disjs) {
			this.set.add(d);
		}
	}

	public boolean implies(CNF other) {
		for (Disjunction disjO : other.set) {
			boolean result = false;
			for (Disjunction disjT : this.set) {
				if (disjT.implies(disjO)) {
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
		if (this.set.isEmpty()) {
			return "True";
		}
		
		Iterator<Disjunction> itr = this.set.iterator();
		String result = "";
		if (itr.hasNext()) {
			Disjunction cur;
			for (cur = itr.next(); itr.hasNext(); cur = itr.next()) {
				result +=  cur + " /\\ ";
			}
			result += cur;
		}
		return result;
	}
	
	public static CNF And(Disjunction... disjs) {
		return new CNF(disjs);
	}
	
	public static CNF And(String... principals) {
		Disjunction[] disjs = new Disjunction[principals.length];
		for (int i = 0; i < principals.length; ++i) {
			disjs[i] = new Disjunction(principals[i]);
		}
		return new CNF(disjs);
	}

	@Override
	public Iterator<Disjunction> iterator() {
		return this.set.iterator();
	}

	@Override
	public CNF toCNF() {
		return this;
	}
}
