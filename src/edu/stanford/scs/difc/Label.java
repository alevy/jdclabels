package edu.stanford.scs.difc;

public interface Label<L extends Label<L>> {
  public boolean canFlowTo(L other);

  public L leastUpperBound(L other);

  public L greatestLowerBound(L other);
}

