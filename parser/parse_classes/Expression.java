package parser.parse_classes;

import lowlevel.Operation;
import lowlevel.Function;

import parser.CodeGenerationException;

/**
 * File: Expression.java
 * 
 * Represent an expression in C-. The class is used only for identity purposes,
 * and contains no data members.
 */
public abstract class Expression implements ParseClass {
    private int regNum;

    public int getRegNum() {
        return regNum;
    }
    public void setRegNum(int destReg) {
        regNum = destReg;
    }

    public abstract void genLLCode(Function fun) throws CodeGenerationException ;
}