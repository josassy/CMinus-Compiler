package parser;
import scanner.Token.TokenType;

public class ParseException extends Exception {
  public ParseException(String routine, TokenType expToken, TokenType actualToken) {
    super(routine + ": Expected " + expToken.toString() + ", got " + actualToken.toString());
  }
}