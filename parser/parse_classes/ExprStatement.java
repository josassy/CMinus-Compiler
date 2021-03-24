package parser.parse_classes;

import parser.ParseUtility;

/**
 * File: ExprStatement.java
 * 
 * Represents an expression statement in C-, inheriting from Statement. Contains
 * an Expression variable.
 */
public class ExprStatement extends Statement {
    Expression expr;

    public ExprStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln(";", indent);
        if (expr != null) {
            expr.Print(indent + 1);
        }
    }
}