/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* Java 1.2 language lexer specification */

/* Use together with unicode.flex for Unicode preprocesssing */
/* and java12.cup for a Java 1.2 parser                      */

/* Note that this lexer specification is not tuned for speed.
   It is in fact quite slow on integer and floating point literals, 
   because the input is read twice and the methods used to parse
   the numbers are not very fast. 
   For a production quality application (e.g. a Java compiler) 
   this could be optimized */


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

  public Token getNextToken() throws java.io.IOException {
    if (nextToken == null) {
      nextToken = yylex();
    }
    Token returnToken = nextToken;
    if (nextToken.getType() != Token.TokenType.EOF)
      nextToken = yylex();
    return returnToken;
  }

  public Token viewNextToken() throws java.io.IOException {
    if (nextToken == null) {
      nextToken = yylex();
    }
    return nextToken;
  }
%}

/* whitespace characters */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = "/*" [^*] ~"*/" | "/*" "*"+ "/"

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

%%

<YYINITIAL> {

  /* keywords */
  "else"                         { return token(TokenType.ELSE_TOKEN); }
  "int"                          { return token(TokenType.INT_TOKEN); }
  "if"                           { return token(TokenType.IF_TOKEN); }
  "return"                       { return token(TokenType.RETURN_TOKEN); }
  "void"                         { return token(TokenType.VOID_TOKEN); }
  "while"                        { return token(TokenType.WHILE_TOKEN); }  
  
  /* separators */
  "("                            { return token(TokenType.OPAREN_TOKEN); }
  ")"                            { return token(TokenType.CPAREN_TOKEN); }
  "{"                            { return token(TokenType.OCURLY_TOKEN); }
  "}"                            { return token(TokenType.CCURLY_TOKEN); }
  "["                            { return token(TokenType.OBRACKET_TOKEN); }
  "]"                            { return token(TokenType.CBRACKET_TOKEN); }
  ";"                            { return token(TokenType.SEMICOLON_TOKEN); }
  ","                            { return token(TokenType.COMMA_TOKEN); }
  
  /* operators */
  "="                            { return token(TokenType.ASSIGN_TOKEN); }
  ">"                            { return token(TokenType.GREATER_TOKEN); }
  "<"                            { return token(TokenType.LESS_TOKEN); }
  "=="                           { return token(TokenType.EQL_TOKEN); }
  "<="                           { return token(TokenType.LEQ_TOKEN); }
  ">="                           { return token(TokenType.GEQ_TOKEN); }
  "!="                           { return token(TokenType.NEQ_TOKEN); }
  "+"                            { return token(TokenType.PLUS_TOKEN); }
  "-"                            { return token(TokenType.MINUS_TOKEN); }
  "*"                            { return token(TokenType.MULT_TOKEN); }
  "/"                            { return token(TokenType.DIV_TOKEN); }

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */
  "-2147483648"                  { return token(TokenType.NUM_TOKEN, Integer.valueOf(Integer.MIN_VALUE)); }
  
  {DecIntegerLiteral}            { return token(TokenType.NUM_TOKEN, Integer.valueOf(yytext())); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return token(TokenType.ID_TOKEN, yytext()); }  
}

/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return token(TokenType.EOF); }