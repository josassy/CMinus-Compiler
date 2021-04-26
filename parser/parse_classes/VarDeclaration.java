package parser.parse_classes;

import parser.ParseUtility;
import scanner.Token;
import lowlevel.Data;
import parser.CodeGenerationException;
import java.io.Writer;

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

    public void Print(Writer out, int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getData().toString());
        sb.append(" ");
        sb.append(id.toString());
        if (size >= 0) {
            sb.append("[" + size + "]");
        }
        ParseUtility.IndentedPrintln(sb.toString(), indent, out);
    }

    public Data genLLCode() throws CodeGenerationException {
        // since we are not handling arrays, ignore the size param
        

        if (size > 0) {
            throw new CodeGenerationException("Cannot handle arrays in this simple parser");
        }
        
        if (type.getType() == Token.TokenType.INT_TOKEN) {
            return new Data(1, id);
        }
        else if (type.getType() == Token.TokenType.VOID_TOKEN) {
            return new Data(0, id);
        }
        else {
            throw new CodeGenerationException("DATA: Invalid variable type " + type.toString());
        }
    }
}