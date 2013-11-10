package edu.stanford.scs.difc.dclabel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class Disjunction {
	
	public static final HashSet<Disjunction> False = new HashSet<Disjunction>();
	
	static {
		False.add(new Disjunction(new HashSet<String>()));
	}
	
	private HashSet<String> set;

	private Disjunction(HashSet<String> set) {
		this.set = set;
	}
	
	public Disjunction(String... principals) {
		new Disjunction(principals);
	}

	public Disjunction(Collection<String> principals) {
		this.set = new HashSet<String>(principals);
	}

	public HashSet<String> getSet() {
		return this.set;
	}

	public boolean implies(Disjunction disjT) {
		return disjT.set.containsAll(this.set);
	}

	public Disjunction union(Disjunction other) {
		Disjunction result = new Disjunction(this.set);
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
		Iterator<String> itr = this.set.iterator();
		String cur = itr.next();
		if (cur == null) {
			return "";
		}
		
		String result = cur;
		for (cur = itr.next(); cur != null; cur = itr.next()) {
			result += " /\\ " + cur;
		}
		return result;
	}
}
