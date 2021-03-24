package parser.parse_classes;

import parser.ParseUtility;
import scanner.Token;

/**
 * File: Declaration.java
 * 
 * Represent a generic declaration in C-. Contains a type token and ID token.
 */
public class Declaration implements ParseClass {

  protected Token type;
  protected String id;

  public Declaration(Token type, String id) {
    this.type = type;
    this.id = id;
  }

  public void Print(int indent) {
    ParseUtility.IndentedPrintln(type.getData().toString() + " " + id, indent);
  }
}