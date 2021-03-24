package parser.parse_classes;

import parser.ParseUtility;

/**
 * File: IterStatement.java
 * 
 * Represents and Iteration Statement in C-, inheriting from Statement. Contains
 * an Expression to represent the loop condition and a Statement to represent
 * the action of the loop.
 */
public class IterStatement extends Statement {
    Expression expr;
    Statement stmt;

    public IterStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("while", indent);
        ParseUtility.IndentedPrintln("(", indent + 1);
        expr.Print(indent + 2);
        ParseUtility.IndentedPrintln(")", indent + 1);
        stmt.Print(indent + 1);
    }
}