package parser.parse_classes;

import parser.ParseUtility;

public class AssignExpression extends Expression {
  VarExpression ve;
  Expression rhs;

  public AssignExpression(VarExpression ve, Expression rhs) {
    this.ve = ve;
    this.rhs = rhs;
  }

  public void Print(int indent) {
    ParseUtility.IndentedPrintln("=", indent);
    ve.Print(indent+1);
    rhs.Print(indent+1);
  }

  public VarExpression getVE() {
    return ve;
  }

  public Expression getRHS() {
    return rhs;
  }
}