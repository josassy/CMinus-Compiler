package parser.parse_classes;

import parser.ParseUtility;

public class ExprStatement extends Statement {
    Expression expr;

    public ExprStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("ExprStmt", indent);
        if (expr != null) {
            expr.Print(indent+1);
        }
    }
    
    public Expression getExpr() {
        return expr;
    }
}