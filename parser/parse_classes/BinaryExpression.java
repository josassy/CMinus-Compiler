package parser.parse_classes;

import scanner.Token;
import parser.CodeGenerationException;
import parser.ParseUtility;
import lowlevel.Operation;
import lowlevel.Operation.OperationType;
import lowlevel.Operand;
import lowlevel.Function;
import java.io.Writer;

/**
 * File: BinaryExpression.java
 * 
 * Represent an expression in C-, inheriting from Expression, with expressions
 * on either side of an operator
 */
public class BinaryExpression extends Expression {
    Expression lhs;
    Expression rhs;
    Token operator;

    public BinaryExpression(Expression lhs, Expression rhs, Token operator) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public void Print(Writer out, int indent) {
        ParseUtility.IndentedPrintln(operator.getData().toString(), indent, out);
        lhs.Print(out, indent + 1);
        rhs.Print(out, indent + 1);
    }

    public void genLLCode(Function fun) throws CodeGenerationException {
        lhs.genLLCode(fun);
        int lopDest = lhs.getRegNum();
        rhs.genLLCode(fun);
        int ropDest = lhs.getRegNum();

        OperationType thingToDo;

        switch (operator.getType()) {
        case PLUS_TOKEN:
            thingToDo = OperationType.ADD_I;
            break;
        case MINUS_TOKEN:
            thingToDo = OperationType.SUB_I;
            break;
        case MULT_TOKEN:
            thingToDo = OperationType.MUL_I;
            break;
        case DIV_TOKEN:
            thingToDo = OperationType.DIV_I;
            break;
        case GEQ_TOKEN:
            thingToDo = OperationType.GTE;
            break;
        case GREATER_TOKEN:
            thingToDo = OperationType.GT;
            break;
        case LEQ_TOKEN:
            thingToDo = OperationType.LTE;
            break;
        case LESS_TOKEN:
            thingToDo = OperationType.LT;
            break;
        case EQL_TOKEN:
            thingToDo = OperationType.EQUAL;
            break;
        case NEQ_TOKEN:
            thingToDo = OperationType.NOT_EQUAL;
            break;
        default:
            throw new CodeGenerationException("BINARY EXPRESSION: Invalid op type" + operator.getType().toString());
        }

        Operation operationToAdd = new Operation(thingToDo, fun.getCurrBlock());
        operationToAdd.setSrcOperand(0, lopDest);
        operationToAdd.setSrcOperand(1, ropDest);

        int newRegNum = fun.getNewRegNum();
        setRegNum(newRegNum);

        Operand destOper = new Operand(Operand.OperandType.REGISTER, newRegNum);
        operationToAdd.setDestOperand(0, destOper);
        fun.getCurrBlock().appendOper(operationToAdd);
    }
}