package parser.parse_classes;

import parser.ParseUtility;
import parser.CodeGenerationException;
import lowlevel.*;
import java.util.ArrayList;

import java.io.Writer;

/**
 * File: CallExpression.java
 * 
 * Represent a function call in C-, which inherits from Expression for
 * interaction with other expressions. Contains id string and args list of
 * expressions.
 */
public class CallExpression extends Expression {
  String id;
  ArrayList<Expression> args;

  public CallExpression(String id, ArrayList<Expression> args) {
    this.id = id;
    this.args = args;
  }

  public CallExpression(String id) {
    this.id = id;
    this.args = null;
  }

  public void Print(Writer out, int indent) {
    if (args != null && !args.isEmpty()) {
      ParseUtility.IndentedPrintln(id + "(", indent, out);
      for (Expression e : args) {
        e.Print(out, indent + 1);
      }
      ParseUtility.IndentedPrintln(")", indent, out);
    } else {
      ParseUtility.IndentedPrintln(id + "()", indent, out);
    }
  }

  public Operation genLLCode(Function fun) throws CodeGenerationException {
    // Generate pass operations using register locations from args
    int i = 0;
    for (Expression expr : args) {
      Operation passOperation = new Operation(Operation.OperationType.PASS, fun.getCurrBlock());
      Operation argumentOperation = expr.genLLCode(fun);
      int destReg = (int) argumentOperation.getDestOperand(0).getValue();

      // Args will always be registers since all nums are being assigned to registers
      Operand passedOperand = new Operand(Operand.OperandType.REGISTER, destReg);
      passOperation.setSrcOperand(0, passedOperand);

      // Add attribute to make G's compiler code work
      Attribute passAttribute = new Attribute("PARAM_NUM", Integer.toString(i));
      passOperation.addAttribute(passAttribute);

      fun.getCurrBlock().appendOper(passOperation);
      i++;
    }

    // Generate operation to perform call using string name of the function
    Operation callOperation = new Operation(Operation.OperationType.CALL, fun.getCurrBlock());
    Operand callSrc = new Operand(Operand.OperandType.STRING, id);
    callOperation.setSrcOperand(0, callSrc);

    // Add attribute detailing number of arguments
    Attribute callAttribute = new Attribute("numParams", Integer.toString(args.size()));
    callOperation.addAttribute(callAttribute);

    fun.getCurrBlock().appendOper(callOperation);

    // Generate PostCall operations
    // Should this be moved out of this class?
    Operation postCallOperation = new Operation(Operation.OperationType.ASSIGN, fun.getCurrBlock());
    Operand postCallSrc = new Operand(Operand.OperandType.MACRO, "RetReg");
    Operand postCallDest = new Operand(Operand.OperandType.REGISTER, fun.getNewRegNum());
    postCallOperation.setDestOperand(0, postCallDest);
    postCallOperation.setSrcOperand(0, postCallSrc);
    fun.getCurrBlock().appendOper(postCallOperation);


    //What should we return here?
    return postCallOperation;
  }
}