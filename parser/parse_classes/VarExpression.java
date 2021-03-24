package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

/**
 * File: VarExpression.java
 * 
 * Represent a Variable Expression in C-, inheriting from Expression. Contains
 * String variable ID and an optional Expression representing an index into an
 * array of name ID
 */
public class VarExpression extends Expression {
  String id;
  Expression index;

  public VarExpression(String id, Expression index) {
    this.id = id;
    this.index = index;
  }

  public VarExpression(String id) {
    this.id = id;
  }

  public void Print(Writer out, int indent) {
    if (index == null) {
      ParseUtility.IndentedPrintln(id, indent, out);
    } else {
      ParseUtility.IndentedPrintln(id, indent, out);
      ParseUtility.IndentedPrintln("[", indent + 1, out);
      index.Print(out, indent + 2);
      ParseUtility.IndentedPrintln("]", indent + 1, out);
    }
  }
}