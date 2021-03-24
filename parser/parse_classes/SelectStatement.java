package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

/**
 * File: VarDeclaration.java
 * 
 * Represent a Selection Statement in C-, kncluding an Expression to represent
 * the "if" condition, a Statement to reprsent the code executed if that
 * condition is met, and an "else" Statement to represent the code executed
 * otherwise
 */
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

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln("if (", indent, out);
        expr.Print(out, indent + 1);
        ParseUtility.IndentedPrintln(")", indent, out);
        stmt.Print(out, indent + 1);
        if (elseStmt != null) {
            ParseUtility.IndentedPrintln("else", indent, out);
            elseStmt.Print(out, indent + 1);
        }
    }
}