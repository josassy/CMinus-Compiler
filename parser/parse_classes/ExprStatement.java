package parser.parse_classes;

public class ExprStatement extends Statement {
    Expression expr;

    public ExprStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print() {
        System.out.println("I'm an ExprStatement");
      }

    public Expression getExpr() {
        return expr;
    }
}