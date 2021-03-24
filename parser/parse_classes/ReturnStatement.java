package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

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

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln("return", indent, out);
        expr.Print(out, indent + 1);
    }
}