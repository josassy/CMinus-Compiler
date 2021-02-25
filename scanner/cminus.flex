/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * Adapted from a Java lexer for C- by Josiah Lansford, Jeb Bishop, and    *
 * Alex Kretschmer as part of course requirements for CS3510 Compiler at   *
 * Cedarville University.                                                  *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* C- language lexer specification */

package scanner;

import scanner.Token.TokenType;

%%

%public
%class CMinusJFlexScanner
%implements Scanner
%type Token
%apiprivate

%unicode

%line
%column

%init{
  // need duplicate zzReader set since there is no way to insert after ctor
  this.zzReader = in;
  this.nextToken = yylex();
%init}

%initthrow{
  java.io.IOException 
%initthrow}

%{
  Token nextToken;
  
  private Token token(TokenType type) {
    return new Token(type);
  }

  private Token token(TokenType type, Object value) {
    return new Token(type, value);
  }

  /**
   * Method to indicate whether another token is available
   */
  public boolean hasNextToken() {
    return nextToken.getType() != Token.TokenType.EOF;
  }

  public Token getNextToken() throws java.io.IOException {
    Token returnToken = nextToken;
    if (nextToken.getType() != Token.TokenType.EOF)
      nextToken = yylex();
    return returnToken;
  }

  public Token viewNextToken() {
    return nextToken;
  }
%}

/* whitespace characters */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
BeginComment = "/*"
EndComment = "*/"

/* identifiers */
Identifier = [a-zA-Z]+

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

/* declare an extra state to handle unclosed comments */
%state COMMENT

%%

<YYINITIAL> {

  /* keywords */
  "else"                         { return token(TokenType.ELSE_TOKEN, yytext()); }
  "int"                          { return token(TokenType.INT_TOKEN, yytext()); }
  "if"                           { return token(TokenType.IF_TOKEN, yytext()); }
  "return"                       { return token(TokenType.RETURN_TOKEN, yytext()); }
  "void"                         { return token(TokenType.VOID_TOKEN, yytext()); }
  "while"                        { return token(TokenType.WHILE_TOKEN, yytext()); }  
  
  /* separators */
  "("                            { return token(TokenType.OPAREN_TOKEN, yytext()); }
  ")"                            { return token(TokenType.CPAREN_TOKEN, yytext()); }
  "{"                            { return token(TokenType.OCURLY_TOKEN, yytext()); }
  "}"                            { return token(TokenType.CCURLY_TOKEN, yytext()); }
  "["                            { return token(TokenType.OBRACKET_TOKEN, yytext()); }
  "]"                            { return token(TokenType.CBRACKET_TOKEN, yytext()); }
  ";"                            { return token(TokenType.SEMICOLON_TOKEN, yytext()); }
  ","                            { return token(TokenType.COMMA_TOKEN, yytext()); }
  
  /* operators */
  "="                            { return token(TokenType.ASSIGN_TOKEN, yytext()); }
  ">"                            { return token(TokenType.GREATER_TOKEN, yytext()); }
  "<"                            { return token(TokenType.LESS_TOKEN, yytext()); }
  "=="                           { return token(TokenType.EQL_TOKEN, yytext()); }
  "<="                           { return token(TokenType.LEQ_TOKEN, yytext()); }
  ">="                           { return token(TokenType.GEQ_TOKEN, yytext()); }
  "!="                           { return token(TokenType.NEQ_TOKEN, yytext()); }
  "+"                            { return token(TokenType.PLUS_TOKEN, yytext()); }
  "-"                            { return token(TokenType.MINUS_TOKEN, yytext()); }
  "*"                            { return token(TokenType.MULT_TOKEN, yytext()); }
  "/"                            { return token(TokenType.DIV_TOKEN, yytext()); }

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */
  "-2147483648"                  { return token(TokenType.NUM_TOKEN, Integer.valueOf(Integer.MIN_VALUE)); }
  
  {DecIntegerLiteral}            { return token(TokenType.NUM_TOKEN, Integer.valueOf(yytext())); }
  
  /* comments */
  //{Comment}                      { /* ignore */ }
  {BeginComment}                 { yybegin(COMMENT); }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return token(TokenType.ID_TOKEN, yytext()); }  

  {Identifier}{DecIntegerLiteral} { return token(TokenType.ERROR, yytext()); }
  {DecIntegerLiteral}{Identifier} { return token(TokenType.ERROR, yytext()); }
}

<COMMENT> {
  {EndComment}                   { yybegin(YYINITIAL); }
  [^]                            { /* ignore */ }
}

/* error fallback */
[^]                              { return token(TokenType.ERROR, yytext()); }
<<EOF>>                          { return token(TokenType.EOF, ""); }