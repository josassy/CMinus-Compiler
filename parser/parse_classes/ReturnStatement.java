package parser.parse_classes;

import parser.ParseUtility;

public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print(int indent) {
        String temp = "return " + expr;
        ParseUtility.IndentedPrintln(temp, indent);
      }

    public Expression getExpr() {
        return expr;
    }
}