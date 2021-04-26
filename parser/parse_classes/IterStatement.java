package parser.parse_classes;

import parser.ParseUtility;
import parser.CodeGenerationException;
import lowlevel.*;
import java.io.Writer;

/**
 * File: IterStatement.java
 * 
 * Represents and Iteration Statement in C-, inheriting from Statement. Contains
 * an Expression to represent the loop condition and a Statement to represent
 * the action of the loop.
 */
public class IterStatement extends Statement {
    Expression expr;
    Statement stmt;

    public IterStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln("while", indent, out);
        ParseUtility.IndentedPrintln("(", indent + 1, out);
        expr.Print(out, indent + 2);
        ParseUtility.IndentedPrintln(")", indent + 1, out);
        stmt.Print(out, indent + 1);
    }

    public void genLLCode(Function fun) throws CodeGenerationException {
        // Create new blocks
        BasicBlock testBlock = new BasicBlock(fun);
        BasicBlock thenBlock = new BasicBlock(fun);
        BasicBlock postBlock = new BasicBlock(fun);

        // Add new block for the condition test
        fun.appendToCurrentBlock(testBlock);
        fun.setCurrBlock(testBlock);

        // Gencode expr
        Operand ifDest = expr.genLLCode(fun).getDestOperand(0);

        // Make branch to post
        Operand zeroCompare = new Operand(Operand.OperandType.INTEGER, 0);
        Operation branchDecisionOperation = new Operation(Operation.OperationType.BEQ, fun.getCurrBlock());
        branchDecisionOperation.setSrcOperand(0, ifDest);
        branchDecisionOperation.setSrcOperand(1, zeroCompare);
        Operand branchOperand = new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum());
        branchDecisionOperation.setDestOperand(0, branchOperand);
        fun.getCurrBlock().appendOper(branchDecisionOperation);

        // append thenblock to fall through
        fun.appendToCurrentBlock(thenBlock);

        // currentblock = then block
        fun.setCurrBlock(thenBlock);
        
        // gencode for then
        stmt.genLLCode(fun);

        // append unconditional jump back to testblock
        Operation jumpOperation = new Operation(Operation.OperationType.JMP, fun.getCurrBlock());
        Operand testBlockOperand = new Operand(Operand.OperandType.BLOCK, testBlock.getBlockNum());
        jumpOperation.setSrcOperand(0, testBlockOperand);
        thenBlock.appendOper(jumpOperation);

        // append postblock to fall through from then
        fun.appendToCurrentBlock(postBlock);
         
        // currentblock = post
        fun.setCurrBlock(postBlock);
    }
}