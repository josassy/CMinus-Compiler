package parser;

import java.util.ArrayList;

import parser.parse_classes.*;
import scanner.*;
import scanner.Token.TokenType;

public class CMinusParser {

  CMinusScanner scanner;

  public void parseProgram() {
    // parse one or more decl's, put em in a list

  }

  private Declaration parseDeclaration() throws ParseException {
    Token type_token = scanner.getNextToken();

    if (type_token.getType() == TokenType.VOID_TOKEN) {
      Token id_token = scanner.getNextToken();
      return parseFunDeclaration(type_token, id_token);
    } 
    else if (type_token.getType() == Token.TokenType.INT_TOKEN) {
      Token id_token = scanner.getNextToken();
      return parseDeclarationP(type_token, id_token);
    }
    else {
      throw new ParseException("parseDeclaration", TokenType.VOID_TOKEN, type_token.getType());
    }
  }

  private Declaration parseDeclarationP(Token type_token, Token id_token) throws ParseException {
    Token token = scanner.viewNextToken();

    switch (token.getType()) {
      // fun-decl
      case OPAREN_TOKEN:
        return parseFunDeclaration(type_token, id_token);
      // var-decl
      case OBRACKET_TOKEN:
      case SEMICOLON_TOKEN:
        return parseVarDeclaration(id_token);
      // error
      default:
        throw new ParseException("parseDeclarationP", TokenType.OPAREN_TOKEN, token.getType());
    }    
  }

  private Declaration parseVarDeclaration(Token id_token) throws ParseException {
    Token token = scanner.getNextToken();
    // parse the int accessor
    if (token.getType() == TokenType.OBRACKET_TOKEN) {
      token = scanner.getNextToken();
      Object numData = token.getData();
      int size = Integer.parseInt(numData.toString());
      handledMatch("parseVarDeclaration", TokenType.CBRACKET_TOKEN);
      handledMatch("parseVarDeclaration", TokenType.SEMICOLON_TOKEN);
      return new VarDeclaration(id_token.getData().toString(), size);
    }
    else {
      handledMatch("parseVarDeclaration", TokenType.SEMICOLON_TOKEN);
      return new VarDeclaration(id_token.getData().toString());
    }
  }

  private Declaration parseFunDeclaration(Token type_token, Token id_token) throws ParseException {
    handledMatch("parseFunDeclaration", TokenType.OPAREN_TOKEN);
    
    ArrayList<Param> params = new ArrayList<Param>();

    //Keep adding params until the close paren is encountered
    while (scanner.viewNextToken().getType() != TokenType.CPAREN_TOKEN) {
      params.add(parseParam());
    }

    handledMatch("parseFunDeclaration", TokenType.CPAREN_TOKEN);

    Statement cs = parseCompStatement();

    return new FunDeclaration(type_token.getType(), id_token.getData().toString(), params, cs);
  }

  private Expression parseExpression() throws ParseException {
    Token token = scanner.getNextToken();
    switch(token.getType()) {
      case NUM_TOKEN:
        // capture num and parse simple expression
        int num = Integer.parseInt(token.getData().toString());
        NumExpression numExpr = new NumExpression(num);
        return parseSimpleExpression(numExpr);
      case OPAREN_TOKEN:
        // capture expression and parse simple expression
        Expression lhs = parseExpression();
        handledMatch("parseExpression", TokenType.CPAREN_TOKEN);
        return parseSimpleExpression(lhs);
      case ID_TOKEN:
        return parseExpressionP(token);
      default:
        throw new ParseException("parseExpression", TokenType.NUM_TOKEN, token.getType());
    }
  }
  
  private Expression parseExpressionP(Token id_token) {
    Token token = scanner.getNextToken();
    switch(token.getType()) {
      // first set
      case ASSIGN_TOKEN:
      case OBRACKET_TOKEN:
      case OPAREN_TOKEN:
      case MULT_TOKEN:
      case DIV_TOKEN:
      case ADD_TOKEN:
      case MINUS_TOKEN:
      
      // follow set
      case SEMICOLON_TOKEN:
      case CBRACKET_TOKEN:
      // case ASSIGN_TOKEN:
      // these are in the first set, so don't include
      // case MULT_TOKEN:
      // case DIV_TOKEN:
      // case ADD_TOKEN:
      case LEQ_TOKEN:
      case LESS_TOKEN:
      case GREATER_TOKEN:
      case GEQ_TOKEN:
      case EQL_TOKEN:
      case NEQ_TOKEN:
      case CBRACKET_TOKEN:
      case COMMA_TOKEN:      
    }
  }

  private Expression parseSimpleExpression(Expression lhs) {
    Token token = scanner.getNextToken();
    switch(token.getType()) {
      case MULT_TOKEN:
      case DIV_TOKEN:
      //additive expression prime

      case LEQ_TOKEN:
      case LESS_TOKEN:
      case GREATER_TOKEN:
      case GEQ_TOKEN:
      case EQL_TOKEN:
      case NEQ_TOKEN:
      //relop additive expression

      
    }
  }

  private Statement parseStatement() throws ParseException {
    Token token = scanner.viewNextToken();
    
    switch(token.getType()){
      case NUM_TOKEN:
      case OPAREN_TOKEN:
      case ID_TOKEN:
      case SEMICOLON_TOKEN:
        return parseExprStatement();
      case OCURLY_TOKEN:
        return parseCompStatement();
      case IF_TOKEN:
        return parseSelectStatement();
      case WHILE_TOKEN:
        return parseIterStatement();
      case RETURN_TOKEN:
        return parseReturnStatement();
      default:
        throw new ParseException("parseStatement", TokenType.NUM_TOKEN, token.getType());
    }
  }

  private Statement parseIterStatement() throws ParseException {
    Token token = scanner.getNextToken();

    handledMatch("ParseIterStatement", TokenType.WHILE_TOKEN);
    handledMatch("ParseIterStatement", TokenType.OPAREN_TOKEN);

    Expression expr = parseExpression();

    handledMatch("ParseIterStatement", TokenType.CPAREN_TOKEN);

    Statement stmt = parseStatement();

    return new IterStatement(expr, stmt);
  }

  private Statement parseReturnStatement() throws ParseException {
    Token token = scanner.getNextToken();

    handledMatch("ParseReturnStatement", TokenType.RETURN_TOKEN);

    token = scanner.getNextToken();
    Expression expr = null;
    switch(token.getType()) {
      case NUM_TOKEN:
      case OPAREN_TOKEN:
      case ID_TOKEN:
        expr = parseExpression();
        break;
      default:
        break;
    }
    
    handledMatch("ParseReturnStatement", TokenType.SEMICOLON_TOKEN);

    return new ReturnStatement(expr);
  }

  private Statement parseSelectStatement() throws ParseException {
    Token token = scanner.getNextToken();
    
   handledMatch("ParseSelectStatement", TokenType.IF_TOKEN);
   handledMatch("ParseSelectStatement", TokenType.OPAREN_TOKEN);

   Expression expr = parseExpression();

   handledMatch("ParseSelectStatement", TokenType.CPAREN_TOKEN);

   Statement stmt = parseStatement();

   if (scanner.viewNextToken().getType() == TokenType.ELSE_TOKEN) {
     Statement elseStmt = parseStatement();
     return new SelectStatement(expr, stmt, elseStmt);
   }
   else {
     return new SelectStatement(expr, stmt);
   }
  }

  private Statement parseCompStatement() throws ParseException {
    Token token = scanner.getNextToken();

    handledMatch("ParseCompStatement", TokenType.OCURLY_TOKEN);
    
    ArrayList<Declaration> localDeclList = parseLocalDeclarations();
    
    ArrayList<Statement> stmtList = new ArrayList<Statement>();
    
    token = scanner.getNextToken();
    while (token.getType() != TokenType.CCURLY_TOKEN) {
      switch (token.getType()) {
        case NUM_TOKEN:
        case OPAREN_TOKEN:
        case ID_TOKEN:
        case SEMICOLON_TOKEN:
        case OCURLY_TOKEN:
        case IF_TOKEN:
        case WHILE_TOKEN:
        case RETURN_TOKEN:
          Statement newStmt = parseStatement();
          stmtList.add(newStmt);
          break;
        default:
          break;
      }
      token = scanner.getNextToken();
    }

    handledMatch("ParseCompStatement", TokenType.CCURLY_TOKEN); 

    return new CompStatement(localDeclList, stmtList);
  }

  private ArrayList<Declaration> parseLocalDeclarations() throws ParseException {
    ArrayList<Declaration> decls = new ArrayList<Declaration>();
    switch(scanner.viewNextToken().getType()) {
      case OBRACKET_TOKEN:
      case SEMICOLON_TOKEN:
        decls.add(parseDeclaration());
        break;
      default:
        break;
    }

    return decls;
  }

  private Statement parseExprStatement() throws ParseException{
    Token token = scanner.viewNextToken();
    switch (token.getType()){
      case NUM_TOKEN:
      case OPAREN_TOKEN:
      case ID_TOKEN:
      case SEMICOLON_TOKEN:
        Expression exp = new Expression();
        exp = parseExpression();
        return new ReturnStatement(exp);
      default:
        throw new ParseException("parseExprStatement", TokenType.NUM_TOKEN, token.getType());
    }
    
  }

  private Param parseParam() throws ParseException {
    Token token = scanner.getNextToken();
    if (token.getType() == TokenType.INT_TOKEN) {
      Token id_token = scanner.getNextToken();
      Boolean hasBrackets = false;
      if (scanner.viewNextToken().getType() == TokenType.OBRACKET_TOKEN) {
        scanner.getNextToken();
        scanner.getNextToken();
        hasBrackets = true;
      }
      return new Param(id_token.getData().toString(), hasBrackets);
    }
    else {
      throw new ParseException("ParseParam", TokenType.INT_TOKEN, token.getType);
    }
  }

  private void match(TokenType t) throws MatchException {
    if (t != scanner.viewNextToken().getType()) {
      throw new MatchException(t, scanner.viewNextToken().getType());
    }
  }

  private void handledMatch(String routine, TokenType t) throws ParseException {
    try {
      match(t);
    }
    catch (MatchException e) {
      throw new ParseException(routine, t, e.actualTokenType);
    }
  }
}
