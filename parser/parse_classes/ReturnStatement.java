package parser.parse_classes;

import parser.ParseUtility;

public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("return", indent);
        expr.Print(indent+1);
      }

    public Expression getExpr() {
        return expr;
    }
}