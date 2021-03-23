package parser.parse_classes;

import scanner.Token.*;

public class BinaryExpression extends Expression {
    Expression lhs;
    Expression rhs;
    TokenType operator;

    public BinaryExpression(Expression lhs, Expression rhs, TokenType operator) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public void Print() {
        System.out.println("I'm a BinaryExpression");
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