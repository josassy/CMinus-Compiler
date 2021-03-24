package parser.parse_classes;

import parser.ParseUtility;

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
    }
    else {
      ParseUtility.IndentedPrintln(id + "[", indent);
      index.Print(indent+1);
      ParseUtility.IndentedPrintln("]", indent);
    }
  }

  public String getID() {
    return id;
  }

  public Expression getIndex() {
    return index;
  }
}