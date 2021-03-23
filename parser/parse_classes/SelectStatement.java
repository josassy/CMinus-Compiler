package parser.parse_classes;

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

    public void Print() {
        System.out.println("I'm a SelectStatement");
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