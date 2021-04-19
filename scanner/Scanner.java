package scanner;

/**
 * File: Scanner.java
 * 
 * Public interface for CMinusScanner to implement. Defines basic get and view
 * (no munch) methods.
 */
public interface Scanner {
  public Token getNextToken();

  public Token viewNextToken();
}
