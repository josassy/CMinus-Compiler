package parser.parse_classes;

import parser.ParseUtility;

import scanner.Token;

public class VarDeclaration extends Declaration {
    int size;

    public VarDeclaration(String id, int size) {
        super(Token.TokenType.INT_TOKEN, id);
        this.size = size;
    }

    public VarDeclaration(String id) {
        super(Token.TokenType.INT_TOKEN, id);
        this.size = -1;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("VarDeclaration", indent);
        ParseUtility.IndentedPrintln(type.toString(), indent+1);
        ParseUtility.IndentedPrintln(id.toString(), indent+1);
        if (size >= 0) {
            ParseUtility.IndentedPrintln("[", indent+1);
            ParseUtility.IndentedPrintln(Integer.toString(size), indent+2);
            ParseUtility.IndentedPrintln("]", indent+1);
        }
    }

    public int getSize() {
        return size;
    }
}