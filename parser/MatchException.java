package parser;

import scanner.Token.TokenType;

/**
 * File: MatchException.java
 * 
 * Represents an exception thrown by the match() function in the CMinusParser.
 * Contains the expected TokenType and the actal TokenType found.
 */
public class MatchException extends Exception {
  TokenType actualTokenType;

  public MatchException(TokenType expTokenType, TokenType actualTokenType) {
    super("Expected token " + expTokenType.toString() + ", got " + actualTokenType.toString());
    this.actualTokenType = actualTokenType;
  }
}