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


import java_cup.runtime.*;

%%

%public
%class CMinusJFlexScanner
%implements Scanner

%unicode

%line
%column

%{
  StringBuilder string = new StringBuilder();
  
  private Token token(TokenType type) {
    return new Token(type);
  }

  private Token token(TokenType type, Object value) {
    return new Token(type, value);
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
  "else"                         { return token(ELSE_TOKEN); }
  "int"                          { return token(INT_TOKEN); }
  "if"                           { return token(IF_TOKEN); }
  "return"                       { return token(RETURN_TOKEN); }
  "void"                         { return token(VOID_TOKEN); }
  "while"                        { return token(WHILE_TOKEN); }  
  
  /* separators */
  "("                            { return token(OPAREN_TOKEN); }
  ")"                            { return token(CPAREN_TOKEN); }
  "{"                            { return token(OCURLY_TOKEN); }
  "}"                            { return token(CCURLY_TOKEN); }
  "["                            { return token(OBRACKET_TOKEN); }
  "]"                            { return token(CBRACKET_TOKEN); }
  ";"                            { return token(SEMICOLON_TOKEN); }
  ","                            { return token(COMMA_TOKEN); }
  
  /* operators */
  "="                            { return token(ASSIGN_TOKEN); }
  ">"                            { return token(GREATER_TOKEN); }
  "<"                            { return token(LESS_TOKEN); }
  "=="                           { return token(EQL_TOKEN); }
  "<="                           { return token(LEQ_TOKEN); }
  ">="                           { return token(GEQ_TOKEN); }
  "!="                           { return token(NEQ_TOKEN); }
  "+"                            { return token(PLUS_TOKEN); }
  "-"                            { return token(MINUS_TOKEN); }
  "*"                            { return token(MULT_TOKEN); }
  "/"                            { return token(DIV_TOKEN); }

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */
  "-2147483648"                  { return token(NUM_TOKEN, Integer.valueOf(Integer.MIN_VALUE)); }
  
  {DecIntegerLiteral}            { return token(NUM_TOKEN, Integer.valueOf(yytext())); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return token(ID_TOKEN, yytext()); }  
}

/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return token(EOF); }