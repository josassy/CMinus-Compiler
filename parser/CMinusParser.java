package parser;

import parser.parse_classes.*;
import scanner.*;
import scanner.Token.TokenType;

public class CMinusParser {

  CMinusScanner scanner;

  public void parseProgram() {
    // parse one or more decl's, put em in a list

  }

  private Declaration parseDeclaration() {
    Token token = scanner.getNextToken();

    if (token.getType() == TokenType.VOID_TOKEN) {
      Token id_token = scanner.getNextToken();
      return parseFunDeclaration(id_token);
    } 
    else if (token.getType() == Token.TokenType.INT_TOKEN) {
      Token id_token = scanner.getNextToken();
      return parseDeclarationP(id_token);
    }
    else {
      throw new ParseException("parseDeclaration", TokenType.VOID_TOKEN, token.getType());
    }
  }

  private Declaration parseDeclarationP(Token id_token) {
    
  }

  private Declaration parseVarDeclaration() {

  }

  private Declaration parseFunDeclaration(Token id_token) {

  }

  private Expression parseExpression() {
    //

  }

  private Statement parseStatement() {

  }

  private Statement parseIterStatement() {

  }

  private Statement parseReturnStatement() {

  }

  private Statement parseSelectStatement() {

  }

  private Statement parseCompStatement() {

  }

  private Statement parseExprStatement() {

  }

  private void match(Token token) {
    if (token.getType() != scanner.viewNextToken().getType()) {
      throw new ParseException("Match", token.getType(), scanner.viewNextToken().getType());
    }
  }
}
