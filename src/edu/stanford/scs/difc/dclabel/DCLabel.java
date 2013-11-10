package edu.stanford.scs.difc.dclabel;

import edu.stanford.scs.difc.Label;

public class DCLabel implements Label<DCLabel> {

	public static final DCLabel Top = new DCLabel(CNF.False, CNF.True);
	public static final DCLabel Public = new DCLabel(CNF.True, CNF.True);
	public static final DCLabel Bottom = new DCLabel(CNF.True, CNF.False);

	private CNF secrecy;
	private CNF integrity;

	public DCLabel(CNF secrecy, CNF integrity) {
		this.secrecy = secrecy;
		this.integrity = integrity;
	}

	public DCLabel(Disjunction secrecy, Disjunction integrity) {
		this.secrecy = new CNF(secrecy);
		this.integrity = new CNF(integrity);
	}

	public boolean canFlowTo(DCLabel other) {
		return other.secrecy.implies(this.secrecy)
				&& this.integrity.implies(other.integrity);
	}

	public DCLabel leastUpperBound(DCLabel other) {
		return new DCLabel(this.secrecy.union(other.secrecy),
				this.integrity.or(other.integrity));
	}

	public DCLabel greatestLowerBound(DCLabel other) {
		return new DCLabel(this.secrecy.or(other.secrecy),
				this.integrity.union(other.integrity));
	}

	public CNF getSecrecy() {
		return secrecy;
	}

	public CNF getIntegrity() {
		return integrity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DCLabel)) {
			return false;
		}
		DCLabel other = (DCLabel) obj;
		return this.secrecy.equals(other.secrecy)
				&& this.integrity.equals(other.integrity);
	}

	@Override
	public String toString() {
		return this.secrecy.toString() + " %% " + this.integrity.toString();
	}

}
