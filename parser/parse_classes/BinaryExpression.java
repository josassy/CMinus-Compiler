package parser.parse_classes;

import scanner.Token;
import parser.ParseUtility;
import java.io.Writer;

/**
 * File: BinaryExpression.java
 * 
 * Represent an expression in C-, inheriting from Expression, with expressions
 * on either side of an operator
 */
public class BinaryExpression extends Expression {
    Expression lhs;
    Expression rhs;
    Token operator;

    public BinaryExpression(Expression lhs, Expression rhs, Token operator) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln(operator.getData().toString(), indent, out);
        lhs.Print(out, indent + 1);
        rhs.Print(out, indent + 1);
    }
}