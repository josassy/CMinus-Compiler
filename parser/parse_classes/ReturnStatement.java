package parser.parse_classes;

public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print() {
        System.out.println("I'm a ReturnStatement");
      }

    public Expression getExpr() {
        return expr;
    }
}