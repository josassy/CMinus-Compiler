package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

/**
 * File: AssignExpression.java
 * 
 * Represent an assignement expression with a VarExpression on the left and an
 * expression on the right.
 */
public class AssignExpression extends Expression {
  VarExpression ve;
  Expression rhs;

  public AssignExpression(VarExpression ve, Expression rhs) {
    this.ve = ve;
    this.rhs = rhs;
  }

  public void Print(Writer out, int indent) {
    ParseUtility.IndentedPrintln("=", indent, out);
    ve.Print(out, indent + 1);
    rhs.Print(out, indent + 1);
  }
}