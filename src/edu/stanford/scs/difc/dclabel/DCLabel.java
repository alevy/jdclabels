package edu.stanford.scs.difc.dclabel;

import edu.stanford.scs.difc.Label;

/**
 * 
 * @author alevy
 *
 */
public class DCLabel extends Label<DCLabel> {

	/** Top of the lattice: most secret data with no integrity guarantees. */
	public static final DCLabel Top = new DCLabel(CNF.False, CNF.True);
	
	/** Bottom of the lattice: totally public data with highest integrity
	 * guarantees
	 */
	public static final DCLabel Bottom = new DCLabel(CNF.True, CNF.False);
	
	/** "Middle" of the lattice: public data with no integrity guarantees */
	public static final DCLabel Public = new DCLabel(CNF.True, CNF.True);

	private CNF secrecy;
	private CNF integrity;

	public DCLabel(ToCNF secrecy, ToCNF integrity) {
		this.secrecy = secrecy.toCNF();
		this.integrity = integrity.toCNF();
	}
	
	public DCLabel(String secrecyPrincipal, ToCNF integrity) {
		this.secrecy = CNF.And(secrecyPrincipal);
		this.integrity = integrity.toCNF();
	}
	
	public DCLabel(ToCNF secrecy, String integrityPrincipal) {
		this.secrecy = secrecy.toCNF();
		this.integrity = CNF.And(integrityPrincipal);
	}
	
	public DCLabel(String secrecyPrincipal, String integrityPrintipal) {
		this.secrecy = CNF.And(secrecyPrincipal);
		this.integrity = CNF.And(integrityPrintipal);
	}

	@Override
	public boolean canFlowTo(DCLabel other) {
		return other.secrecy.implies(this.secrecy)
				&& this.integrity.implies(other.integrity);
	}

	@Override
	public DCLabel leastUpperBound(DCLabel other) {
		return new DCLabel(this.secrecy.union(other.secrecy),
				this.integrity.or(other.integrity));
	}

	@Override
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
