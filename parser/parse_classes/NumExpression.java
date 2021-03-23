package parser.parse_classes;

public class NumExpression extends Expression {
    int num;

    public NumExpression(int num) {
        this.num = num;
    }

    public void Print() {
        System.out.println("I'm a NumExpression");
    }

    public int getNum() {
        return num;
    }
}