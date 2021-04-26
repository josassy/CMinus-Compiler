package parser.parse_classes;

import parser.ParseUtility;
import parser.CodeGenerationException;
import lowlevel.*;
import java.io.Writer;

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

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln(";", indent, out);
        if (expr != null) {
            expr.Print(out, indent + 1);
        }
    }

    public void genLLCode(Function fun) throws CodeGenerationException {
        expr.genLLCode(fun);
    }
}