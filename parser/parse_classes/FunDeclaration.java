package parser.parse_classes;

import java.util.ArrayList;

import parser.ParseUtility;
import scanner.Token;

public class FunDeclaration extends Declaration {
    ArrayList<Param> params;
    Statement cs;

    public FunDeclaration(Token type, String id, ArrayList<Param> params, Statement cs) {
        super(type, id);
        this.params = params;
        this.cs = cs;
    }

    public void Print(int indent) {
        ParseUtility.IndentedPrintln(type.getData().toString() + " " + id.toString() + "(", indent);
        if (params != null) {
            for (Param currParam : params) {
                currParam.Print(indent + 1);
            }
        } else {
            ParseUtility.IndentedPrintln("void", indent + 1);
        }
        ParseUtility.IndentedPrintln(")", indent);

        cs.Print(indent + 1);
    }

    public ArrayList<Param> getParams() {
        return params;
    }

    public Statement getCompStatement() {
        return cs;
    }
}