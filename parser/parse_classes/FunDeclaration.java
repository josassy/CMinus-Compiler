package parser.parse_classes;

import java.util.ArrayList;

import scanner.Token;

public class FunDeclaration extends Declaration {
    ArrayList<Param> params;
    Statement cs;

    public FunDeclaration(Token.TokenType type, String id, ArrayList<Param> params, Statement cs) {
        super(type, id);
        this.params = params;
        this.cs = cs;
    }

    public void Print() {
        System.out.println("I'm a FunDeclaration");
      }

    public ArrayList<Param> getParams() {
        return params;
    }

    public Statement getCompStatement() {
        return cs;
    }
}