package parser.parse_classes;

import parser.ParseUtility;

public class SelectStatement extends Statement {
    Expression expr;
    Statement stmt;
    Statement elseStmt;

    public SelectStatement(Expression expr, Statement stmt, Statement elseStatement) {
        this.expr = expr;
        this.stmt = stmt;
        this.elseStmt = elseStatement;
    }

    public SelectStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
        this.elseStmt = null;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("if (", indent);
        expr.Print(indent+1);
        ParseUtility.IndentedPrintln(")", indent);
        stmt.Print(indent+1);
        if (elseStmt != null){
            ParseUtility.IndentedPrintln("else", indent);
            elseStmt.Print(indent+1);
        }
    }

    public Expression getExpr() {
        return expr;
    }

    public Statement getStmt() {
        return stmt;
    }

    public Statement getElseStmt() {
        return elseStmt;
    }
}