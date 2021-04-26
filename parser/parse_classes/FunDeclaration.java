package parser.parse_classes;

import java.util.ArrayList;

import parser.ParseUtility;
import scanner.Token;
import lowlevel.*;
import parser.CodeGenerationException;
import java.io.Writer;

/**
 * File: FunDeclaration.java
 * 
 * Represent a function declaration. Contains a list of parameters and a
 * compound statement for the body of the function.
 */
public class FunDeclaration extends Declaration {
    ArrayList<Param> params;
    CompStatement cs;

    public FunDeclaration(Token type, String id, ArrayList<Param> params, CompStatement cs) {
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

    public Function genLLCode() throws CodeGenerationException {
        FuncParam head = null;

        if (params.size() > 0) {
            head = params.get(0).genLLCode();
            FuncParam currItem = head;
            for (int i = 1; i < params.size(); i++) {
              FuncParam nextItem = params.get(i).genLLCode();
              currItem.setNextParam(nextItem);
              currItem = nextItem;
            }
        }

        Function thisFunction = null;

        if (type.getType() == Token.TokenType.INT_TOKEN) {
            thisFunction = new Function(1, id, head);
        }
        else if (type.getType() == Token.TokenType.VOID_TOKEN) {
            thisFunction = new Function(0, id, head);
        }
        else {
            throw new CodeGenerationException("FunDecl: Invalid return type " + type.toString());
        }


        thisFunction.createBlock0();

        BasicBlock firstBlock = new BasicBlock(thisFunction);
        thisFunction.appendBlock(firstBlock);

        thisFunction.setCurrBlock(firstBlock);

        cs.genLLCode(thisFunction);

        return thisFunction;
    }
}