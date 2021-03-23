package parser;
import scanner.Token.TokenType;

public class MatchException extends Exception {
  TokenType actualTokenType;
  public MatchException(TokenType expTokenType, TokenType actualTokenType) {
    super("Expected token " + expTokenType.toString() + ", got " + actualTokenType.toString());
    this.actualTokenType = actualTokenType;
  }
}