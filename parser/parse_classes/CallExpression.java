package parser.parse_classes;

import parser.ParseUtility;
import java.util.ArrayList;

public class CallExpression extends Expression {
  String id;
  ArrayList<Expression> args;

  public CallExpression(String id, ArrayList<Expression> args) {
    this.id = id;
    this.args = args;
  }

  public CallExpression(String id) {
    this.id = id;
    this.args = null;
  }

  public void Print(int indent) {
    ParseUtility.IndentedPrintln(id+"(", indent);
    if (args != null) {
      for (Expression e : args) {
        e.Print(indent+1);
      }
    }
    ParseUtility.IndentedPrintln(")", indent);
  }

  public String getID() {
    return id;
  }

  public ArrayList<Expression> getArgs() {
    return args;
  }
}