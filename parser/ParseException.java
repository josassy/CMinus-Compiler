package parser;

import scanner.Token.TokenType;

/**
 * File: ParseException.java
 * 
 * Represents an exception thrown by the CMinusParser when the expected token is
 * not found. This exception class is distinct from MatchException, as it
 * includes the current parse routine in addition to the expected and actual
 * TokenTypes. This exception is propogated to the external calling function for
 * debug output.
 */
public class ParseException extends Exception {
  public ParseException(String routine, TokenType expToken, TokenType actualToken) {
    super(routine + ": Expected " + expToken.toString() + ", got " + actualToken.toString());
  }
}