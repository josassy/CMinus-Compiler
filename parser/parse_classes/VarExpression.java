package parser.parse_classes;

public class VarExpression extends Expression {
    String id;
    Expression index;

    public VarExpression(String id, Expression index) {
        this.id = id;
        this.index = index;
    }

    public VarExpression(String id) {
        this.id = id;
    }

    public void Print() {
        System.out.println("I'm a VarExpression");
      }

    public String getID() {
        return id;
    }

    public Expression getIndex() {
        return index;
    }
}