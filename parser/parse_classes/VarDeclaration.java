package parser.parse_classes;

import parser.ParseUtility;
import scanner.Token;

/**
 * File: VarDeclaration.java
 * 
 * Represent a declaration of an int variable in C- with an optional array size
 * parameter.
 */
public class VarDeclaration extends Declaration {
    int size;

    public VarDeclaration(String id, Token type, int size) {
        super(type, id);
        this.size = size;
    }

    public VarDeclaration(String id, Token type) {
        super(type, id);
        this.size = -1;
    }

    public void Print(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getData().toString());
        sb.append(" ");
        sb.append(id.toString());
        if (size >= 0) {
            sb.append("[" + size + "]");
        }
        ParseUtility.IndentedPrintln(sb.toString(), indent);
    }
}