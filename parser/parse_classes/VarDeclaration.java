package parser.parse_classes;

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

    public void Print() {
        System.out.println("I'm a VarDeclaration");
      }

    public int getSize() {
        return size;
    }
}