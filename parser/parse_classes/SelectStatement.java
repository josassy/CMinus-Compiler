package parser.parse_classes;

import parser.ParseUtility;
import parser.CodeGenerationException;
import lowlevel.*;
import java.io.Writer;

/**
 * File: SelectStatement.java
 * 
 * Represent a Selection Statement in C-, kncluding an Expression to represent
 * the "if" condition, a Statement to reprsent the code executed if that
 * condition is met, and an "else" Statement to represent the code executed
 * otherwise
 */
public class SelectStatement extends Statement {
    Expression expr;
    Statement stmt;
    Statement elseStmt;

    public SelectStatement(Expression expr, Statement stmt, Statement elseStatement) {
        this.expr = expr;
        this.stmt = stmt;
        this.elseStmt = elseStatement;
    }

    public SelectStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
        this.elseStmt = null;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln("if", indent, out);
        ParseUtility.IndentedPrintln("(", indent + 1, out);
        expr.Print(out, indent + 2);
        ParseUtility.IndentedPrintln(")", indent + 1, out);
        stmt.Print(out, indent + 1);
        if (elseStmt != null) {
            ParseUtility.IndentedPrintln("else", indent, out);
            elseStmt.Print(out, indent + 1);
        }
    }

    public void genLLCode(Function fun) throws CodeGenerationException {
        // Create new blocks
        BasicBlock thenBlock = new BasicBlock(fun);
        BasicBlock postBlock = new BasicBlock(fun);
        BasicBlock elseBlock = null;
        if (elseStmt != null) {
            elseBlock = new BasicBlock(fun);
        }

        // Gencode expr
        expr.genLLCode(fun);
        int ifReg = expr.getRegNum();
        Operand ifDest = new Operand(Operand.OperandType.REGISTER, ifReg);

        // Make branch to post or else
        Operand zeroCompare = new Operand(Operand.OperandType.INTEGER, 0);
        Operation branchDecisionOperation = new Operation(Operation.OperationType.BEQ, fun.getCurrBlock());
        branchDecisionOperation.setSrcOperand(0, ifDest);
        branchDecisionOperation.setSrcOperand(1, zeroCompare);

        // decide whether branch goes to post or else
        Operand branchOperand = null;
        if (elseStmt != null) {
            branchOperand = new Operand(Operand.OperandType.BLOCK, elseBlock.getBlockNum());
        } else {
            branchOperand = new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum());
        }
        branchDecisionOperation.setSrcOperand(2, branchOperand);
        fun.getCurrBlock().appendOper(branchDecisionOperation);

        // append thenblock to fall through
        fun.appendToCurrentBlock(thenBlock);

        // currentblock = then block
        fun.setCurrBlock(thenBlock);

        // gencode for then
        stmt.genLLCode(fun);

        // append postblock to fall through from then
        fun.appendToCurrentBlock(postBlock);

        // genCode on else
        if (elseBlock != null) {
            // currentblock = else
            fun.setCurrBlock(elseBlock);

            // genCode for else
            elseStmt.genLLCode(fun);

            // Add JMP to post
            Operation jumpToPostOperation = new Operation(Operation.OperationType.JMP, fun.getCurrBlock());
            Operand destBlockOperand = new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum());
            jumpToPostOperation.setSrcOperand(0, destBlockOperand);
            elseBlock.appendOper(jumpToPostOperation);

            // Append else block to unconnected chain
            fun.appendUnconnectedBlock(elseBlock);
        }

        // currentblock = post
        fun.setCurrBlock(postBlock);
    }
}