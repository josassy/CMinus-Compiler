package parser.parse_classes;

import java.util.ArrayList;

import parser.ParseUtility;
import scanner.Token;
import java.io.Writer;

/**
 * File: FunDeclaration.java
 * 
 * Represent a function declaration. Contains a list of parameters and a
 * compound statement for the body of the function.
 */
public class FunDeclaration extends Declaration {
    ArrayList<Param> params;
    Statement cs;

    public FunDeclaration(Token type, String id, ArrayList<Param> params, Statement cs) {
        super(type, id);
        this.params = params;
        this.cs = cs;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln(type.getData().toString() + " " + id.toString(), indent, out);
        ParseUtility.IndentedPrintln("(", indent + 1, out);
        if (params != null) {
            for (Param currParam : params) {
                currParam.Print(out, indent + 2);
            }
        } else {
            ParseUtility.IndentedPrintln("void", indent + 2, out);
        }
        ParseUtility.IndentedPrintln(")", indent + 1, out);

        cs.Print(out, indent + 1);
    }
}