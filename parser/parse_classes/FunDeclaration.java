package parser.parse_classes;

import java.util.ArrayList;

import parser.ParseUtility;
import scanner.Token;

public class FunDeclaration extends Declaration {
    ArrayList<Param> params;
    Statement cs;

    public FunDeclaration(Token.TokenType type, String id, ArrayList<Param> params, Statement cs) {
        super(type, id);
        this.params = params;
        this.cs = cs;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln("FunDeclaration", indent);
        ParseUtility.IndentedPrintln(type.toString(), indent+1);
        ParseUtility.IndentedPrintln(id.toString(), indent+1);
        ParseUtility.IndentedPrintln("{", indent+1);
        for (Param currParam : params) {
            currParam.Print(indent+2);
        }
        ParseUtility.IndentedPrintln("}", indent+1);

        cs.Print(indent+1);
      }
    
    public ArrayList<Param> getParams() {
        return params;
    }

    public Statement getCompStatement() {
        return cs;
    }
}