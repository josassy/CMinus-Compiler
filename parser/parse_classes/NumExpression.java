package parser.parse_classes;

import parser.ParseUtility;
import lowlevel.Function;
import lowlevel.Operation;
import lowlevel.Operand;
import java.io.Writer;
import java.util.HashMap;

/**
 * File: NumExpression.java
 * 
 * Represent an int variable in C- with a single integer value
 */
public class NumExpression extends Expression {
    int num;

    public NumExpression(int num) {
        this.num = num;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln(Integer.toString(num), indent, out);
    }

    public Operation genLLCode(Function fun) {
        HashMap st = fun.getTable();
        int newRegNum = fun.getNewRegNum();
        st.put(num, newRegNum);
        Operand newSrc = new Operand(Operand.OperandType.INTEGER, num);
        Operand newDest = new Operand(Operand.OperandType.REGISTER, newRegNum);
        Operation newOperation = new Operation(Operation.OperationType.ASSIGN, fun.getCurrBlock());
        newOperation.setDestOperand(0, newDest);
        newOperation.setSrcOperand(0, newSrc);
        fun.getCurrBlock().appendOper(newOperation);

        // Annotate destination register
        this.setRegNum(newRegNum);
    }
}