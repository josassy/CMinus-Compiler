package parser.parse_classes;

import scanner.Token.*;
import parser.ParseUtility;
public class BinaryExpression extends Expression {
    Expression lhs;
    Expression rhs;
    TokenType operator;

    public BinaryExpression(Expression lhs, Expression rhs, TokenType operator) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln(operator.toString(), indent);
        lhs.Print(indent + 1);
        rhs.Print(indent + 1);
    }

    public Expression getLHS() {
        return lhs;
    }

    public Expression getRHS() {
        return rhs;
    }

    public TokenType getOperator() {
        return operator;
    }
}