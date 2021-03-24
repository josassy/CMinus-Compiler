package parser.parse_classes;

import parser.ParseUtility;

/**
 * File: ReturnStatement.java
 * 
 * Represent a return statement in C-. Contains an expression to be returned.
 */
public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("return", indent);
        expr.Print(indent + 1);
    }
}