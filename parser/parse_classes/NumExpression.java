package parser.parse_classes;

import parser.ParseUtility;
import java.io.Writer;

/**
 * File: NumExpression.java
 * 
 * Represent an int variable in C- with a single integer value
 */
public class NumExpression extends Expression {
    int num;

    public NumExpression(int num) {
        this.num = num;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln(Integer.toString(num), indent, out);
    }
}