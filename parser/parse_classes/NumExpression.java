package parser.parse_classes;

import parser.ParseUtility;

public class NumExpression extends Expression {
    int num;

    public NumExpression(int num) {
        this.num = num;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln(Integer.toString(num), indent);
    }

    public int getNum() {
        return num;
    }
}