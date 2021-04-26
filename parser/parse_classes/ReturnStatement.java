package parser.parse_classes;

import parser.ParseUtility;
import lowlevel.*;
import java.io.Writer;

/**
 * File: ReturnStatement.java
 * 
 * Represent a return statement in C-. Contains an expression to be returned.
 */
public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln("return", indent, out);
        expr.Print(out, indent + 1);
    }

    public void genLLCode(Function fun) {
        if (expr != null) {
            Operation retexpr = expr.genLLCode();
            Operand expressionResultOper = retexpr.getDestOperand(0);
            
            // move expression result into return register
            Operation moveOp = new Operation(Operation.OperationType.ASSIGN, fun.getCurrBlock());            
            moveOp.setSrcOperand(0, expressionResultOper);
            Operand dest = new Operand(Operand.OperandType.MACRO, "RetReg");
            moveOp.setDestOperand(0, dest);
            fun.getCurrBlock().appendOper(moveOp);
        }
        
        // jump operation to the exit block
        Operation jumpOp = new Operation(Operation.OperationType.JMP, fun.getCurrBlock());
        Operand jumpDest = new Operand(Operand.OperandType.BLOCK, fun.getReturnBlock().getBlockNum());
        jumpOp.setSrcOperand(0, jumpDest);
        fun.getCurrBlock().appendOper(jumpOp);
    }
}