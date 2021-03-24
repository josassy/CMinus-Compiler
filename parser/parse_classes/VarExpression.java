package parser.parse_classes;

import parser.ParseUtility;

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

  public void Print(int indent) {
    if (index == null) {
      ParseUtility.IndentedPrintln(id, indent);
    } else {
      ParseUtility.IndentedPrintln(id + "[", indent);
      index.Print(indent + 1);
      ParseUtility.IndentedPrintln("]", indent);
    }
  }
}