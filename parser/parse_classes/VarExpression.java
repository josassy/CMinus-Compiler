package parser.parse_classes;

import lowlevel.Operation;
import lowlevel.Operation.OperationType;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import compiler.CMinusCompiler;
import parser.CodeGenerationException;
import parser.ParseUtility;
import java.io.Writer;
import java.util.HashMap;

/**
 * File: VarExpression.java
 * 
 * Represent a Variable Expression in C-, inheriting from Expression. Contains
 * String variable ID and an optional Expression representing an index into an
 * array of name ID
 */
public class VarExpression extends Expression {
  String id;
  Expression index;

  public VarExpression(String id, Expression index) {
    this.id = id;
    this.index = index;
  }

  public VarExpression(String id) {
    this.id = id;
  }

  public void Print(Writer out, int indent) {
    if (index == null) {
      ParseUtility.IndentedPrintln(id, indent, out);
    } else {
      ParseUtility.IndentedPrintln(id, indent, out);
      ParseUtility.IndentedPrintln("[", indent + 1, out);
      index.Print(out, indent + 2);
      ParseUtility.IndentedPrintln("]", indent + 1, out);
    }
  }

  public void genLLCode(Function fun) throws CodeGenerationException {
    BasicBlock currBlock = fun.getCurrBlock();

    // try to retrieve from function symtable
    HashMap st = fun.getTable();
    if (st.containsKey(id)) {
      this.setRegNum((int) st.get(id));
    }

    // if can't find, maybe its a global???
    else if (CMinusCompiler.globalHash.containsKey(id)) {
      // create a load operation
      Operation loadOper = new Operation(OperationType.LOAD_I, currBlock);
      Operand newSrc = new Operand(Operand.OperandType.STRING, id);

      int newReg = fun.getNewRegNum();
      Operand newDest = new Operand(Operand.OperandType.REGISTER, newReg);
      loadOper.setSrcOperand(0, newSrc);
      loadOper.setDestOperand(0, newDest);

      // APPEND THE LOAD OPERATION
      currBlock.appendOper(loadOper);

      // Annoatate destination register
      this.setRegNum(newReg);
    }

    // if can't find in global, this variable probably doesn't exist!
    else {
      throw new CodeGenerationException("VarExpression: Could not find symbol " + id);
    }
  }
}