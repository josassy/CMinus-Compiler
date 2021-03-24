package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

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

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln("while", indent, out);
        ParseUtility.IndentedPrintln("(", indent + 1, out);
        expr.Print(out, indent + 2);
        ParseUtility.IndentedPrintln(")", indent + 1, out);
        stmt.Print(out, indent + 1);
    }
}