package parser.parse_classes;

public class AssignExpression extends Expression {
    VarExpression ve;
    Expression rhs;

    public AssignExpression(VarExpression ve, Expression rhs) {
        this.ve = ve;
        this.rhs = rhs;
    }

    public void Print() {
        System.out.println("I'm an AssignExpression");
      }

    public VarExpression getVE() {
        return ve;
    }

    public Expression getRHS() {
        return rhs;
    }
}