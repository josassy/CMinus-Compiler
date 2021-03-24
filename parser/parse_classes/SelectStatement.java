package parser.parse_classes;

import parser.ParseUtility;

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

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("if (", indent);
        expr.Print(indent + 1);
        ParseUtility.IndentedPrintln(")", indent);
        stmt.Print(indent + 1);
        if (elseStmt != null) {
            ParseUtility.IndentedPrintln("else", indent);
            elseStmt.Print(indent + 1);
        }
    }
}