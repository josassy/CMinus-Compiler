package parser.parse_classes;

import parser.ParseUtility;
import compiler.CMinusCompiler;
import parser.CodeGenerationException;
import lowlevel.*;
import java.io.Writer;

/**
 * File: AssignExpression.java
 * 
 * Represent an assignement expression with a VarExpression on the left and an
 * expression on the right.
 */
public class AssignExpression extends Expression {
  VarExpression ve;
  Expression rhs;

  public AssignExpression(VarExpression ve, Expression rhs) {
    this.ve = ve;
    this.rhs = rhs;
  }

  public void Print(Writer out, int indent) {
    ParseUtility.IndentedPrintln("=", indent, out);
    ve.Print(out, indent + 1);
    rhs.Print(out, indent + 1);
  }

  public Operation genLLCode(Function fun) throws CodeGenerationException {
    Operand lhOp = ve.genLLCode(fun).getDestOperand(0);
    Operand rhOp = rhs.genLLCode(fun).getDestOperand(0);
    
    // need to check if we are in the global symtable (thus global variable)
    if (CMinusCompiler.globalHash.containsKey(ve.id)) {
      // if so, need to generate a STORE to write back the changes to the global.
      Operation storeOperation = new Operation(Operation.OperationType.STORE_I, fun.getCurrBlock());
      Operand globalStoreLocation = new Operand(Operand.OperandType.STRING, ve.id);
      storeOperation.setSrcOperand(1, globalStoreLocation);
      storeOperation.setSrcOperand(0, rhOp);

      return storeOperation;
    }
    else {
      // otherwise, do a regular assign operation (it is assign of the times)
      Operation assignOperation = new Operation(Operation.OperationType.ASSIGN, fun.getCurrBlock());
      assignOperation.setDestOperand(0, lhOp);
      assignOperation.setSrcOperand(0, rhOp);
      
      fun.getCurrBlock().appendOper(assignOperation);

      return assignOperation;
    }
  }
}