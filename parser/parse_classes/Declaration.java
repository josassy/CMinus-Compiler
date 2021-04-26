package parser.parse_classes;

import parser.CodeGenerationException;
import parser.ParseUtility;
import scanner.Token;
import lowlevel.CodeItem;
import java.io.Writer;

/**
 * File: Declaration.java
 * 
 * Represent a generic declaration in C-. Contains a type token and ID token.
 */
public abstract class Declaration implements ParseClass {

  protected Token type;
  protected String id;

  public Declaration(Token type, String id) {
    this.type = type;
    this.id = id;
  }

  public void Print(Writer out, int indent) {
    ParseUtility.IndentedPrintln(type.getData().toString() + " " + id, indent, out);
  }

  public abstract CodeItem genLLCode() throws CodeGenerationException;
}