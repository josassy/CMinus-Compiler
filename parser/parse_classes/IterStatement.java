package parser.parse_classes;

public class IterStatement extends Statement {
    Expression expr;
    Statement stmt;

    public IterStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    public void Print() {
        System.out.println("I'm an IterStatement");
      }

    public Expression getExpr() {
        return expr;
    }

    public Statement getStatement() {
        return stmt;
    }
}