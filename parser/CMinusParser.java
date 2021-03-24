package parser;

import java.util.ArrayList;

import parser.parse_classes.*;
import scanner.*;
import scanner.Token.TokenType;

public class CMinusParser {

  CMinusScanner scanner;

  public CMinusParser(CMinusScanner scanner) {
    this.scanner = scanner;
  }

  public ArrayList<Declaration> parseProgram() throws ParseException {
    ArrayList<Declaration> decls = new ArrayList<Declaration>();
    
    while (scanner.viewNextToken().getType() != TokenType.EOF) {
      switch (scanner.viewNextToken().getType()) {
        case VOID_TOKEN:
        case INT_TOKEN:
          Declaration decl = parseDeclaration();
          decls.add(decl);
          break;
        default:
          throw new ParseException("ParseProgram", TokenType.INT_TOKEN, scanner.viewNextToken().getType());
      }
    }
    
    return decls;
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
    params.add(parseParam());

    //Keep adding params until the close paren is encountered
    while (scanner.viewNextToken().getType() != TokenType.CPAREN_TOKEN) {
      if (scanner.viewNextToken().getType() == TokenType.COMMA_TOKEN) {
        handledMatch("ParseFunDeclaration", TokenType.COMMA_TOKEN);
      }
      params.add(parseParam());
    }

    handledMatch("parseFunDeclaration", TokenType.CPAREN_TOKEN);

    Statement cs = parseCompStatement();

    return new FunDeclaration(type_token.getType(), id_token.getData().toString(), params, cs);
  }

  private Declaration parseParams()

  private Expression parseExpression() throws ParseException {
    Token token = scanner.getNextToken();
    switch(token.getType()) {
      case NUM_TOKEN:
        // capture num and parse simple expression
        int num = Integer.parseInt(token.getData().toString());
        NumExpression numExpr = new NumExpression(num);
        return parseSimpleExpressionP(numExpr);
      case OPAREN_TOKEN:
        // capture expression and parse simple expression
        Expression lhs = parseExpression();
        handledMatch("parseExpression", TokenType.CPAREN_TOKEN);
        return parseSimpleExpressionP(lhs);
      case ID_TOKEN:
        return parseExpressionP(token);
      default:
        throw new ParseException("parseExpression", TokenType.NUM_TOKEN, token.getType());
    }
  }
  
  private Expression parseExpressionP(Token id_token) throws ParseException {
    Token token = scanner.viewNextToken();
    switch(token.getType()) {
      // first set
      case ASSIGN_TOKEN:
        handledMatch("parseExpressionP", TokenType.ASSIGN_TOKEN);
        Expression rh = parseExpression();
        VarExpression lh = new VarExpression(id_token.getData().toString());
        return new AssignExpression(lh, rh);
      case OBRACKET_TOKEN:
        handledMatch("parseExpressionP", TokenType.OBRACKET_TOKEN);
        Expression accessor = parseExpression();
        handledMatch("parseExpressionP", TokenType.CBRACKET_TOKEN);
        return parseExpressionPP(id_token, accessor);        
      case OPAREN_TOKEN:
        // call expression is lhs of binary expression
        handledMatch("parseExpressionP", TokenType.OPAREN_TOKEN);
        ArrayList<Expression> args = parseArgs();
        handledMatch("parseExpressionP", TokenType.CPAREN_TOKEN);
        String id_string = id_token.getData().toString();
        CallExpression callExpression = new CallExpression(id_string, args);
        return parseSimpleExpressionP(callExpression);
      case MULT_TOKEN:
      case DIV_TOKEN:
      case PLUS_TOKEN:
      case MINUS_TOKEN:
      case LEQ_TOKEN:
      case LESS_TOKEN:
      case GREATER_TOKEN:
      case GEQ_TOKEN:
      case EQL_TOKEN:
      case NEQ_TOKEN:
        String id_string2 = id_token.getData().toString();
        VarExpression varExpression = new VarExpression(id_string2);
        return parseSimpleExpressionP(varExpression);
      
      // follow set if expression' goes to empty
      case SEMICOLON_TOKEN:
      case CPAREN_TOKEN:
      case CBRACKET_TOKEN:
      case COMMA_TOKEN:
        return null;

      default:
        throw new ParseException("ParseExpressionP", TokenType.ASSIGN_TOKEN, token.getType());   
    }
  }

  private Expression parseExpressionPP(Token id_token, Expression accessor) throws ParseException {
    
    Token token = scanner.viewNextToken();
    // either of the non-terms will accept a varExpression
    VarExpression varExpression = new VarExpression(id_token.getData().toString(), accessor);
    switch (token.getType()) {

      case ASSIGN_TOKEN:
        handledMatch("parseExpressionPP", TokenType.ASSIGN_TOKEN);
        Expression rhs = parseExpression();
        return new AssignExpression(varExpression, rhs);
      case MULT_TOKEN:
      case DIV_TOKEN:
      case PLUS_TOKEN:
      case MINUS_TOKEN:
      case LEQ_TOKEN:
      case LESS_TOKEN:
      case GREATER_TOKEN:
      case GEQ_TOKEN:
      case EQL_TOKEN:
      case NEQ_TOKEN:
        return parseSimpleExpressionP(varExpression);

      // follow set if expression'' goes to empty
      case SEMICOLON_TOKEN:
      case CPAREN_TOKEN:
      case CBRACKET_TOKEN:
      case COMMA_TOKEN:
        return null;

      default:
        throw new ParseException("parseExpressionPP", TokenType.ASSIGN_TOKEN, token.getType());
    }
  }

  private Expression parseSimpleExpressionP(Expression lhs) throws ParseException {
    Token token = scanner.viewNextToken();
    // additive expression prime
    if (isAddop(token.getType()) || isMulop(token.getType())) {      
      // parse additive expression, check for relop as next token
      Expression relopLhs = parseAdditiveExpressionP(lhs);
      if (isRelop(scanner.viewNextToken().getType())) {
          Token relopToken = scanner.getNextToken();
          Expression relopRhs = parseAdditiveExpression();
          return new BinaryExpression(relopLhs, relopRhs, relopToken.getType());
      }
      else {
          // no relop, so just return the lhs
          return relopLhs;
      }
    }

    else if (isRelop(token.getType())) {
      // relop additive expression
      Token relopToken = scanner.getNextToken();
      Expression rhs = parseAdditiveExpression();
      return new BinaryExpression(lhs, rhs, relopToken.getType());
    }

    else {
      switch (token.getType()) {
        
      // follow set
      case SEMICOLON_TOKEN:
      case CPAREN_TOKEN:
      case CBRACKET_TOKEN:
      case COMMA_TOKEN:
        return null;

      default:
        throw new ParseException("parseSimpleExpressionP", TokenType.MULT_TOKEN, token.getType());
      }
    }
  }
  
  public Expression parseAdditiveExpression() throws ParseException {
    Expression lhs = parseTerm();
    while (isAddop(scanner.viewNextToken().getType())) {
      Token addopToken = scanner.getNextToken();
      Expression rhs = parseTerm();
      lhs = new BinaryExpression(lhs, rhs, addopToken.getType());
    }
    return lhs;
  }

  public Expression parseAdditiveExpressionP(Expression lhs) throws ParseException {
    Expression termLhs = parseTermP(lhs);
    while (isAddop(scanner.viewNextToken().getType())) {
      Token addopToken = scanner.getNextToken();
      Expression rhs = parseTerm();
      termLhs = new BinaryExpression(termLhs, rhs, addopToken.getType());
    }
    return termLhs;
  }

  public Expression parseTerm() throws ParseException {
    Expression lhs = parseFactor();
    while (isMulop(scanner.viewNextToken().getType())) {
      Token mulopToken = scanner.getNextToken();
      Expression rhs = parseFactor();
      lhs = new BinaryExpression(lhs, rhs, mulopToken.getType());      
    }
    return lhs;
  }
  
  public Expression parseTermP(Expression lhs) throws ParseException {
    while (isMulop(scanner.viewNextToken().getType())) {
      Token mulopToken = scanner.getNextToken();
      Expression rhs = parseFactor();
      lhs = new BinaryExpression(lhs, rhs, mulopToken.getType());      
    }
    return lhs;
  }
  
  public Expression parseFactor() throws ParseException {
    switch(scanner.viewNextToken().getType()) {
      case OPAREN_TOKEN:
        handledMatch("ParseFactor", TokenType.OPAREN_TOKEN);
        Expression expr = parseExpression();
        handledMatch("ParseFactor", TokenType.CPAREN_TOKEN);
        return expr;
      case ID_TOKEN:
        return parseExpressionP(scanner.getNextToken());
      case NUM_TOKEN:
        return new NumExpression((Integer) scanner.getNextToken().getData());
      default:
        throw new ParseException("ParseFactor", TokenType.OPAREN_TOKEN, scanner.viewNextToken().getType());
    }
  }

  public Expression parseFactorP(Token id_token) throws ParseException {
    switch(scanner.viewNextToken().getType()) {
      case OBRACKET_TOKEN:
        handledMatch("ParseFactorP", TokenType.OBRACKET_TOKEN);
        Expression expr = parseExpression();
        handledMatch("ParseFactorP", TokenType.CBRACKET_TOKEN);
        return new VarExpression(id_token.getData().toString(), expr);
      case OPAREN_TOKEN:
        handledMatch("ParseFactorP", TokenType.OPAREN_TOKEN);
        ArrayList<Expression> args = parseArgs();
        handledMatch("ParseFactorP", TokenType.CPAREN_TOKEN);
        return new CallExpression(id_token.getData().toString(), args);
      default:
        return new VarExpression(id_token.getData().toString());
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
    handledMatch("ParseIterStatement", TokenType.WHILE_TOKEN);
    handledMatch("ParseIterStatement", TokenType.OPAREN_TOKEN);

    Expression expr = parseExpression();

    handledMatch("ParseIterStatement", TokenType.CPAREN_TOKEN);

    Statement stmt = parseStatement();

    return new IterStatement(expr, stmt);
  }

  private Statement parseReturnStatement() throws ParseException {
    handledMatch("ParseReturnStatement", TokenType.RETURN_TOKEN);
    
    Expression expr = null;
    switch(scanner.viewNextToken().getType()) {
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
    
    handledMatch("ParseSelectStatement", TokenType.IF_TOKEN);
    handledMatch("ParseSelectStatement", TokenType.OPAREN_TOKEN);

    Expression expr = parseExpression();

    handledMatch("ParseSelectStatement", TokenType.CPAREN_TOKEN);

    Statement stmt = parseStatement();

    if (scanner.viewNextToken().getType() == TokenType.ELSE_TOKEN) {
      handledMatch("ParseSelectStatement", TokenType.ELSE_TOKEN);
      Statement elseStmt = parseStatement();
      return new SelectStatement(expr, stmt, elseStmt);
    }
    else {
      return new SelectStatement(expr, stmt);
    }
  }

  private Statement parseCompStatement() throws ParseException {
    handledMatch("ParseCompStatement", TokenType.OCURLY_TOKEN);
    
    ArrayList<Declaration> localDeclList = parseLocalDeclarations();
    
    ArrayList<Statement> stmtList = new ArrayList<Statement>();

    while (scanner.viewNextToken().getType() != TokenType.CCURLY_TOKEN) {
      switch (scanner.viewNextToken().getType()) {
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
          throw new ParseException("ParseCompStatement", TokenType.NUM_TOKEN, scanner.viewNextToken().getType());
      }
    }

    handledMatch("ParseCompStatement", TokenType.CCURLY_TOKEN); 

    return new CompStatement(localDeclList, stmtList);
  }

  private ArrayList<Declaration> parseLocalDeclarations() throws ParseException {
    ArrayList<Declaration> decls = new ArrayList<Declaration>();
    while (scanner.viewNextToken().getType() == TokenType.INT_TOKEN) {
      Declaration vdecl = parseDeclaration();
      decls.add(vdecl);
    }
    
    return decls;
  }

  private Statement parseExprStatement() throws ParseException {
    Expression exp = null;

    switch (scanner.viewNextToken().getType()) {
      case NUM_TOKEN:
      case OPAREN_TOKEN:
      case ID_TOKEN:
        exp = parseExpression();
      default:
        break;
    }
    
    handledMatch("ParseExprStatement", TokenType.SEMICOLON_TOKEN);

    return new ExprStatement(exp);
  }

  private Param parseParam() throws ParseException {
    Token token = scanner.getNextToken();
    if (token.getType() == TokenType.INT_TOKEN) {
      Token id_token = scanner.getNextToken();
      Boolean hasBrackets = false;
      if (scanner.viewNextToken().getType() == TokenType.OBRACKET_TOKEN) {
        handledMatch("ParseParam", TokenType.OBRACKET_TOKEN);
        handledMatch("ParseParam", TokenType.CBRACKET_TOKEN);
        hasBrackets = true;
      }
      return new Param(id_token.getData().toString(), hasBrackets);
    }
    else {
      throw new ParseException("ParseParam", TokenType.INT_TOKEN, token.getType());
    }
  }

  private ArrayList<Expression> parseArgs() throws ParseException {
    ArrayList<Expression> args = new ArrayList<Expression>();
    while (scanner.viewNextToken().getType() != TokenType.CPAREN_TOKEN) {
      switch (scanner.viewNextToken().getType()) {
        case COMMA_TOKEN:
          handledMatch("ParseArgs", TokenType.COMMA_TOKEN);
        case NUM_TOKEN:
        case OPAREN_TOKEN:
        case ID_TOKEN:
          Expression expr = parseExpression();
          args.add(expr);
          break;
        default:
          throw new ParseException("ParseArgs", TokenType.NUM_TOKEN, scanner.viewNextToken().getType());
      }
    }
    return args;
  }

  private void match(TokenType t) throws MatchException {
    if (t != scanner.getNextToken().getType()) {
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

  private boolean isAddop(TokenType t) {
    switch (t) {
      case PLUS_TOKEN:
      case MINUS_TOKEN:
        return true;
      default:
        return false;
    }
  }
  
  private boolean isMulop(TokenType t) {
    switch (t) {
      case MULT_TOKEN:
      case DIV_TOKEN:
        return true;
      default:
        return false;
    }
  }

  private boolean isRelop(TokenType t) {
    switch (t) {
      case LESS_TOKEN:
      case GREATER_TOKEN:
      case GEQ_TOKEN:
      case LEQ_TOKEN:
      case EQL_TOKEN:
      case NEQ_TOKEN:
        return true;
      default:
        return false;
    }
  }
}
