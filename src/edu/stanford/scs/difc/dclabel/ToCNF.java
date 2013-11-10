package edu.stanford.scs.difc.dclabel;

/**
 * An interface for classes that can be converted to {@link CNF}.
 * @author alevy
 *
 */
public interface ToCNF {
	
	CNF toCNF();
	
}
