package parser;

import java.util.ArrayList;

import parser.parse_classes.*;
import scanner.*;
import scanner.Token.TokenType;

public class CMinusParser {

  // We use our scanner created in a previous project to scan the file for tokens
  CMinusScanner scanner;

  /**
   * Initialize the scanner in the constructor
   * 
   * @param scanner the scanner from CMinusParser
   */
  public CMinusParser(CMinusScanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Parses the program from the beginning until EOF
   * 
   * @return the complete program with printable AST tree
   * @throws ParseException
   */
  public Program parseProgram() throws ParseException {
    ArrayList<Declaration> decls = new ArrayList<Declaration>();

    Declaration firstDecl = parseDeclaration();
    decls.add(firstDecl);

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

    return new Program(decls);
  }

  /**
   * Parse a declaration containing either a fundecl or vardecl
   * 
   * @return Declaration object initialized with values.
   * @throws ParseException if unexpected tokens are found
   */
  private Declaration parseDeclaration() throws ParseException {
    Token type_token = scanner.getNextToken();

    if (type_token.getType() == TokenType.VOID_TOKEN) {
      Token id_token = scanner.getNextToken();
      return parseFunDeclarationP(type_token, id_token);
    } else if (type_token.getType() == Token.TokenType.INT_TOKEN) {
      Token id_token = scanner.getNextToken();
      return parseDeclarationP(type_token, id_token);
    } else {
      throw new ParseException("parseDeclaration", TokenType.VOID_TOKEN, type_token.getType());
    }
  }

  /**
   * Parse a declaration' containing the second parts of a declaration
   * 
   * @param type_token the void or int type of the decl from parseDeclaration
   * @param id_token   the id passed from parseDeclaration
   * @return an initialized VarDeclaration
   * @throws ParseException
   */
  private Declaration parseDeclarationP(Token type_token, Token id_token) throws ParseException {
    Token token = scanner.viewNextToken();

    switch (token.getType()) {
    // fun-decl
    case OPAREN_TOKEN:
      return parseFunDeclarationP(type_token, id_token);
    // var-decl
    case OBRACKET_TOKEN:
    case SEMICOLON_TOKEN:
      return parseVarDeclarationP(type_token, id_token);
    // error
    default:
      throw new ParseException("parseDeclarationP", TokenType.OPAREN_TOKEN, token.getType());
    }
  }

  /**
   * Parse a VarDeclaration' containing the second part of a VarDeclaration
   * 
   * @param type_token the void or int type oof the decl from parseDeclarationP
   * @param id_token   the id passed from parseDeclarationP
   * @return an initialized VarDeclaration
   * @throws ParseException
   */
  private Declaration parseVarDeclarationP(Token type_token, Token id_token) throws ParseException {
    Token token = scanner.viewNextToken();
    // parse the int accessor
    if (token.getType() == TokenType.OBRACKET_TOKEN) {
      handledMatch("parseVarDeclaration", TokenType.OBRACKET_TOKEN);
      token = scanner.viewNextToken();
      handledMatch("parseVarDeclarationP", TokenType.NUM_TOKEN);
      Object numData = token.getData();
      int size = Integer.parseInt(numData.toString());
      handledMatch("parseVarDeclarationP", TokenType.CBRACKET_TOKEN);
      handledMatch("parseVarDeclarationP", TokenType.SEMICOLON_TOKEN);
      return new VarDeclaration(id_token.getData().toString(), type_token, size);
    } else {
      handledMatch("parseVarDeclarationP", TokenType.SEMICOLON_TOKEN);
      return new VarDeclaration(id_token.getData().toString(), type_token);
    }
  }

  /**
   * Parse a FunDeclaration' containing the second part of a FunDeclaration
   * 
   * @param type_token the void or int type of the decl from parseDeclaration or
   *                   parseDeclarationP
   * @param id_token   the id passed from parseDeclaration or parseDeclarationP
   * @return an initialized FunDeclaration
   * @throws ParseException
   */
  private Declaration parseFunDeclarationP(Token type_token, Token id_token) throws ParseException {
    handledMatch("parseFunDeclarationP", TokenType.OPAREN_TOKEN);

    ArrayList<Param> params = null;

    switch (scanner.viewNextToken().getType()) {
    case INT_TOKEN:
      params = new ArrayList<Param>();
      params.add(parseParam());

      // Keep adding params while the next token is int
      while (scanner.viewNextToken().getType() == TokenType.COMMA_TOKEN) {
        handledMatch("parseFunDeclarationP", TokenType.COMMA_TOKEN);
        params.add(parseParam());
      }
      break;
    case VOID_TOKEN:
      handledMatch("parseFunDeclarationP", TokenType.VOID_TOKEN);
      break;
    default:
      throw new ParseException("parseFunDeclarationP", TokenType.INT_TOKEN, scanner.viewNextToken().getType());

    }

    handledMatch("parseFunDeclarationP", TokenType.CPAREN_TOKEN);

    Statement cs = parseCompStatement();

    return new FunDeclaration(type_token, id_token.getData().toString(), params, cs);
  }

  /**
   * Parse a generic expression, recursing down into parsing a specific kind of
   * expression
   * 
   * @return Expression object with initialized values
   * @throws ParseException
   */
  private Expression parseExpression() throws ParseException {
    Token token = scanner.getNextToken();
    switch (token.getType()) {
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

  /**
   * Parse the right-hand side of a generic expression
   * 
   * @param id_token
   * @return Expression object with initialized values
   * @throws ParseException
   */
  private Expression parseExpressionP(Token id_token) throws ParseException {
    Token token = scanner.viewNextToken();
    switch (token.getType()) {
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
      VarExpression varExpression = new VarExpression(id_token.getData().toString());
      return parseSimpleExpressionP(varExpression);

    // if next token is follow set, return simple var expression
    case SEMICOLON_TOKEN:
    case CPAREN_TOKEN:
    case CBRACKET_TOKEN:
    case COMMA_TOKEN:
      return new VarExpression(id_token.getData().toString());

    default:
      throw new ParseException("ParseExpressionP", TokenType.ASSIGN_TOKEN, token.getType());
    }
  }

  /**
   * Parse the third part of VarExpressions containing index accessors, accepting
   * values from previous parseExpression calls.
   * 
   * @param id_token the ID of the variable being referenced
   * @param accessor the Expression indexing the variable
   * @return Expression object with initialized values
   * @throws ParseException
   */
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

    // if reach follow set, return simple var w/ accessor
    case SEMICOLON_TOKEN:
    case CPAREN_TOKEN:
    case CBRACKET_TOKEN:
    case COMMA_TOKEN:
      return varExpression;

    default:
      throw new ParseException("parseExpressionPP", TokenType.ASSIGN_TOKEN, token.getType());
    }
  }

  /**
   * Parse the second part of a Simple Expression after being called from
   * parseExpressionP or parseExpressionPP
   * 
   * @param lhs an Expression representing the first part of the Simple Expression
   *            parsed by parseExpressionP or parseExpressionPP
   * @return an initialized SimpleExpression
   * @throws ParseException
   */
  private Expression parseSimpleExpressionP(Expression lhs) throws ParseException {
    Token token = scanner.viewNextToken();
    // additive expression prime
    if (isAddop(token.getType()) || isMulop(token.getType())) {
      // parse additive expression, check for relop as next token
      Expression relopLhs = parseAdditiveExpressionP(lhs);
      if (isRelop(scanner.viewNextToken().getType())) {
        Token relopToken = scanner.getNextToken();
        Expression relopRhs = parseAdditiveExpression();
        return new BinaryExpression(relopLhs, relopRhs, relopToken);
      } else {
        // no relop, so just return the lhs
        return relopLhs;
      }
    }

    else if (isRelop(token.getType())) {
      // relop additive expression
      Token relopToken = scanner.getNextToken();
      Expression rhs = parseAdditiveExpression();
      return new BinaryExpression(lhs, rhs, relopToken);
    }

    else {
      switch (token.getType()) {

      // if reach follow set, return left hand side
      case SEMICOLON_TOKEN:
      case CPAREN_TOKEN:
      case CBRACKET_TOKEN:
      case COMMA_TOKEN:
        return lhs;

      default:
        throw new ParseException("parseSimpleExpressionP", TokenType.MULT_TOKEN, token.getType());
      }
    }
  }

  /**
   * Parse an Additive Expression
   * 
   * @return an initialized BinaryExpression
   * @throws ParseException
   */
  public Expression parseAdditiveExpression() throws ParseException {
    Expression lhs = parseTerm();
    while (isAddop(scanner.viewNextToken().getType())) {
      Token addopToken = scanner.getNextToken();
      Expression rhs = parseTerm();
      lhs = new BinaryExpression(lhs, rhs, addopToken);
    }
    return lhs;
  }

  /**
   * Parse the second part of an Additive Expression after being caleld from
   * AdditiveExpression
   * 
   * @param lhs the first part of Additive Expression as parsed by
   *            parseAdditiveExpression
   * @return an initialized BinaryExpression
   * @throws ParseException
   */
  public Expression parseAdditiveExpressionP(Expression lhs) throws ParseException {
    Expression termLhs = parseTermP(lhs);
    while (isAddop(scanner.viewNextToken().getType())) {
      Token addopToken = scanner.getNextToken();
      Expression rhs = parseTerm();
      termLhs = new BinaryExpression(termLhs, rhs, addopToken);
    }
    return termLhs;
  }

  /**
   * Parse a series of factors separated by mulops
   * 
   * @return an Expression object with a left-recursive tree of factors separated
   *         by mulops
   * @throws ParseException
   */
  public Expression parseTerm() throws ParseException {
    Expression lhs = parseFactor();
    while (isMulop(scanner.viewNextToken().getType())) {
      Token mulopToken = scanner.getNextToken();
      Expression rhs = parseFactor();
      lhs = new BinaryExpression(lhs, rhs, mulopToken);
    }
    return lhs;
  }

  /**
   * Parse a series of factors preceded by and separated by mulops
   * 
   * @param lhs a term parsed by parseTerm
   * @return an initialized BinaryExpression
   * @throws ParseException
   */
  public Expression parseTermP(Expression lhs) throws ParseException {
    while (isMulop(scanner.viewNextToken().getType())) {
      Token mulopToken = scanner.getNextToken();
      Expression rhs = parseFactor();
      lhs = new BinaryExpression(lhs, rhs, mulopToken);
    }
    return lhs;
  }

  /**
   * Parse a factor into an expression of one of several types
   * 
   * @return a NumExpression, VarExpression, or CallExpression
   * @throws ParseException
   */
  public Expression parseFactor() throws ParseException {
    switch (scanner.viewNextToken().getType()) {
    case OPAREN_TOKEN:
      handledMatch("ParseFactor", TokenType.OPAREN_TOKEN);
      Expression expr = parseExpression();
      handledMatch("ParseFactor", TokenType.CPAREN_TOKEN);
      return expr;
    case ID_TOKEN:
      return parseFactorP(scanner.getNextToken());
    case NUM_TOKEN:
      return new NumExpression(Integer.parseInt(scanner.getNextToken().getData().toString()));
    default:
      throw new ParseException("ParseFactor", TokenType.OPAREN_TOKEN, scanner.viewNextToken().getType());
    }
  }

  /**
   * Parse the second part of a factor
   * 
   * @param id_token a token containing an ID name for a variable
   * @return an initialized VarExpression or CallExpression
   * @throws ParseException
   */
  public Expression parseFactorP(Token id_token) throws ParseException {
    switch (scanner.viewNextToken().getType()) {
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

  /**
   * Parse a generic statement, ultimately returning a statement of a specific
   * type
   * 
   * @return an initialized ExprStatement, CompStatement, SelectStatement, or
   *         ReturnStatement
   * @throws ParseException
   */
  private Statement parseStatement() throws ParseException {
    Token token = scanner.viewNextToken();

    switch (token.getType()) {
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

  /**
   * Parse an iteration statement
   * 
   * @return an initialized IterStatement
   * @throws ParseException
   */
  private Statement parseIterStatement() throws ParseException {
    handledMatch("ParseIterStatement", TokenType.WHILE_TOKEN);
    handledMatch("ParseIterStatement", TokenType.OPAREN_TOKEN);

    Expression expr = parseExpression();

    handledMatch("ParseIterStatement", TokenType.CPAREN_TOKEN);

    Statement stmt = parseStatement();

    return new IterStatement(expr, stmt);
  }

  /**
   * Parse a Return Statement
   * 
   * @return an initialized ReturnStatement
   * @throws ParseException
   */
  private Statement parseReturnStatement() throws ParseException {
    handledMatch("ParseReturnStatement", TokenType.RETURN_TOKEN);

    Expression expr = null;
    switch (scanner.viewNextToken().getType()) {
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

  /**
   * Parse a SelectStatement
   * 
   * @return an initialized SelectStatement
   * @throws ParseException
   */
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
    } else {
      return new SelectStatement(expr, stmt);
    }
  }

  /**
   * Parse a CompStatement
   * 
   * @return an initialized CompStatement
   * @throws ParseException
   */
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

  /**
   * Parse a list of VarDeclarations starting with int
   * 
   * @return a list of VarDeclarations starting with int
   * @throws ParseException
   */
  private ArrayList<Declaration> parseLocalDeclarations() throws ParseException {
    ArrayList<Declaration> decls = new ArrayList<Declaration>();
    while (scanner.viewNextToken().getType() == TokenType.INT_TOKEN) {
      Declaration vdecl = parseDeclaration();
      decls.add(vdecl);
    }

    return decls;
  }

  /**
   * Parse an ExprStatement
   * 
   * @return an initialized ExprStatement
   * @throws ParseException
   */
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

  /**
   * Parse a Param
   * 
   * @return an intialized Param
   * @throws ParseException
   */
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
    } else {
      throw new ParseException("ParseParam", TokenType.INT_TOKEN, token.getType());
    }
  }

  /**
   * Parse a list of Args
   * 
   * @return an initialized ArrayList of Expressions
   * @throws ParseException
   */
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

  /**
   * Verifies that the next token is the type that we expect it to be
   * 
   * @param t Token whose type we want to check
   * @throws MatchException
   */
  private void match(TokenType t) throws MatchException {
    Token munch = scanner.getNextToken();
    if (t != munch.getType()) {
      throw new MatchException(t, munch.getType());
    }
  }

  /**
   * Wrapper for match that catches the MatchException and throws a ParseException
   * to provide for better debugging
   * 
   * @param routine A String listing the calling routine for debugging purposes
   * @param t       The token whose type we want to check
   * @throws ParseException
   */
  private void handledMatch(String routine, TokenType t) throws ParseException {
    try {
      match(t);
    } catch (MatchException e) {
      throw new ParseException(routine, t, e.actualTokenType);
    }
  }

  /**
   * Matches a token against the multiple kinds of addop, allowing for easier
   * verification of the next token
   * 
   * @param t The TokenType to check against the lsit of addops
   * @return a boolean indicating whether the match succeeded
   */
  private boolean isAddop(TokenType t) {
    switch (t) {
    case PLUS_TOKEN:
    case MINUS_TOKEN:
      return true;
    default:
      return false;
    }
  }

  /**
   * Matches a token against the multiple kinds of mulop, allowing for easier
   * verification of the next token
   * 
   * @param t The TokenType to check against the lsit of mulops
   * @return a boolean indicating whether the match succeeded
   */
  private boolean isMulop(TokenType t) {
    switch (t) {
    case MULT_TOKEN:
    case DIV_TOKEN:
      return true;
    default:
      return false;
    }
  }

  /**
   * Matches a token against the multiple kinds of relop, allowing for easier
   * verification of the next token
   * 
   * @param t The TokenType to check against the lsit of relops
   * @return a boolean indicating whether the match succeeded
   */
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
